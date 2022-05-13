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
public class Comptabilite {
    
    private int id;
    private String libelle;
    private String description;
    private Date dateC;
    private TypeComptabilite typec;

    public Comptabilite() {
    }

    public Comptabilite(String libelle, String description, Date dateC, TypeComptabilite typec) {
        this.libelle = libelle;
        this.description = description;
        this.dateC = dateC;
        this.typec = typec;
    }

    public Comptabilite(int id, String libelle, String description, Date dateC, TypeComptabilite typec) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.dateC = dateC;
        this.typec = typec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateC() {
        return dateC;
    }

    public void setDateC(Date dateC) {
        this.dateC = dateC;
    }

    public TypeComptabilite getTypec() {
        return typec;
    }

    public void setTypec(TypeComptabilite typec) {
        this.typec = typec;
    }

    
    
}
