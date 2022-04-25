package com.example.PZ.info;

public class Doktor {
    public String getNazwa_uzyt() {
        return nazwa_uzyt;
    }

    public void setNazwa_uzyt(String nazwa_uzyt) {
        this.nazwa_uzyt = nazwa_uzyt;
    }

    private String nazwa_uzyt;

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    private String imie;

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    private String nazwisko;

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) { this.adres = adres; }

    private String adres;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    private String mail;

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    private String telefon;

    public Doktor(String nazwa_uzyt, String imie, String nazwisko, String mail, String telefon) {
        this.nazwa_uzyt = nazwa_uzyt;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.mail = mail;
        this.telefon = telefon;
    }

}
