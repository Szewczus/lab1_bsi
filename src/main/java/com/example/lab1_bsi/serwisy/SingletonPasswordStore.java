package com.example.lab1_bsi.serwisy;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class SingletonPasswordStore {

    private static SingletonPasswordStore instance;
    public String password;
    public boolean loggedIn;
    public LocalDateTime loggingTime;
    public String Ip;

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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public LocalDateTime getLoggingTime() {
        return loggingTime;
    }

    public void setLoggingTime(LocalDateTime loggingTime) {
        this.loggingTime = loggingTime;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }
}
