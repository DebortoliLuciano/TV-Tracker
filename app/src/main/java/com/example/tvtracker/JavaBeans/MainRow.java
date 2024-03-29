package com.example.tvtracker.JavaBeans;

import android.os.Parcel;
import android.os.Parcelable;

public class MainRow implements Parcelable {

    private int id;
    private int show;
    private int genre;
    private String status;
    private int network;

    public MainRow(int show, int genre, String status, int network) {
        this.show = show;
        this.genre = genre;
        this.status = status;
        this.network = network;
    }
    public MainRow(int id, int show, int genre, String status, int network) {
        this.id = id;
        this.show = show;
        this.genre = genre;
        this.status = status;
        this.network = network;
    }
    public MainRow(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNetwork() {
        return network;
    }

    public void setNetwork(int network) {
        this.network = network;
    }

    @Override
    public String toString() {
        return this.status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.show);
        dest.writeInt(this.genre);
        dest.writeString(this.status);
        dest.writeInt(this.network);
    }

    protected MainRow(Parcel in) {
        this.id = in.readInt();
        this.show = in.readInt();
        this.genre = in.readInt();
        this.status = in.readString();
        this.network = in.readInt();
    }

    public static final Parcelable.Creator<MainRow> CREATOR = new Parcelable.Creator<MainRow>() {
        @Override
        public MainRow createFromParcel(Parcel source) {
            return new MainRow(source);
        }

        @Override
        public MainRow[] newArray(int size) {
            return new MainRow[size];
        }
    };
}