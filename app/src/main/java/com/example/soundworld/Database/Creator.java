package com.example.soundworld.Database;
/*
 * @author: Dearseven
 * @description:
 */

public class Creator {
    private int id;
    private String creatorName;

    public Creator(int id, String creatorName, String creatorImage, int podcastId) {
        this.id = id;
        this.creatorName = creatorName;
        this.creatorImage = creatorImage;
        this.podcastId = podcastId;
    }

    private String creatorImage;

    public String getCreatorImage() {
        return creatorImage;
    }

    public void setCreatorImage(String creatorImage) {
        this.creatorImage = creatorImage;
    }

    public int getId() {
        return id;
    }

    public Creator(int id, String creatorName, int podcastId, int images) {
        this.id = id;
        this.creatorName = creatorName;
        this.podcastId = podcastId;
        this.images = images;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPodcastId() {
        return podcastId;
    }

    public void setPodcastId(int podcastId) {
        this.podcastId = podcastId;
    }

    private int podcastId;

    private int images;

    public Creator(String creatorName, int images) {
        this.creatorName = creatorName;
        this.images = images;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }
}
