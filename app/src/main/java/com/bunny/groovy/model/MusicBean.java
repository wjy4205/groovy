package com.bunny.groovy.model;

import android.os.Parcel;
import android.os.Parcelable;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/14.
 ****************************************/

public class MusicBean implements Parcelable {
    private String musicName;
    private String musicPath;
    private String image; // icon
    private String artist; // 艺术家
    private double length; // 长度
    private int id; // 音乐id
    private String title; // 音乐标题

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
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


    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(musicName);
        dest.writeString(musicPath);
        dest.writeString(image);
        dest.writeString(artist);
        dest.writeDouble(length);
        dest.writeInt(id);
        dest.writeString(title);
    }

    /**
     * 必须用 public static final 修饰符
     * 对象必须用 CREATOR
     */
    public static final Creator<MusicBean> CREATOR = new Creator<MusicBean>() {

        @Override
        public MusicBean createFromParcel(Parcel source) {

            MusicBean music = new MusicBean();
            music.setMusicName(source.readString());
            music.setMusicPath(source.readString());
            music.setImage(source.readString());
            music.setArtist(source.readString());
            music.setLength(source.readDouble());
            music.setId(source.readInt());
            music.setTitle(source.readString());

            return music;
        }

        @Override
        public MusicBean[] newArray(int size) {
            return new MusicBean[size];
        }

    };
}
