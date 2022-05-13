/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.myapp.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.services.ServiceEvent;

/**
 * The g form
 *
 * @author Shai Almog
 */
public class getAllEvent extends BaseForm {

    public getAllEvent(Resources res) {
        super("getAllEvent", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("getAllEvent");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});
        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("news-item.jpg"), spacer1, "", "", "");
        addTab(swipe, res.getImage("dog.jpg"), spacer2, "", "", "");
                
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
                
        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        
        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("Ajouter", barGroup);
        all.setUIID("SelectBar");
        all.setPreferredW(999);
        all.addActionListener((evt) -> {
            AddEventForm aef = new AddEventForm(res);
            aef.show();
        });
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all),
                FlowLayout.encloseBottom(arrow)
        ));
        
        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        for(Event e : ServiceEvent.getInstance().affichageEvent()){
           addButton(res.getImage("happy.jpg"),e.getNom_event(),e.getDate_debut(),e.getDate_fin(),e.getEvent_status(),String.valueOf(e.getNbr_participants()),e.getLieu(),e.getId_client().getNom(),e.getId_type().getLibelle(),e.getId(),res,e ); 
        }
        
    }
    
    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
        
        
    }
    
    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
                            new SpanLabel(text, "LargeWhiteText"),
                           
                            spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }
    
   private void addButton(Image img, String nom_event,String date_debut,String date_fin,String event_status,String nbr,String lieu,String client,String type,int id,Resources res,Event e ) {
       int height = Display.getInstance().convertToPixels(11.5f);
       int width = Display.getInstance().convertToPixels(14f);
       Button image = new Button(img.fill(width, height));
       image.setUIID("Label");
       Container cnt = BorderLayout.west(image);
       //cnt.setLeadComponent(image);
       Label spacer1 = new Label("=================================================================");
       TextArea tNom = new TextArea("nom_event: "+nom_event);
       TextArea tDateD = new TextArea("date_debut: "+date_debut);
       TextArea tDateF = new TextArea("date_fin: "+date_fin);
       TextArea tStatus = new TextArea("event_status: "+event_status);
       TextArea tNbr = new TextArea("nbr: "+nbr);
       TextArea tLieu = new TextArea("lieu: "+lieu);
       TextArea tClient = new TextArea("client: "+client);
       TextArea tType = new TextArea("type: "+type);
       tNom.setUIID("NewsTopLine");
       tNom.setEditable(false);
       tDateD.setUIID("NewsTopLine");
       tDateD.setEditable(false);
       tDateF.setUIID("NewsTopLine");
       tDateF.setEditable(false);
       tStatus.setUIID("NewsTopLine");
       tStatus.setEditable(false);
       tNbr.setUIID("NewsTopLine");
       tNbr.setEditable(false);
       tLieu.setUIID("NewsTopLine");
       tLieu.setEditable(false);
       tClient.setUIID("NewsTopLine");
       tClient.setEditable(false);
       tType.setUIID("NewsTopLine");
       tType.setEditable(false);
       
       Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Button btnEdit = new Button("Modifier");
            Button btnDelete = new Button("Supprimer");
        btnDelete.addActionListener((evt) -> {
            ServiceEvent.getInstance().deleteEvent(id);
            Dialog.show("Remove Event", "Successfully deleted",new Command("OK"));
            getAllEvent gae= new getAllEvent(res);
            gae.show();
        });
        btnEdit.addActionListener((evt) -> {
            EditEventForm eef= new EditEventForm(res, e);
            eef.show();
        });
           
       //,BoxLayout.encloseX(tDateF,tStatus),BoxLayout.encloseX(tNbr,tLieu),BoxLayout.encloseX(tClient,tType)
       add(cnt);
       cnt.add(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE, 
               BoxLayout.encloseY(
                       tNom,tDateD,
                       tDateF,tStatus,
                       tNbr,tLieu,
                      tClient,tType,BoxLayout.encloseX(btnEdit,btnDelete),spacer1
               ));
       
      // image.addActionListener(e -> ToastBar.showMessage(nom_event, FontImage.MATERIAL_INFO));
   }
    
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
    
}
