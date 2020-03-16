package com.example.tvtracker.JavaBeans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Saad Amjad
 * @date 2020/03/16
 */
public class genre implements Parcelable {

    private int id;
    private String genreName;



    public genre(String genreName) {
        this.genreName = genreName;
    }
    public genre(){

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

    protected genre(Parcel in) {
        this.id = in.readInt();
        this.genreName = in.readString();
    }

    public static final Parcelable.Creator<genre> CREATOR = new Parcelable.Creator<genre>() {
        @Override
        public genre createFromParcel(Parcel source) {
            return new genre(source);
        }

        @Override
        public genre[] newArray(int size) {
            return new genre[size];
        }
    };
}