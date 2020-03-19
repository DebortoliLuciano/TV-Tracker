package com.example.tvtracker.JavaBeans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Saad Amjad
 * @date 2020/03/16
 */
public class Genre implements Parcelable {

    private int id;
    private String genreName;



    public Genre(String genreName) {
        this.genreName = genreName;
    }
    public Genre(int id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }
    public Genre(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return this.genreName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.genreName);
    }

    protected Genre(Parcel in) {
        this.id = in.readInt();
        this.genreName = in.readString();
    }

    public static final Parcelable.Creator<Genre> CREATOR = new Parcelable.Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel source) {
            return new Genre(source);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}