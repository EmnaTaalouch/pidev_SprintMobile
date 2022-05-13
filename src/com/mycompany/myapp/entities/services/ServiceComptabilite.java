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
import com.mycompany.myapp.entities.Comptabilite;
import com.mycompany.myapp.entities.Comptabilite;
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
public class ServiceComptabilite {

    public ArrayList<Comptabilite> comptabilites;
    
    public static ServiceComptabilite instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceComptabilite() {
         req = new ConnectionRequest();
    }

    public static ServiceComptabilite getInstance() {
        if (instance == null) {
            instance = new ServiceComptabilite();
        }
        return instance;
    }
    


    public ArrayList<Comptabilite> parseComptabilites(String jsonText){
                try {

                    System.out.println(jsonText);
            comptabilites=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Comptabilite a = new Comptabilite();
                                
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);
                a.setLibelle(obj.get("libelle").toString());
                a.setDescription(obj.get("description").toString());
                                try {  
                            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("date").toString());
                            a.setDateC(date1);

                        } catch (ParseException ex) {
                            System.out.println(ex);
                        }
                               

                String test=obj.get("idType").toString();
                                
                System.out.println();
                
                int i = (int) Float.parseFloat(test.substring((test).indexOf("id=")+3 ,(test).indexOf(", type")));
                TypeComptabilite cat = new TypeComptabilite();
                cat.setId(i);
                                
                cat.setType(test.substring((test).indexOf("type=")+5 ,(test).indexOf(", montant")));
                cat.setMontant(Float.parseFloat(test.substring((test).indexOf("montant=")+8 ,(test).indexOf(", __isInitialized"))));
                
                a.setTypec(cat);

                comptabilites.add(a);


            }
        } catch (IOException ex) {
            
        }
        return comptabilites;
    }
    public ArrayList<Comptabilite> parseComptabilitesstat(String jsonText){
                try {

                    System.out.println(jsonText);
            comptabilites=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Comptabilite a = new Comptabilite();
                                
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);
                a.setLibelle(obj.get("libelle").toString());
                a.setDescription(obj.get("description").toString());
                                try {  
                            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("date").toString());
                            a.setDateC(date1);

                        } catch (ParseException ex) {
                            System.out.println(ex);
                        }
                               

                String test=obj.get("idType").toString();
                                
                System.out.println();
                
                int i = (int) Float.parseFloat(test.substring((test).indexOf("id=")+3 ,(test).indexOf(", type")));
                TypeComptabilite cat = new TypeComptabilite();
                cat.setId(i);
                                
                
                a.setTypec(cat);

                comptabilites.add(a);


            }
        } catch (IOException ex) {
            
        }
        return comptabilites;
    }    
    
    public ArrayList<Comptabilite> getAllComptabilites(){
        String url = Statics.BASE_URL+"comptabilite/mobile/aff";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                comptabilites = parseComptabilites(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return comptabilites;
    }
    public ArrayList<Comptabilite> getByTypeComptabilites(int id){
        String url = Statics.BASE_URL+"comptabilite/mobile/finbytype?id="+id;
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                comptabilites = parseComptabilitesstat(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return comptabilites;
    }


    public boolean addComptabilite(Comptabilite u) {
        String url = Statics.BASE_URL + "comptabilite/mobile/new?libelle="+u.getLibelle()+"&description="+u.getDescription()+"&datec="+u.getDateC()+"&type="+u.getTypec().getType(); //création de l'URL
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

        public boolean editComptabilite(Comptabilite u) {
        String url = Statics.BASE_URL + "comptabilite/mobile/edit?id="+u.getId()+"&libelle="+u.getLibelle()+"&description="+u.getDescription()+"&datec="+u.getDateC()+"&type="+u.getTypec().getType(); //création de l'URL
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

    public boolean deleteComptabilite(int id) {
        String url = Statics.BASE_URL + "comptabilite/mobile/del?id=" + id;
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
