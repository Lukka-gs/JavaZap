package br.senac.rj.msn.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;

    public User(String userName) {
        this.userName = userName;
    }
    public String getUserName() {;
        return userName;
    }
}