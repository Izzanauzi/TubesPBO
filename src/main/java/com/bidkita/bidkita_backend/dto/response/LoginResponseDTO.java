package com.bidkita.bidkita_backend.dto.response;

public class LoginResponseDTO {

    private String accessToken;
    private String userId;
    private String role;
    private String username;

    public LoginResponseDTO() {}

    public LoginResponseDTO(String accessToken, String userId, String role, String username) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.role = role;
        this.username = username;
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
