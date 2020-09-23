package com.ab.farm.Model;

public class CropModel {

    String id;
    String naem;
    String image;
    String type;
    String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaem() {
        return naem;
    }

    public void setNaem(String naem) {
        this.naem = naem;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CropModel() {
    }

    public CropModel(String id, String naem, String image, String type, String status) {
        this.id = id;
        this.naem = naem;
        this.image = image;
        this.type = type;
        this.status = status;
    }
}
