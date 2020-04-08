package com.example.tvtracker.JavaBeans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Saad Amjad
 * @date 2020/03/16
 */
public class Show implements Parcelable {

    private int id;
    private String title;
    private String imdbID;
    private String time;
    private String day;
    private String cover; //image
    private String summary;
    private String watched;


    public Show(String title, String imdbID, String time, String day, String cover, String summary, String watched) {
        this.title = title;
        this.imdbID = imdbID;
        this.time = time;
        this.day = day;
        this.cover = cover;
        this.summary = summary;
        this.watched = watched;
    }
    public Show(){

    }
    public Show(int id, String title, String imdbID, String time, String day, String cover, String summary, String watched) {
        this.id = id;
        this.title = title;
        this.imdbID = imdbID;
        this.time = time;
        this.day = day;
        this.cover = cover;
        this.summary = summary;
        this.watched = watched;
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

    public String getWatched() {
        return watched;
    }

    public void setWatched(String watched) {
        this.watched = watched;
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
        dest.writeString(this.watched);
    }

    protected Show(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.imdbID = in.readString();
        this.time = in.readString();
        this.day = in.readString();
        this.cover = in.readString();
        this.summary = in.readString();
        this.watched = in.readString();
    }

    public static final Parcelable.Creator<Show> CREATOR = new Parcelable.Creator<Show>() {
        @Override
        public Show createFromParcel(Parcel source) {
            return new Show(source);
        }

        @Override
        public Show[] newArray(int size) {
            return new Show[size];
        }
    };
}
