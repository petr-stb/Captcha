package ru.kontur.intern.captcha.entities;

import java.util.UUID;

public class User {

    private String secretKey;
    private String publicKey;
    private String token;

    public User(){
        setSecretKey();
        setPublicKey();
    }

    public void setSecretKey() {
        secretKey = UUID.randomUUID().toString();
    }

    public void setPublicKey() {
        publicKey = UUID.randomUUID().toString();
    }

    public void setToken() {
        this.token = UUID.randomUUID().toString();
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getToken() {
        return token;
    }

    public void deleteToken(){
        token = "";
    }
}
