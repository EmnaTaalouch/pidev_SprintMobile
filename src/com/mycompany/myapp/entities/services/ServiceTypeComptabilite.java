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
import com.mycompany.myapp.entities.TypeComptabilite;
import com.mycompany.myapp.entities.TypeComptabilite;

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
public class ServiceTypeComptabilite {

    public ArrayList<TypeComptabilite> typeComptabilites;
    
    public static ServiceTypeComptabilite instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceTypeComptabilite() {
         req = new ConnectionRequest();
    }

    public static ServiceTypeComptabilite getInstance() {
        if (instance == null) {
            instance = new ServiceTypeComptabilite();
        }
        return instance;
    }
    


    public ArrayList<TypeComptabilite> parseTypeComptabilites(String jsonText){
                try {

                    System.out.println(jsonText);
            typeComptabilites=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                TypeComptabilite a = new TypeComptabilite();
                                
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);
                a.setType(obj.get("type").toString());
                a.setMontant(Float.parseFloat(obj.get("montant").toString()));

                typeComptabilites.add(a);


            }
        } catch (IOException ex) {
            
        }
        return typeComptabilites;
    }
    public ArrayList<TypeComptabilite> getAllTypeComptabilites(){
        String url = Statics.BASE_URL+"typecomptabilite/mobile/aff";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                typeComptabilites = parseTypeComptabilites(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return typeComptabilites;
    }


    public boolean addTypeComptabilite(TypeComptabilite u) {
        String url = Statics.BASE_URL + "typecomptabilite/mobile/new?type="+u.getType()+"&montant="+u.getMontant(); //création de l'URL
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

        public boolean editTypeComptabilite(TypeComptabilite u) {
        String url = Statics.BASE_URL + "typecomptabilite/mobile/edit?id="+u.getId()+"&type="+u.getType()+"&montant="+u.getMontant(); //création de l'URL
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

    public boolean deleteTypeComptabilite(int id) {
        String url = Statics.BASE_URL + "typecomptabilite/mobile/del?id=" + id;
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
