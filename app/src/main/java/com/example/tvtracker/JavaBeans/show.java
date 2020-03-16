package com.example.tvtracker.JavaBeans;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tvtracker.R;

/**
 * @author Saad Amjad
 * @date 2020/03/16
 */
public class show implements Parcelable {

    private int id;
    private String title;
    private String imdbID;
    private String time;
    private String day;
    private String cover; //image
    private String summary;


    public show(String title, String imdbID, String time, String day, String cover, String summary) {
        this.title = title;
        this.imdbID = imdbID;
        this.time = time;
        this.day = day;
        this.cover = cover;
        this.summary = summary;
    }
    public show(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.imdbID);
        dest.writeString(this.time);
        dest.writeString(this.day);
        dest.writeString(this.cover);
        dest.writeString(this.summary);
    }

    protected show(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.imdbID = in.readString();
        this.time = in.readString();
        this.day = in.readString();
        this.cover = in.readString();
        this.summary = in.readString();
    }

    public static final Parcelable.Creator<show> CREATOR = new Parcelable.Creator<show>() {
        @Override
        public show createFromParcel(Parcel source) {
            return new show(source);
        }

        @Override
        public show[] newArray(int size) {
            return new show[size];
        }
    };
}
