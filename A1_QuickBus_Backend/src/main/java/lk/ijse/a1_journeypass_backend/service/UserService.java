package lk.ijse.a1_journeypass_backend.service;

import jakarta.validation.Valid;
import lk.ijse.a1_journeypass_backend.dto.UserDTO;

public interface UserService {
    int saveUser(@Valid UserDTO userDTO);
}
