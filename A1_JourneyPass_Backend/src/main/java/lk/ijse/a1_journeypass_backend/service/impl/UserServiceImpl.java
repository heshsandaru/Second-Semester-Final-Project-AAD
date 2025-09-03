package lk.ijse.a1_journeypass_backend.service.impl;

import jakarta.validation.Valid;
import lk.ijse.a1_journeypass_backend.dto.UserDTO;
import lk.ijse.a1_journeypass_backend.entity.Role;
import lk.ijse.a1_journeypass_backend.entity.Status;
import lk.ijse.a1_journeypass_backend.entity.User;
import lk.ijse.a1_journeypass_backend.repo.UserRepo;
import lk.ijse.a1_journeypass_backend.service.UserService;
import lk.ijse.a1_journeypass_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin("*")
@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user)
        );
    }

    public UserDTO loadUserDetailsByEmail(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        return modelMapper.map(user, UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        return authorities;
    }

    @Override
    public int saveUser(@Valid UserDTO userDTO) {
        if (userRepo.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        }else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
            userRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }
}

