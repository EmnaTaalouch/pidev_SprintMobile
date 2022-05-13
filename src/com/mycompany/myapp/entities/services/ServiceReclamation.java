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
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.entities.Reclamation;

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
public class ServiceReclamation {

    public ArrayList<Reclamation> reclamations;
    
    public static ServiceReclamation instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceReclamation() {
         req = new ConnectionRequest();
    }

    public static ServiceReclamation getInstance() {
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }
    


    public ArrayList<Reclamation> parseReclamations(String jsonText){
                try {

                    
            System.out.println(jsonText);
            reclamations=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Reclamation a = new Reclamation();
                                
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);
                a.setDescription(obj.get("description").toString());
                a.setImage(obj.get("image").toString());
                               
                String test=obj.get("user").toString();
                int i = (int) Float.parseFloat(test.substring((test).indexOf("id=")+3 ,(test).indexOf(", state")));
                a.setIduser(i);
                
                
                        try {  
                            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("dateReclamation").toString());
                            a.setDateC(date1);

                        } catch (ParseException ex) {
                            System.out.println(ex);
                        }

                System.out.println();
                

                reclamations.add(a);


            }
        } catch (IOException ex) {
            
        }
        return reclamations;
    }
    public ArrayList<Reclamation> getAllReclamations(){
        String url = Statics.BASE_URL+"reclamation/mobile/aff";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reclamations = parseReclamations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return reclamations;
    }


    public boolean addReclamation(Reclamation u) {
        String url = Statics.BASE_URL + "reclamation/mobile/new?description="+u.getDescription()+"&image="+u.getImage()+"&iduser="+u.getIduser(); //création de l'URL
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

        public boolean editReclamation(Reclamation u) {
        String url = Statics.BASE_URL + "reclamation/mobile/edit?id="+u.getId()+"&description="+u.getDescription()+"&image="+u.getImage(); //création de l'URL
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

    public boolean deleteReclamation(int id) {
        String url = Statics.BASE_URL + "reclamation/mobile/del?id=" + id;
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
