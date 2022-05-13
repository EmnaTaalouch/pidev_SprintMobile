/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities.services;


import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.entities.Event_type;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author Emna
 */

public class ServiceEvent {
    
    //singleton 
    public static ServiceEvent instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
        public ArrayList<User> tasks; 
        public ArrayList<Event> Eventtasks; 
        public LinkedHashMap<String, Object> user = new LinkedHashMap<>();
        public LinkedHashMap<String, Object> et = new LinkedHashMap<>();
        private final static String ACCOUNT_SID = "ACb3d0e0eced06ffd7beec5ff336b6b389";
        private final static String AUTH_TOKEN = "c4a5a0890ecfa32fc51b0d75d89f7a4d";
    
    
    public static ServiceEvent getInstance() {
        if(instance == null )
            instance = new ServiceEvent();
        return instance ;
    }
    
    
    
    public ServiceEvent() {
        req = new ConnectionRequest();
        
    }
    
    
    //ajout 
    public void ajoutEvent(Event event) {
        
        String url =Statics.BASE_URL+"api/ajoutereventjson/"+event.getNom_event()+"/"+event.getEvent_description()+"/"+event.getEvent_theme()+"/"+event.getDate_debut()+"/"+event.getDate_fin()+"/"+
        event.getEvent_status()+"/"+event.getId_client().getId()+"/"+event.getId_type().getId()+"/"+event.getNbr_participants()+"/"+event.getLieu()+"/"+event.getImage_event();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    
    
  
       public ArrayList<User> parseClients(String jsonText){
        try {
           tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
           
            for(Map<String,Object> obj : list){      
                   User u = new User(); 
                        float id = Float.parseFloat(obj.get("id").toString());
                        String nom = obj.get("Nom").toString();
                        String prenom = obj.get("Prenom").toString();
                        String login = obj.get("username").toString();
                        String password = obj.get("password").toString();

                        u.setId((int)id);
                        u.setNom(nom);
                        u.setPrenom(prenom);
                        u.setLogin(login);
                        u.setPassword(password);
                tasks.add(u);
            }         
        } catch (IOException ex) {
            
        }
        return tasks;
    }
       public ArrayList<Event> parseEvents(String jsonText){
        try {
           Eventtasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
           
            for(Map<String,Object> obj : list){      
                    Event e = new Event();
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        String nom_event = obj.get("nomEvent").toString();
                        String event_description = obj.get("eventDescription").toString();
                        String event_theme = obj.get("eventTheme").toString();
                        String event_status = obj.get("eventStatus").toString();
                        float nbr_participants = Float.parseFloat(obj.get("nbrParticipants").toString());
                        String lieu = obj.get("lieu").toString();
                        String image_event = obj.get("imageEvent").toString();
                        String date_debut = obj.get("dateDebut").toString().substring(0,obj.get("dateDebut").toString().indexOf('T') );
                        String date_fin = obj.get("dateFin").toString().substring(0,obj.get("dateFin").toString().indexOf('T') );
                          ////////////////EventType//////////////
                et = (LinkedHashMap) obj.get("idType");
                Event_type m = new Event_type();
                for (Map.Entry<String, Object> entry : et.entrySet()) {
                    if (entry.getKey().contains("libelle")) {
                        m.setLibelle(entry.getValue().toString());
                    }
                    if (entry.getKey().contains("id")) {
                        String pp = entry.getValue().toString();
                  //      System.out.println(pp);
                        float idd = Float.parseFloat(pp);
                        m.setId((int)idd);
                    }
                }
              ///////////////End EventType///////////////
              //////////////User////////////////
                           user = (LinkedHashMap) obj.get("idClient");
                User u = new User();
                for (Map.Entry<String, Object> entry : user.entrySet()) {
                    if (entry.getKey().contains("nom")) {
                        u.setNom(entry.getValue().toString());
                    }
                    if (entry.getKey().contains("prenom")) {
                        u.setPrenom(entry.getValue().toString());
                    }
                    if (entry.getKey().contains("username")) {
                        u.setLogin(entry.getValue().toString());
                    }
                    if (entry.getKey().contains("password")) {
                        u.setPassword(entry.getValue().toString());
                    }
                   
                    
                    if (entry.getKey().contains("id")) {
                        String pp = entry.getValue().toString();
                    //    System.out.println(pp);
                        int idd =(int) Float.parseFloat(pp);
                        u.setId((int)idd);
                    }

                }
              ///////////////End User///////////////
                        e.setId((int)id);
                        e.setNom_event(nom_event);
                        e.setEvent_description(event_description);
                        e.setEvent_theme(event_theme);
                        e.setEvent_status(event_status);
                        e.setNbr_participants((int)nbr_participants);
                        e.setLieu(lieu);
                        e.setImage_event(image_event);
                        e.setDate_debut(date_debut);
                        e.setDate_fin(date_fin);
                        e.setId_client(u);
                        e.setId_type(m);
                        
                Eventtasks.add(e);
            }         
        } catch (IOException ex) {
            
        }
        return Eventtasks;
    }
            public ArrayList<Event>affichageEvent() {
        ArrayList<Event> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"api/getallevents";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Eventtasks = parseEvents(new String(req.getResponseData()));
                System.out.println("Task : " + tasks);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Eventtasks;
        
        
    }
    
        public ArrayList<User>affichageClients() {
        ArrayList<User> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"api/getallclients";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseClients(new String(req.getResponseData()));
                System.out.println("Task : " + tasks);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
        
        
    }
    
    
    
    
    //Delete 
    public boolean deleteEvent(int id ) {
        
        String url = Statics.BASE_URL +"api/supprimereventjson/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
    
    
    
    //Update 
    public boolean modifierEvent(Event event) {
        String url = Statics.BASE_URL +"api/modifiereventjson/"+event.getId()+"/"+event.getNom_event()+"/"+event.getEvent_description()+"/"+event.getEvent_theme()+"/"+event.getDate_debut()+"/"+event.getDate_fin()+"/"+
                event.getEvent_status()+"/"+event.getId_client().getId()+"/"+event.getId_type().getId()+"/"+event.getNbr_participants()+"/"+event.getLieu()+"/"+event.getImage_event();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
    
        public void sendsms(String str, String nomevent) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+216" + str), // To number
                new PhoneNumber("+15038324523"), // From number
                "votre evenement "+nomevent+" a ete crée avec success "
        ).create();
        System.out.println(message.getSid());
    }

    

    
}