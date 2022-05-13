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
public class TypeComptabilite {
    
    private int id;
    private String type;
    private float montant;

    public TypeComptabilite() {
    }

    public TypeComptabilite(int id, String type, float montant) {
        this.id = id;
        this.type = type;
        this.montant = montant;
    }

    public TypeComptabilite(String type, float montant) {
        this.type = type;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    
   
    
}
