package com.example.samsung.inviteapplication.models;

public class invite {
    String Name, Msg, Usn;
    byte[] Image;
    int Id, img;

    public invite(String name, String msg,String usn, byte[] image) {
        Name = name;
        Msg = msg;
        Usn = usn;
        Image = image;
    }

    public invite(int id, String name, String msg,String usn, int image) {
        Id = id;
        Name = name;
        Msg = msg;
        Usn = usn;
        img = image;
    }

    public invite(int id, String name, String msg,String usn, byte[] image) {
        Id = id;
        Name = name;
        Msg = msg;
        Usn = usn;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public byte[] getImage() {
        return Image;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

}
