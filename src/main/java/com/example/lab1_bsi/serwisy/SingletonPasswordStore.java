package com.example.lab1_bsi.serwisy;

public class SingletonPasswordStore {

    private static SingletonPasswordStore instance;
    public String value;
    public static SingletonPasswordStore getInstance() {
        if (instance == null) {
            instance = new SingletonPasswordStore();
        }
        return instance;
    }
    public String getPassword(){
        return value;
    }
    public void setPassword(String value){
        this.value = value;
    }
}
