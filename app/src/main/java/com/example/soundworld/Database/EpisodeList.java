package com.example.soundworld.Database;
/*
 * @author: Dearseven
 * @description:
 */

public class EpisodeList {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String title;
    private String cover;
    private boolean star;
    private boolean love;

    public EpisodeList(int id, String title, String cover,String url,String description, int podcastId, boolean star,boolean download, boolean love ) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.star = star;
        this.love = love;
        this.download = download;
        this.podcastId = podcastId;
        this.description = description;
        this.url = url;
    }

    private boolean download;
    private int podcastId;
    private String description;
    private String url;


    public boolean getStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public boolean getLove() {
        return love;
    }

    public void setLove(boolean love) {
        this.love = love;
    }

    public boolean getDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }


    public int getPodcastId() {
        return podcastId;
    }

    public void setPodcastId(int podcastId) {
        this.podcastId = podcastId;
    }
    public EpisodeList(int id, String title, String cover, int podcastId, String description, String url) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.podcastId = podcastId;
        this.description = description;
        this.url = url;
    }



    public EpisodeList() {
    }

    public EpisodeList(String title, String cover, String description, String url) {
        this.title = title;
        this.cover = cover;
        this.description = description;
        this.url = url;
    }

    public EpisodeList(int id, String title, String cover, String description, String url) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.description = description;
        this.url = url;
    }





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
