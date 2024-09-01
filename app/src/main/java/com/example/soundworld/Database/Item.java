package com.example.soundworld.Database;

//对应main_item中的每一个元素
//表示数据
public class Item {
    private String title;
    private String content;
    private int img;

    public Item(String title, int img){
        this.title=title;
        this.img=img;
    }
    public Item(String title, String content, int img){
        this.title=title;
        this.content = content;
        this.img=img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getcontent() { return content;}
    public void setContent(String content) {
        this.content = content;
    }


}



