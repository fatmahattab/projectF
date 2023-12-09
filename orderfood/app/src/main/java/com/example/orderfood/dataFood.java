package com.example.orderfood;

public class dataFood {
    private String nom;
    private String description;
    private String image;
    private String prix;
    private String key;


    public dataFood(String nom, String description, String image, String prix) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
    }
    public dataFood(){
    }
    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getPrix() {
        return prix;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
}
