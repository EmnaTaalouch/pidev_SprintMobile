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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Relationnel;
import com.mycompany.myapp.entities.Relationnel;

import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author msi
 */
public class ServiceRelationnel {

    public ArrayList<Relationnel> relationnels;
    
    public static ServiceRelationnel instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceRelationnel() {
         req = new ConnectionRequest();
    }

    public static ServiceRelationnel getInstance() {
        if (instance == null) {
            instance = new ServiceRelationnel();
        }
        return instance;
    }
    


    public ArrayList<Relationnel> parseRelationnels(String jsonText){
                try {

                    System.out.println(jsonText);
            relationnels=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Relationnel a = new Relationnel();
                                
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);
                a.setNom(obj.get("nom").toString());
                a.setDescription(obj.get("description").toString());
                a.setImage(obj.get("image").toString());
                a.setRating(Float.parseFloat(obj.get("rating").toString()));
                               
                String test=obj.get("categorie").toString();
                                
                System.out.println();
                
                int i = (int) Float.parseFloat(test.substring((test).indexOf("id=")+3 ,(test).indexOf(", role")));
                Categorie cat = new Categorie();
                cat.setId(i);
                                
                cat.setRole(test.substring((test).indexOf("role=")+5 ,(test).indexOf(", __isInitialized")));
                
                a.setCategorie(cat);

                relationnels.add(a);


            }
        } catch (IOException ex) {
            
        }
        return relationnels;
    }
    public ArrayList<Relationnel> getAllRelationnels(){
        String url = Statics.BASE_URL+"relationnel/mobile/aff";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                relationnels = parseRelationnels(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return relationnels;
    }


    public boolean addRelationnel(Relationnel u) {
        String url = Statics.BASE_URL + "relationnel/mobile/new?nom="+u.getNom()+"&description="+u.getDescription()+"&image="+u.getImage()+"&role="+u.getCategorie().getRole(); //création de l'URL
               req.setUrl(url);
               System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

        public boolean editRelationnel(Relationnel u) {
        String url = Statics.BASE_URL + "relationnel/mobile/edit?id="+u.getId()+"&nom="+u.getNom()+"&description="+u.getDescription()+"&image="+u.getImage()+"&role="+u.getCategorie().getRole()+"&rating="+u.getRating(); 
               req.setUrl(url);
               System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean deleteRelationnel(int id) {
        String url = Statics.BASE_URL + "relationnel/mobile/del?id=" + id;
               req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

}
