package com.example.PZ.info;

public class DoktorSzukaj {
    private String nick;
    private String nazwa;

    public DoktorSzukaj(String nazwa, String nick) {
        this.nick = nick;
        this.nazwa = nazwa;
    }


    public String getNick() {
        return nick;
    }

    public String getNazwa() {
        return nazwa;
    }
}
