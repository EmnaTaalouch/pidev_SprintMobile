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
import com.mycompany.myapp.entities.Event_type;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Emna
 */
public class ServiceEventType {
   
    
    //singleton 
    public static ServiceEventType instance = null ;
    
    public static boolean resultOk = true;
            public ArrayList<Event_type> tasks; 
        public LinkedHashMap<String, Object> empl = new LinkedHashMap<>();

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceEventType getInstance() {
        if(instance == null )
            instance = new ServiceEventType();
        return instance ;
    }
    
    
    
    public ServiceEventType() {
        req = new ConnectionRequest();
        
    }
    
    
    //ajout 
    public void ajoutEventType(Event_type eventtype) {
        
        String url =Statics.BASE_URL+"api/ajoutereventtypejson/"+eventtype.getLibelle();
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    
           public ArrayList<Event_type> parseEventTypes(String jsonText){
        try {
           tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
           
            for(Map<String,Object> obj : list){      
                   User u = new User(); 
                    Event_type et = new Event_type();
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        String libelle = obj.get("libelle").toString();
                        
                        
                        et.setId((int)id);
                        et.setLibelle(libelle);
                tasks.add(et);
            }         
        } catch (IOException ex) {
            
        }
        return tasks;
    }
    
    
        public ArrayList<Event_type>affichageEventtype() {
        ArrayList<User> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"api/getalleventtypess";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseEventTypes(new String(req.getResponseData()));
                System.out.println("Task : " + tasks);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
        
        
    }
    
    
    
    
    //Delete 
    public boolean deleteEventtype(int id ) {
        
        String url = Statics.BASE_URL +"api/supprimereventtypejson/"+id;
        
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
    public boolean modifierEvent(Event_type event) {
        String url = Statics.BASE_URL +"api/modifiereventtypejson/"+event.getId()+"/"+event.getLibelle();
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
    

    
}
