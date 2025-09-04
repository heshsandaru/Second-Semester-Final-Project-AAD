package lk.ijse.a1_journeypass_backend.controller;

import lk.ijse.a1_journeypass_backend.dto.AuthDTO;
import lk.ijse.a1_journeypass_backend.dto.LoginDTO;
import lk.ijse.a1_journeypass_backend.dto.ResponseDTO;
import lk.ijse.a1_journeypass_backend.dto.UserDTO;
import lk.ijse.a1_journeypass_backend.service.impl.UserServiceImpl;
import lk.ijse.a1_journeypass_backend.util.JwtUtil;
import lk.ijse.a1_journeypass_backend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/JourneyPass/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final ResponseDTO responseDTO;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, ResponseDTO responseDTO) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody LoginDTO loginDTO){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Unauthorized,"Invalid Credential",e.getMessage()));
        }

        UserDTO loadedUser = userService.loadUserDetailsByEmail(loginDTO.getEmail());
        if (loadedUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Unauthorized,"Authorization Failure! Please Try Again",null));
        }

        String token = jwtUtil.generateToken(loadedUser);
        if (token == null || token.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Unauthorized,"Authorization Failure! Please Try Again",null));
        }

        AuthDTO authDTO = new AuthDTO();
        authDTO.setEmail(loadedUser.getEmail());
        authDTO.setToken(token);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(VarList.Created,"Successfully Logged in",authDTO));
    }
}
