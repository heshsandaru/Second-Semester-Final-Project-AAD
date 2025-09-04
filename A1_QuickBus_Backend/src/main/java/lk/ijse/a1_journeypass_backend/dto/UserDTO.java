package lk.ijse.a1_journeypass_backend.dto;

import lk.ijse.a1_journeypass_backend.entity.Role;
import lk.ijse.a1_journeypass_backend.entity.Status;

import java.util.UUID;

public class UserDTO {
    private UUID uid;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
    private Status status;

    public UserDTO() {
    }

    public UserDTO(UUID uid, String fullName, String email, String password, String phoneNumber, Role role, Status status) {
        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswordHash(String passwordHash) {
        this.password = passwordHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "uid=" + uid +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
