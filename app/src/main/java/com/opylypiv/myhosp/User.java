package com.opylypiv.myhosp;

public class User {

    private String name;
    private String email;
    private String id;
    private int avatarMockUpResource;
    public boolean isLogin;

    public User() {
    }

    public User(String name, String email, String id, int avatarMockUpResource, boolean isLogin) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.avatarMockUpResource = avatarMockUpResource;
        this.isLogin = isLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAvatarMockUpResource() {
        return avatarMockUpResource;
    }

    public void setAvatarMockUpResource(int avatarMockUpResource) {
        this.avatarMockUpResource = avatarMockUpResource;
    }
}
