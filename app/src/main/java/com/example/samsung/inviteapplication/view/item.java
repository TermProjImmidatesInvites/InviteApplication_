package com.example.samsung.inviteapplication.view;

public class item {

    private int imgResource;
    private int imgResource2;
    private String line;
    private String line1;

    public item(int imgResource, String line) {
        this.imgResource = imgResource;
        this.line = line;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public String getLine() {
        return line;
    }

    public item(int imgResource, String line, String line1) {
        this.imgResource = imgResource;
        this.line = line;
        this.line1 = line1;
    }

    public item(int imgResource, int imgResource2, String line, String line1) {
        this.imgResource = imgResource;
        this.imgResource2 = imgResource2;
        this.line = line;
        this.line1 = line1;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getImgResource2() {
        return imgResource2;
    }

    public void setImgResource2(int imgResource2) {
        this.imgResource2 = imgResource2;
    }
}
