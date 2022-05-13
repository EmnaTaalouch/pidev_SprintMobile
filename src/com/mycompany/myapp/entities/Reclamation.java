/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author msi
 */
public class Reclamation {
    
    private int id;
    private String description;
    private String image;
    private Date dateC;
    private int iduser;

    public Reclamation() {
    }

    public Reclamation(int id, String description, String image, Date dateC, int iduser) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.dateC = dateC;
        this.iduser = iduser;
    }

    public Reclamation(String description, String image, int iduser) {
        this.description = description;
        this.image = image;
        this.iduser = iduser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateC() {
        return dateC;
    }

    public void setDateC(Date dateC) {
        this.dateC = dateC;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

}
