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
import com.mycompany.myapp.entities.Reponse;

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
public class ServiceReponse {

    public ArrayList<Reponse> reponses;
    
    public static ServiceReponse instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceReponse() {
         req = new ConnectionRequest();
    }

    public static ServiceReponse getInstance() {
        if (instance == null) {
            instance = new ServiceReponse();
        }
        return instance;
    }
    


    public ArrayList<Reponse> parseReponses(String jsonText){
                try {

                    System.out.println(jsonText);
            reponses=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Reponse a = new Reponse();
                                
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);
                a.setText(obj.get("text").toString());
                
                String test=obj.get("reclamations").toString();
                System.out.println(test);
                int i = (int) Float.parseFloat(test.substring((test).indexOf(", id=")+5 ,(test).indexOf(", description")));
                a.setIdrec(i);

                reponses.add(a);


            }
        } catch (IOException ex) {
            
        }
        return reponses;
    }
    public ArrayList<Reponse> getAllReponses(){
        String url = Statics.BASE_URL+"reponse/mobile/affAll";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reponses = parseReponses(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return reponses;
    }

        public ArrayList<Reponse> getReponses(int id){
        String url = Statics.BASE_URL+"reponse/mobile/aff?id="+id;
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reponses = parseReponses(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return reponses;
    }


    public boolean addReponse(Reponse u) {
        String url = Statics.BASE_URL + "reponse/mobile/new?text="+u.getText()+"&idrec="+u.getIdrec(); //création de l'URL
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

        public boolean editReponse(Reponse u) {
        String url = Statics.BASE_URL + "reponse/mobile/edit?id="+u.getId()+"&text="+u.getText(); //création de l'URL
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

    public boolean deleteReponse(int id) {
        String url = Statics.BASE_URL + "reponse/mobile/del?id=" + id;
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
