/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author msi
 */
public class Relationnel {
    
    private int id;
    private String nom;
    private String description;
    private String image;
    private Categorie categorie;
    private float rating;

    public Relationnel() {
    }

    public Relationnel(int id, String nom, String description, String image, Categorie categorie, float rating) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.categorie = categorie;
        this.rating = rating;
    }

    public Relationnel(String nom, String description, String image, Categorie categorie, float rating) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.categorie = categorie;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
    
    

    
}
