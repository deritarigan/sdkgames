package com.akggames.akg_sdk.dao.api.model.request;

public class UpdatePasswordRequest {

    private String phone_number;
    private String auth_provider;
    private String game_provider;
    private String password;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAuth_provider() {
        return auth_provider;
    }

    public void setAuth_provider(String auth_provider) {
        this.auth_provider = auth_provider;
    }

    public String getGame_provider() {
        return game_provider;
    }

    public void setGame_provider(String game_provider) {
        this.game_provider = game_provider;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
