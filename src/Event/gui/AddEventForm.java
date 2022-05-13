/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Event.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.gui.BaseForm;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.entities.Event_type;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.entities.services.ServiceEventType;
import com.mycompany.myapp.entities.services.ServiceEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Emna
 */

public class AddEventForm extends BaseForm{
    
    
    Form current;
    public AddEventForm(Resources res ) {
         super("AddEventForm", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("AddEventForm");
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
      
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
    
        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
      
        TextField nom_event = new TextField("", "entrer nom_event!!");
        nom_event.setUIID("TextFieldBlack");
        addStringValue("nom_event",nom_event);
        
        TextField event_description = new TextField("", "entrer event_description!!");
        event_description.setUIID("TextFieldBlack");
        addStringValue("event_description",event_description);
        
        TextField event_theme = new TextField("", "entrer event_theme!!");
        event_theme.setUIID("TextFieldBlack");
        addStringValue("event_theme",event_theme);

        Picker date_debut = new Picker();
        date_debut.setUIID("TextFieldBlack");
        date_debut.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        addStringValue("date_debut",date_debut);

        Picker date_fin = new Picker();
        date_fin.setUIID("TextFieldBlack");
        date_fin.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        addStringValue("date_fin",date_fin);

        TextField event_status = new TextField("", "entrer event_status!!");
        event_status.setUIID("TextFieldBlack");
        addStringValue("event_status",event_status);


        TextField nbr_participants = new TextField("", "entrer nbr_participants!!");
        nbr_participants.setUIID("TextFieldBlack");
        addStringValue("nbr_participants",nbr_participants);

        TextField lieu = new TextField("", "entrer lieu!!");
        lieu.setUIID("TextFieldBlack");
        addStringValue("lieu",lieu);
        
        ComboBox<Event_type> type = new ComboBox();
        type.setUIID("TextFieldBlack");
        addStringValue("type",type);
        
        ComboBox<User> client = new ComboBox();
        client.setUIID("TextFieldBlack");
        addStringValue("client",client);
        
        
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);
        
        
        
        for (Event_type et : ServiceEventType.getInstance().affichageEventtype()) {
            type.addItem(et);
        }
        
        for (User u : ServiceEvent.getInstance().affichageClients()) {
            client.addItem(u);
        }
        
        
        //onclick button event 

        btnAjouter.addActionListener((e) -> {
            
            
            try {
                
                if(nom_event.getText().equals("") || event_description.getText().equals("") 
                        || event_theme.getText().equals("") 
                        || date_debut.getText().equals("") || date_fin.getText().equals("") || event_status.getText().equals("")
                        || nbr_participants.getText().equals("") || lieu.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                }
                
                else {
                    InfiniteProgress ip = new InfiniteProgress();; 
                
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    
                    Event event = new Event();
                    
                
                    event.setNom_event(nom_event.getText());
                    event.setEvent_theme(event_theme.getText());
                    event.setEvent_description(event_description.getText());
                    Calendar c = Calendar.getInstance();
                    c.setTime(date_debut.getDate());
                    event.setDate_debut(String.valueOf(date_debut.getDate()));
                    event.setDate_fin(String.valueOf(date_fin.getDate()));
                    event.setEvent_status(event_status.getText());
                    event.setNbr_participants(Integer.parseInt(nbr_participants.getText()));
                    event.setLieu(lieu.getText());
                    event.setId_type(type.getSelectedItem());
                    event.setId_client(client.getSelectedItem());
                    event.setImage_event("defaultimage.png");
                    
                    ServiceEvent.getInstance().ajoutEvent(event);
                    ServiceEvent.getInstance().sendsms("53328112", nom_event.getText());
                    iDialog.dispose();
                    Dialog.show("Add Event", "Successfully added",new Command("OK"));
                    getAllEvent aa= new getAllEvent(res);
                    aa.show();
                    refreshTheme();
                            
                }
                
            }catch(Exception ex ) {
                ex.printStackTrace();
            }
            
            
            
            
            
        });
        
        
    }

    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
    }

    
    
    
    public void bindButtonSelection(Button btn , Label l ) {
        
        btn.addActionListener(e-> {
        if(btn.isSelected()) {
            updateArrowPosition(btn,l);
        }
    });
    }

    private void updateArrowPosition(Button btn, Label l) {
        
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()  / 2  - l.getWidth() / 2 );
        l.getParent().repaint();
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
    
    
   
   
    
}
