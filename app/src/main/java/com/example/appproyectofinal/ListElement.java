package com.example.appproyectofinal;

public class ListElement {
    public String color;
    public String name;
    public String img;
    public String permission;

    public ListElement(String color, String name, String img, String permission) {
        this.color = color;
        this.name = name;
        this.img = img;
        this.permission = permission;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) { this.permission = permission; }
}
