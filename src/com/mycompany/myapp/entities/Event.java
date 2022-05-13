/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;


import java.util.Date;

/**
 *
 * @author Emna
 */
public class Event {
    private int id;
    private String nom_event;
    private String event_description;
    private String event_theme;
    private String date_debut;
    private String date_fin;
    private String event_status;
    private String demande_status;
    private User id_client;
    private Event_type id_type;
    private int nbr_participants;
    private String lieu;
    private String image_event;
    



    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_event() {
        return nom_event;
    }

    public void setNom_event(String nom_event) {
        this.nom_event = nom_event;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getEvent_theme() {
        return event_theme;
    }

    public void setEvent_theme(String event_theme) {
        this.event_theme = event_theme;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getEvent_status() {
        return event_status;
    }

    public void setEvent_status(String event_status) {
        this.event_status = event_status;
    }

    public String getDemande_status() {
        return demande_status;
    }

    public void setDemande_status(String demande_status) {
        this.demande_status = demande_status;
    }

    public User getId_client() {
        return id_client;
    }

    public void setId_client(User id_client) {
        this.id_client = id_client;
    }

   

    public Event_type getId_type() {
        return id_type;
    }

    public void setId_type(Event_type id_type) {
        this.id_type = id_type;
    }

    public int getNbr_participants() {
        return nbr_participants;
    }

    public void setNbr_participants(int nbr_participants) {
        this.nbr_participants = nbr_participants;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getImage_event() {
        return image_event;
    }

    public void setImage_event(String image_event) {
        this.image_event = image_event;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", nom_event=" + nom_event + ", event_description=" + event_description + ", event_theme=" + event_theme + ", date_debut=" + date_debut + ", date_fin=" + date_fin + ", event_status=" + event_status + ", demande_status=" + demande_status + ", id_client=" + id_client + ", id_type=" + id_type + ", nbr_participants=" + nbr_participants + ", lieu=" + lieu + ", image_event=" + image_event + '}';
    }


    
    
    
    
    

    

   
}
