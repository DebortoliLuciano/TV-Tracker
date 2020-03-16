package com.example.tvtracker.JavaBeans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Saad Amjad
 * @date 2020/03/16
 */
public class network implements Parcelable {

    private int id;
    private String networkName;



    public network(String networkName) {
        this.networkName = networkName;
    }
    public network(){

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

    protected network(Parcel in) {
        this.id = in.readInt();
        this.networkName = in.readString();
    }

    public static final Parcelable.Creator<network> CREATOR = new Parcelable.Creator<network>() {
        @Override
        public network createFromParcel(Parcel source) {
            return new network(source);
        }

        @Override
        public network[] newArray(int size) {
            return new network[size];
        }
    };
}