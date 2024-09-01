package com.example.soundworld.Database;
/*
 * @author: Dearseven
 * @description:
 */

public class CoverItem {
    private int image;

    public CoverItem(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private String title;

    private String author;

//    @ColumnInfo(name = "artwork_image_url")
    private String coverImageUrl;

}
