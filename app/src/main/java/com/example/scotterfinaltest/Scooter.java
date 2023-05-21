package com.example.scotterfinaltest;

import android.os.Parcel;
import android.os.Parcelable;

public class Scooter implements Parcelable {
    private String name;
    private String qrCode;
    private Boolean activated;

    public Scooter(String name, String qrCode, Boolean activated) {
        this.name = name;
        this.qrCode = qrCode;
        this.activated = activated;
    }

    protected Scooter(Parcel in) {
        name = in.readString();
        qrCode = in.readString();
        byte tmpActivated = in.readByte();
        activated = tmpActivated == 1;
    }

    public static final Creator<Scooter> CREATOR = new Creator<Scooter>() {
        @Override
        public Scooter createFromParcel(Parcel in) {
            return new Scooter(in);
        }

        @Override
        public Scooter[] newArray(int size) {
            return new Scooter[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(qrCode);
        dest.writeByte((byte) (activated ? 1 : 0));
    }
}

