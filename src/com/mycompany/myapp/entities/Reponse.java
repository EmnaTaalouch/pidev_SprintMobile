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
public class Reponse {
    
    private int id;
    private String text;
    private int idrec;

    public Reponse() {
    }

    public Reponse(int id, String text, int idrec) {
        this.id = id;
        this.text = text;
        this.idrec = idrec;
    }

    public Reponse(String text, int idrec) {
        this.text = text;
        this.idrec = idrec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIdrec() {
        return idrec;
    }

    public void setIdrec(int idrec) {
        this.idrec = idrec;
    }

    
    
}
