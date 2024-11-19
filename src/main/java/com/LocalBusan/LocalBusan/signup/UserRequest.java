package com.LocalBusan.LocalBusan.signup;

public class UserRequest {
    private String email;
    private String password;
    private String nickname;
    private int region_id;

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public int getRegion_id() { return region_id; }
    public void setRegion_id(int region_id) { this.region_id = region_id; }
}
