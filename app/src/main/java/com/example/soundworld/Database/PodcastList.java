package com.example.soundworld.Database;
/*
 * @author: Dearseven
 * @description:
 */

import java.util.List;

public class PodcastList {

    public boolean subscribe;

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public boolean isLove() {
        return love;
    }

    public void setLove(boolean love) {
        this.love = love;
    }

    public boolean love;
    public String getImage_url() {
        return image_url;
    }

    public PodcastList( int id, String title, String image_url,String description) {
        this.id = id;
        this.title = title;
        this.image_url = image_url;
        this.description = description;
    }
    public PodcastList( int id, String title, String image_url,String description,boolean subscribe,boolean love) {
        this.id = id;
        this.title = title;
        this.image_url = image_url;
        this.description = description;
        this.description = description;
        this.love = love;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    private String image_url;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    private String creator;

    private String episode_name;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String title;
    private String description;
    private int podcast_img;

    public String getTitle() {
        return title;
    }

    public PodcastList(int id, String title, String description, int podcast_img) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.podcast_img = podcast_img;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPodcast_img() {
        return podcast_img;
    }

    public void setPodcast_img(int podcast_img) {
        this.podcast_img = podcast_img;
    }

    public PodcastList(){

    }


}
