/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Emna
 */

public class Event_type {
    
    private int id;
    private String libelle;

    public Event_type(String libelle) {
        this.libelle = libelle;
    }

    public Event_type(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Event_type() {
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

    @Override
    public String toString() {
        return this.libelle;
    }
    
    
    
}
