package com.example.tvtracker.JavaBeans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Saad Amjad
 * @date 2020/03/16
 */
public class Network implements Parcelable {

    private int id;
    private String networkName;



    public Network(String networkName) {
        this.networkName = networkName;
    }
    public Network(int id, String networkName) {
        this.id = id;
        this.networkName = networkName;
    }
    public Network(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    @Override
    public String toString() {
        return this.networkName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.networkName);
    }

    protected Network(Parcel in) {
        this.id = in.readInt();
        this.networkName = in.readString();
    }

    public static final Parcelable.Creator<Network> CREATOR = new Parcelable.Creator<Network>() {
        @Override
        public Network createFromParcel(Parcel source) {
            return new Network(source);
        }

        @Override
        public Network[] newArray(int size) {
            return new Network[size];
        }
    };
}