package com.example.scotterfinaltest;

public class Scooter
{
    private String name;
    private String qrCode;
    private Boolean activated;

    public Scooter(String name, String qrCode, Boolean activated) {
        this.name = name;
        this.qrCode = qrCode;
        this.activated = activated;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getQrCode() {
        return qrCode;
    }
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    public Boolean getActivated() {
        return activated;
    }
    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
}
