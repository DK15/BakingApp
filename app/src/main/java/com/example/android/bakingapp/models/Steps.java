package com.example.android.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Steps implements Parcelable {

    public Steps() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    int id;
    String short_desc;
    String desc;
    String video_url;
    String thumbnail;
    private boolean expanded;

    public Steps(int id, String short_desc, String desc, String video_url, String thumbnail) {
        this.id = id;
        this.short_desc = short_desc;
        this.desc = desc;
        this.video_url = video_url;
        this.thumbnail = thumbnail;
    }

    protected Steps(Parcel in) {
        id = in.readInt();
        short_desc = in.readString();
        desc = in.readString();
        video_url = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getThumbnail() {
        return thumbnail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(short_desc);
        parcel.writeString(desc);
        parcel.writeString(video_url);
        parcel.writeString(thumbnail);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
