package com.example.lab1_bsi.serwisy;

public class SingletonPasswordStore {

    private static SingletonPasswordStore instance;
    public String password;
    public static SingletonPasswordStore getInstance() {
        if (instance == null) {
            instance = new SingletonPasswordStore();
        }
        return instance;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String value){
        this.password = value;
    }
}
