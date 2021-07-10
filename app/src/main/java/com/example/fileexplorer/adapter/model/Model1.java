package com.example.fileexplorer.adapter.model;

public class Model1 {
    String text1;
    int Image;

    public Model1(String text1, int image) {
        this.text1 = text1;
        Image = image;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
