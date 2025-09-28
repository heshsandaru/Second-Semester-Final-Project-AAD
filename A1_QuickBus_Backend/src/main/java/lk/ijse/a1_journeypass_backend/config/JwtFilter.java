package lk.ijse.a1_journeypass_backend.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.a1_journeypass_backend.service.impl.UserServiceImpl;
import lk.ijse.a1_journeypass_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserServiceImpl userService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);

            try {
                // validate token structure first
                if (token.split("\\.").length == 3) {
                    email = jwtUtil.getUsernameFromToken(token);
                    Claims claims = jwtUtil.getUserRoleCodeFromToken(token);

                    request.setAttribute("email", email);
                    request.setAttribute("role", claims.get("role"));
                } else {
                    System.out.println("⚠️ Invalid JWT format: " + token);
                }
            } catch (Exception e) {
                System.out.println("⚠️ Failed to parse JWT: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ Missing or invalid Authorization header: " + authorization);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(email);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

}

