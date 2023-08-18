package windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.plaf.ColorUIResource;

import component.Components;
import component.SetDimension;
import socket.ClientSocket;


/* class description:
        name: HomeWindow
        work: show all data bases created by user; allow to create new(max 4), delete, rename and open.
        main function: runHomeWindow()
        sub functions: 
        output: return a database selected by user for further working; return "LOGOUT" string for back to login
*/

public class HomeWindow extends SetDimension implements  MouseListener
{
    // helper class:
    ClientSocket cs = new ClientSocket();


    // root components:
    Components cmp = new Components();
    JLabel pan1 = cmp.pan1;
    JLabel pan2 = cmp.pan2;
    JLabel panMenu = cmp.panMenu;


    // global:
    private String[] dataBaseNames;
    private String user;
    private int db_count;


    // flags:
    public boolean flogout = false;

    public boolean refresh_ = false;

    public boolean open_DB = false;
    public boolean rename_DB = false;
    public boolean delete_DB = false;
    public boolean create_DB = false;

    public boolean apply_ = false;
    public boolean cancel_ = false;

    public boolean confirm_ = false;
    public boolean back_ = false;

    public boolean abort_ = false;
    public boolean next_ = false;


    // refresh btn-label:
    JLabel refresh = new JLabel("Refresh");

    // selected dbs label:
    JLabel selected;


    // username label:
    JLabel username = new JLabel("@myusername");
    // logout btn-label:
    JLabel logout = new JLabel("Log out");



    // databases title label:
    JLabel dataBases_txt = new JLabel("D  A  T  A  -  B  A  S  E  S"); 

    // database labels array:
    JLabel[] dataBase = new JLabel[4]; 
    


    // database operation bg label:
    JLabel dbOperation = new JLabel("<");
    // all operation labels:
    JLabel open = new JLabel("Open");
    JLabel rename = new JLabel("Rename");
    JLabel delete = new JLabel("Delete");

    // create new database bottom labelL:
    JLabel createNew = new JLabel("create new |+|");



    // operation performing labels (from pan2) :
    JLabel ops_rename = new JLabel();
    // creating components for rename operation :
    JTextField textRename = new JTextField();
    JLabel apply = new JLabel("Apply");
    JLabel cancel = new JLabel("Cancel");

    JLabel ops_delete = new JLabel();
    // creating components for delete operation:
    JLabel confirm = new JLabel("Yes");
    JLabel back = new JLabel("Back");


    JLabel ops_createNew = new JLabel("required information!");
    // creating components for create operation:
    JLabel button_bg = new JLabel();
    JLabel abort = new JLabel("Abort");
    JLabel next = new JLabel("Next");

    JLabel input_bg = new JLabel();
    JLabel input_element = new JLabel("database name: ");
    JTextField inpute = new JTextField();






    // constructor:
    public HomeWindow()
    {
        // pan 2:
        pan2.setForeground(new Color(110, 10, 10));
        pan2.setFont(new Font("Baskerville", Font.BOLD, 20));
        pan2.setVerticalAlignment(JLabel.CENTER);
        pan2.setHorizontalAlignment(JLabel.CENTER);
        
        // logout btn-lable:
        logout.setBounds(sb.getMyXPose(xp.logout), sb.getMyYPose(yp.logout), sb.getMyWidth(wr.logout), sb.getMyHeight(hr.logout));
        logout.setBackground(new Color(50, 50, 50));
        logout.setForeground(new Color(0, 0, 0));
        logout.setFont(new Font("Baskerville", Font.BOLD, 14));
        logout.setVerticalAlignment(JLabel.CENTER);
        logout.setHorizontalAlignment(JLabel.CENTER);
        logout.setOpaque(true);
        logout.addMouseListener(this);

        // refresh btn-label:
        refresh.setBounds(sb.getMyXPose(xp.refresh), sb.getMyYPose(yp.refresh), sb.getMyWidth(wr.refresh), sb.getMyHeight(hr.refresh));
        refresh.setBackground(new Color(50, 50, 50));
        refresh.setForeground(new Color(0, 0, 0));
        refresh.setFont(new Font("Baskerville", Font.BOLD, 14));
        refresh.setVerticalAlignment(JLabel.CENTER);
        refresh.setHorizontalAlignment(JLabel.CENTER);
        refresh.setOpaque(true);
        refresh.addMouseListener(this);

        // username bg:
        username.setBounds(sb.getMyXPose(xp.usernameh), sb.getMyYPose(yp.usernameh), sb.getMyWidth(wr.usernameh), sb.getMyHeight(hr.usernameh));
        username.setBackground(new Color(38, 38, 42));
        username.setForeground(new Color(170, 170, 170));
        username.setFont(new Font("Monospaced", Font.ITALIC, 18));
        username.setVerticalAlignment(JLabel.CENTER);
        username.setHorizontalAlignment(JLabel.CENTER);
        username.setOpaque(true);

        // databases title bg:
        dataBases_txt.setBounds(sb.getMyXPose(xp.dataBases_txt), sb.getMyYPose(yp.dataBases_txt), sb.getMyWidth(wr.dataBases_txt), sb.getMyHeight(hr.dataBases_txt));
        dataBases_txt.setBackground(new Color(38, 38, 42));
        dataBases_txt.setForeground(new Color(100, 100, 100));
        dataBases_txt.setFont(new Font("Monospaced", Font.BOLD, 20));
        dataBases_txt.setVerticalAlignment(JLabel.CENTER);
        dataBases_txt.setHorizontalAlignment(JLabel.CENTER);
        dataBases_txt.setOpaque(true);
    

        // create new database bg label:
        createNew.setBounds(sb.getMyXPose(xp.createNew), sb.getMyYPose(yp.createNew), sb.getMyWidth(wr.createNew), sb.getMyHeight(hr.createNew));
        createNew.setBackground(new Color(27, 28, 33));
        createNew.setForeground(new Color(140, 140, 140));
        createNew.setFont(new Font("Monospaced", Font.BOLD, 20));
        createNew.setVerticalAlignment(JLabel.CENTER);
        createNew.setHorizontalAlignment(JLabel.CENTER);
        createNew.setOpaque(true);
        createNew.addMouseListener(this);

        // creating databases label:
        for(int i=0; i<4; i++)
        {
            dataBase[i] = new JLabel();
        }


        // select operation label:
        // open:
        open.setBounds(sb.getMyXPose(xp.open), sb.getMyYPose(yp.open), sb.getMyWidth(wr.open), sb.getMyHeight(hr.open));
        open.setBackground(new Color(27, 28, 33));
        open.setForeground(new Color(140, 140, 140));
        open.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        open.setVerticalAlignment(JLabel.CENTER);
        open.setHorizontalAlignment(JLabel.CENTER);
        open.setOpaque(true);
        open.addMouseListener(this);

        // rename:
        rename.setBounds(sb.getMyXPose(xp.rename), sb.getMyYPose(yp.rename), sb.getMyWidth(wr.rename), sb.getMyHeight(hr.rename));
        rename.setBackground(new Color(27, 28, 33));
        rename.setForeground(new Color(140, 140, 140));
        rename.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rename.setVerticalAlignment(JLabel.CENTER);
        rename.setHorizontalAlignment(JLabel.CENTER);
        rename.setOpaque(true);
        rename.addMouseListener(this);

        // delete:
        delete.setBounds(sb.getMyXPose(xp.delete), sb.getMyYPose(yp.delete), sb.getMyWidth(wr.delete), sb.getMyHeight(hr.delete));
        delete.setBackground(new Color(27, 28, 33));
        delete.setForeground(new Color(140, 140, 140));
        delete.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        delete.setVerticalAlignment(JLabel.CENTER);
        delete.setHorizontalAlignment(JLabel.CENTER);
        delete.setOpaque(true);
        delete.addMouseListener(this);

        // operation bg label:
        dbOperation.setBounds(sb.getMyXPose(xp.dbOperation), sb.getMyYPose(yp.dbOperation), sb.getMyWidth(wr.dbOperation), sb.getMyHeight(hr.dbOperation));
        dbOperation.setBackground(new Color(37, 38, 43));
        dbOperation.setForeground(new Color(17, 18, 23));
        dbOperation.setFont(new Font("colonna", Font.ITALIC, 20));
        dbOperation.setVerticalTextPosition(JLabel.CENTER);
        dbOperation.setHorizontalTextPosition(JLabel.LEFT);
        dbOperation.setOpaque(true);
        dbOperation.add(open);
        dbOperation.add(rename);
        dbOperation.add(delete);
        dbOperation.setOpaque(true);
        
        pan1.addMouseListener(this);
        pan2.addMouseListener(this);
        panMenu.addMouseListener(this);


        // defining conponents for rename operation :
        textRename.setBounds(sb.getMyXPose(xp.textRename), sb.getMyYPose(yp.textRename), sb.getMyWidth(wr.textRename), sb.getMyHeight(hr.textRename));
        textRename.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        textRename.setBackground(new Color(27, 28, 33));
        textRename.setForeground(new Color(227, 228, 233));
        textRename.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        apply.setBounds(sb.getMyXPose(xp.apply), sb.getMyYPose(yp.apply), sb.getMyWidth(wr.apply), sb.getMyHeight(hr.apply));
        apply.setBackground(new Color(27, 28, 33));
        apply.setForeground(new Color(127, 128, 143));
        apply.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        apply.setHorizontalAlignment(JLabel.CENTER);
        apply.setVerticalAlignment(JLabel.CENTER);
        apply.setOpaque(true);
        apply.addMouseListener(this);

        cancel.setBounds(sb.getMyXPose(xp.cancel), sb.getMyYPose(yp.cancel), sb.getMyWidth(wr.cancel), sb.getMyHeight(hr.cancel));
        cancel.setBackground(new Color(27, 28, 33));
        cancel.setForeground(new Color(137, 128, 133));
        cancel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancel.setHorizontalAlignment(JLabel.CENTER);
        cancel.setVerticalAlignment(JLabel.CENTER);
        cancel.setOpaque(true);
        cancel.addMouseListener(this);

        ops_rename.setBounds(sb.getMyXPose(xp.ops_rename), sb.getMyYPose(yp.ops_rename), sb.getMyWidth(wr.ops_rename), sb.getMyHeight(hr.ops_rename));
        ops_rename.setBackground(new Color(37, 38, 43));
        ops_rename.add(textRename);
        ops_rename.add(apply);
        ops_rename.add(cancel);
        ops_rename.setOpaque(true);
        ops_rename.addMouseListener(this);


        // defining components for delete operation:
        confirm.setBounds(sb.getMyXPose(xp.confirmh), sb.getMyYPose(yp.confirmh), sb.getMyWidth(wr.confirmh), sb.getMyHeight(hr.confirmh));
        confirm.setBackground(new Color(27, 28, 33));
        confirm.setForeground(new Color(137, 128, 133));
        confirm.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirm.setHorizontalAlignment(JLabel.CENTER);
        confirm.setVerticalAlignment(JLabel.CENTER);
        confirm.setOpaque(true);
        confirm.addMouseListener(this);

        back.setBounds(sb.getMyXPose(xp.back), sb.getMyYPose(yp.back), sb.getMyWidth(wr.back), sb.getMyHeight(hr.back));
        back.setBackground(new Color(27, 28, 33));
        back.setForeground(new Color(127, 128, 143));
        back.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        back.setHorizontalAlignment(JLabel.CENTER);
        back.setVerticalAlignment(JLabel.CENTER);
        back.setOpaque(true);
        back.addMouseListener(this);

        ops_delete.setBounds(-1000, -1000, sb.getMyWidth(wr.ops_delete), sb.getMyHeight(hr.ops_delete));
        ops_delete.setBackground(new Color(80, 80, 80));
        ops_delete.setForeground(new Color(40, 20, 20));
        ops_delete.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        ops_delete.setBorder(BorderFactory.createLineBorder(new Color(50, 0, 0), 1));
        ops_delete.setHorizontalAlignment(JLabel.CENTER);
        ops_delete.setVerticalAlignment(JLabel.TOP);
        ops_delete.add(confirm);
        ops_delete.add(back);
        ops_delete.setOpaque(true);
        ops_delete.addMouseListener(this);


        // defining compponents for create new database operation:
        // input bg:
        input_element.setPreferredSize(new Dimension(sb.getMyWidth(wr.input_element), sb.getMyHeight(hr.input_element)));
        input_element.setHorizontalAlignment(JLabel.CENTER);
        input_element.setVerticalAlignment(JLabel.CENTER);
        input_element.setFont(new Font("Baskerville", Font.ITALIC, 20));
        input_element.setBackground(new Color(38, 39, 43));
        input_element.setForeground(new ColorUIResource(197, 197, 197));
        input_element.setOpaque(true);

        inpute.setPreferredSize(new Dimension(sb.getMyWidth(wr.input), sb.getMyHeight(hr.input)));
        inpute.setFont(new Font("Baskerville", Font.PLAIN, 20));
        inpute.setBackground(new Color(30, 31, 36));
        inpute.setForeground(new ColorUIResource(255, 255, 255));
        inpute.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        input_bg.setBounds(sb.getMyXPose(xp.input_bg), sb.getMyYPose(yp.input_bg), sb.getMyWidth(wr.input_bg), sb.getMyHeight(hr.input_bg));
        input_bg.setLayout(new FlowLayout());
        input_bg.setBackground(new Color(40, 41, 45));
        input_bg.add(input_element);
        input_bg.add(inpute);
        input_bg.setOpaque(true);

        // btn:
        abort.setPreferredSize(new Dimension(sb.getMyWidth(wr.abort), sb.getMyHeight(hr.abort)));
        abort.setHorizontalAlignment(JLabel.CENTER);
        abort.setVerticalAlignment(JLabel.CENTER);
        abort.setFont(new Font("Dialog", Font.PLAIN, 20));
        abort.setBackground(new Color(25, 27, 30));
        abort.setForeground(new ColorUIResource(61, 61, 61));
        abort.setOpaque(true);
        abort.addMouseListener(this);

        next.setPreferredSize(new Dimension(sb.getMyWidth(wr.next), sb.getMyHeight(hr.next)));
        next.setHorizontalAlignment(JLabel.CENTER);
        next.setVerticalAlignment(JLabel.CENTER);
        next.setFont(new Font("Dialog", Font.PLAIN, 20));
        next.setBackground(new Color(25, 27, 30));
        next.setForeground(new ColorUIResource(61, 61, 61));
        next.setOpaque(true);
        next.addMouseListener(this);

        button_bg.setBounds(sb.getMyXPose(xp.button_bg), sb.getMyYPose(yp.button_bg), sb.getMyWidth(wr.button_bg), sb.getMyHeight(hr.button_bg));
        button_bg.setLayout(new FlowLayout());
        button_bg.setBackground(new Color(40, 41, 45));
        button_bg.add(abort);
        button_bg.add(next);
        button_bg.setOpaque(true);

        // main bg label:
        ops_createNew.setBounds(-1000, -1000, sb.getMyWidth(wr.ops_createNew), sb.getMyHeight(hr.ops_createNew));
        ops_createNew.setHorizontalAlignment(JLabel.CENTER);
        ops_createNew.setVerticalAlignment(JLabel.TOP);
        ops_createNew.setFont(new Font("Monospaced", Font.PLAIN, 20));
        ops_createNew.setForeground(new ColorUIResource(229, 30, 30));
        ops_createNew.setBackground(new Color(40, 41, 45));
        ops_createNew.add(button_bg);
        ops_createNew.add(input_bg);
        ops_createNew.setOpaque(true);

    }


    // set databases label:
    public JLabel setDataBases(JLabel pan1)
    {
        pan1.removeAll();
        pan1.add(dataBases_txt);

        int yPose = 50;
        int initial = 90;
        int  j=0;

        for(int i=0; i<4; i++)
        {
            if(!dataBaseNames[i].equals("X"))
            {
                dataBase[i].setText(dataBaseNames[i]);
                dataBase[i].setBounds(0, (initial + (yPose*j)), sb.getMyWidth(wr.dataBasei), sb.getMyHeight(hr.dataBasei));
                dataBase[i].setBackground(new Color(27, 28, 33));
                dataBase[i].setForeground(new Color(140, 140, 140));
                dataBase[i].setFont(new Font("Monospaced", Font.BOLD, 20));
                dataBase[i].setVerticalAlignment(JLabel.CENTER);
                dataBase[i].setHorizontalAlignment(JLabel.CENTER);
                dataBase[i].setOpaque(true);
                dataBase[i].addMouseListener(this);
                pan1.add(dataBase[i]);
                j += 1;
            }
        }

        pan1.add(createNew);
        

        return pan1;
    }


    // rename database:
    public void renameDatabase() throws IOException
    {
        textRename.setFocusable(true);
        textRename.setText(selected.getText());

        while(true)
        {
            try{ Thread.sleep(30); }catch (Exception e){ e.printStackTrace(); }
            if(apply_ || cancel_)break;

            if(!rename_DB)break;
        }

        if(apply_)
        {
            apply_ = false;
            
            String old_name = selected.getText();

            String new_name = textRename.getText();
            if(new_name.length() == 0)return; // if no input.

            // return if duplicate name inputed:
            for(int i=0; i<dataBaseNames.length; i++)
            {
                if(dataBaseNames[i].equals(new_name))
                {
                    return;
                }
            }


            // requesting to server:
            String[] request = new String[5];

            request[0] = "RENAME_DB";
            request[1] = user;
            request[2] = old_name;
            request[3] = new_name;
            request[4] = "1";

            String[] response = cs.request(request); // sending request ad receiving response.
            if(!response[0].equals("TRUE"))
            {
                return;
            }
    
            // updating main list:
            for(int i=0; i<dataBaseNames.length; i++)
            {
                if(dataBaseNames[i].equals(old_name))
                {
                    dataBaseNames[i] = new_name;
                }
            }

            // updating label text:
            selected.setText(new_name);

            textRename.setText("");
            
            
        }
        else if(cancel_){ textRename.setText(""); cancel_ = false; }
        
        textRename.setFocusable(false);
    }



    // delete database:
    public void deleteDataBase() throws IOException
    {
        ops_delete.setText("CONFIRM to DELETE '" + selected.getText() + "'");

        while(true)
        {
            try{ Thread.sleep(30);}catch(Exception e){ e.printStackTrace();}
            if( confirm_ || back_)break;

            if(!delete_DB)break;
        }

        if(confirm_)
        {
            // requesting to server:
            String[] request = new String[4];

            request[0] = "DELETE_DB";
            request[1] = user;
            request[2] = selected.getText();
            request[3] = "1";

            String[] response = cs.request(request);

            if(!response[0].equals("TRUE"))
            {
                selected.setBackground(new Color(27, 28, 33));
                return;
            }
            

            // removing name of deleted database from database list:
            for(int i=0; i<dataBaseNames.length; i++)
            {
                if(dataBaseNames[i].equals(selected.getText()))
                {
                    dataBaseNames[i] = "X";
                }
            } 


            // updating changes:
            reArrangeDB();

            // removing database label:
            pan1.remove(selected);


            // re-arranging databases label:
            pan1 = setDataBases(pan1);

            

            confirm_ = false;
        }
        else if(back_) back_ = false;
        
    }


    // re-arrange database names and count:
    public void reArrangeDB()
    {
        db_count = 0;

        // re-arranging database names:
        String[] tempDBNames = new String[dataBaseNames.length]; // creating new array.
        for(int i=0; i<dataBaseNames.length; i++)tempDBNames[i] =  dataBaseNames[i]; // copying into new temp array.

        for(int i=0; i<dataBaseNames.length; i++)dataBaseNames[i] = "X"; // erasing original array with " ".

        // updating original list:
        int j=0;
        for(int i=0; i<dataBaseNames.length; i++)
        {
            if(!tempDBNames[i].equals("X"))
            {
                dataBaseNames[j] = tempDBNames[i];
                j += 1;
                db_count += 1;
            }
        }


    }

    

    // create new database:
    public void createNewDataBase() throws IOException
    {

        String data_base_name;
        Integer table_count = 0;
        String[] tables;
        String[] attri_names;
        int[] attri_count;
        
        

        ops_createNew.setText("required information!");
        input_element.setText("database name: ");
        inpute.setFocusable(true);

        while(true) // input database name.
        {
            try{ Thread.sleep(30);} catch (Exception e){};
            if(next_)
            {
                data_base_name = inpute.getText().toString(); inpute.setText("");
                if(data_base_name.length() != 0)
                {
                    // checking for unique name:
                    for(int i=0; i<dataBaseNames.length; i++)
                    {
                        if(dataBaseNames[i].equals(data_base_name))
                        {
                            ops_createNew.setText("The name [ " + data_base_name + " ] already exist!");
                            next_ = false;
                            break;
                        }
                    }
                    if(!next_) continue;
                    input_element.setText("Table (count): ");
                    break;
                }
                ops_createNew.setText("blank name not allowed!");
                next_ = false;                
            }
            else if(abort_) {  abort_ = false; return; }
        }
        ops_createNew.setText("required information!");
        next_ = false;
    

        while(true) // input table count.
        {
            try{ Thread.sleep(30);} catch (Exception e){};
            if(next_)
            {
                try {
                    table_count = Integer.parseInt(inpute.getText());inpute.setText("");
                    break;
                } catch (Exception e) {
                    ops_createNew.setText("please enter a number!");
                    inpute.setText("");
                    next_ = false;
                }
            }
            else if(abort_) {  abort_ = false; return; }
            
        }
        ops_createNew.setText("required information!");
        next_ = false;



        // sending database name and attribute count:
        String[] request = new String[5];
        request[0] = "CREATE_DATABASE";
        request[1] = user;
        request[2] = data_base_name;
        request[3] = table_count.toString();
        request[4] = "1";
        // requesting for database creation:
        String[] response = cs.request(request);
        System.out.println(response[0]);


        request = null;
        response = null;

        // abort database creation request:
        String[] abortRequest = new String[4];
        abortRequest[0] = "ABORT_DB_CREATION";
        abortRequest[1] = user;
        abortRequest[2] = data_base_name;
        abortRequest[3] = "1";

        

        tables = new String[table_count]; // declare array for tables name.
        attri_count = new int[table_count]; // stores attributes count for each table.

        
        for(int i=0; i<table_count; i++) // input all table name with its attributes.
        {
            
            input_element.setText( (i+1) + "th table name:");
            while(true)
            {
                try{ Thread.sleep(30);} catch (Exception e){};
                if(next_)
                {
                    if(inpute.getText().length() != 0)
                    {
                        tables[i] = inpute.getText();inpute.setText("");
                        break;
                    }
                    ops_createNew.setText("blank name not allowed!");
                    next_ = false;
                }
                else if(abort_)
                {  
                    abort_ = false; 
                    response = cs.request(abortRequest);
                    System.out.println(response[0]); 
                    return; 
                }
            }
            ops_createNew.setText("required information!");
            next_ = false;

            input_element.setText("Attributes (count): ");
            while(true) // inputing attribute counts.
            {
                try{ Thread.sleep(30);} catch (Exception e){};
                if(next_)
                {
                    try {
                        attri_count[i] = Integer.parseInt(inpute.getText());inpute.setText("");
                        break;
                    } catch (Exception e) {
                        ops_createNew.setText("please enter a number!");
                        inpute.setText("");
                        next_ = false;
                    }
                }
                else if(abort_) 
                {  
                    abort_ = false;
                    response = cs.request(abortRequest);
                    System.out.println(response[0]); 
                    return;
                }
            }
            ops_createNew.setText("required information!");
            next_ = false;


            attri_names = new String[attri_count[i]]; // attribute names for each table.

            // inputing all attributes of a table:
            for(int j=0; j<attri_count[i]; j++)
            {
                if(j == 0)
                {
                    input_element.setText("[ Primary Key ]");
                }
                else
                {
                    input_element.setText( (j+1) + "th Attribute:");
                }


                while(true)
                {
                    try{ Thread.sleep(30);} catch (Exception e){};
                    if(next_)
                    {
                        if(inpute.getText().length() != 0)
                        {
                            attri_names[j] = inpute.getText();inpute.setText("");
                            break;
                        }
                        ops_createNew.setText("blank name not allowed!");
                        next_ = false;
                    }
                    else if(abort_) 
                    {  
                        abort_ = false; 
                        response = cs.request(abortRequest);
                        System.out.println(response[0]); 
                        return; 
                    }
                }
                ops_createNew.setText("required information!");
                next_ = false;
            }

            // sending table name and its all attributes name:
            request = new String[5 + attri_count[i]];
            request[0] = "CREATE_TABLE";
            request[1] = user;
            request[2] = data_base_name;
            request[3] = tables[i];
            // filling attri. names:
            int x = 0;
            for(int s = 4; s < (4 + attri_count[i]); s++)
            {
                request[s] = attri_names[x];
                x += 1;
            } 
            // response size:
            request[5 + attri_count[i] - 1] = "1";
            // sending request:
            response = cs.request(request);

        }
        

        // inserting new database:
        for(int i=0; i<dataBaseNames.length; i++)
        {
            if(dataBaseNames[i] == "X")
            {
                dataBaseNames[i] = data_base_name;
                break;
            }
        }


        // updating changes:
        reArrangeDB();


        // re-arranging databases label:
        pan1 = setDataBases(pan1);
       

        input_element.setText("");
        inpute.setText("");
        next_ = false;
        ops_createNew.setLocation(-1000, -1000);
    }




    // set home-window:
    public JFrame setHomeWindow(JFrame frame, String userName)
    {
        frame.setVisible(false);

        pan1 = setDataBases(pan1);
        frame.add(pan1);

        pan2.add(dbOperation);
        pan2.add(ops_rename);
        pan2.add(ops_delete);
        pan2.add(ops_createNew);
        frame.add(pan2);

        username.setText("@" + userName);
        panMenu.add(username);
        panMenu.add(logout);
        panMenu.add(refresh);
        frame.add(panMenu);

        frame.setVisible(true);

        return frame;
    }



    // clear window:
    public JFrame clearWindow(JFrame frame)
    {
        frame.setVisible(false);

        // off logout btn-label:
        logout.setBackground(new Color(50, 50, 50));
        logout.setForeground(new Color(0, 0, 0));

        pan1.removeAll();
        frame.remove(pan1);
        pan2.removeAll();
        frame.remove(pan2);
        panMenu.removeAll();
        frame.remove(panMenu);
        frame.setVisible(true);

        return frame;
    }



    // refresh window:
    public void refreshWindow(JFrame frame)
    {
        textRename.setFocusable(true);
        textRename.setFocusable(false);
        inpute.setFocusable(true);
        inpute.setFocusable(false);
        frame.setState(JFrame.ICONIFIED);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(sb.screenWidth, sb.screenHeight);
    }
    


   

    // MAIN-Func:
    // operating home-window and returning either DB name or logout signal:
    public String runHomeWindow(JFrame frame, String userName, String[] databases) throws IOException
    {
        // recreate selected:
        selected = null;
        selected = new JLabel();

        dataBaseNames = databases;
        user = userName;

        frame = setHomeWindow(frame, userName);

        while(true)
        {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(flogout) //logout.
            {
                frame = clearWindow(frame);
                flogout = false;
                return "LOGOUT";
            }
            else if(refresh_)
            {
                refreshWindow(frame);                
                refresh_ = false;
            }
            else if(open_DB) //open database.
            {
                frame = clearWindow(frame);
                open_DB = false;
                return selected.getText();
            }
            else if(rename_DB) //rename database.
            {
                renameDatabase();
                rename_DB = false;
            }
            else if(delete_DB) //delete database.
            {
                selected.setBackground(new Color(37, 28, 33));
                deleteDataBase();
                refreshWindow(frame); 
                delete_DB = false;
            }
            else if(create_DB) // create new database.
            {
                if(db_count >= 4)
                {
                    try{ Thread.sleep(1400);}catch(Exception e){ e.printStackTrace();}
                    pan2.setText("");
                    create_DB = false;
                    continue;
                }
                createNewDataBase();
                refreshWindow(frame);    
                create_DB = false;
                
            }                                        
        }
        
    }








    @Override
    public void mouseClicked(MouseEvent e)
    {
        // logout:
        if(e.getSource() == logout && ( rename_DB == false && delete_DB == false && create_DB == false) ){ flogout = true; }
        else if(e.getSource() == refresh){ refresh_ = true; }

        // select databases 1-4:
        for(int i=0; i<4; i++)
        {
            if(e.getSource() == dataBase[i])
            {
                
                dbOperation.setLocation(dataBase[i].getX(), dataBase[i].getY()); // show dbops.
                if(selected != dataBase[i]) // if seleced another label.
                {
                    selected.setBackground(new Color(27, 28, 33)); // make previous normal.
                }
                selected = dataBase[i]; // assign new selected label.
                selected.setBackground(new Color(37, 38, 43)); // highlight it.


                ops_rename.setLocation(-100, -100); // hide rename ops.
                textRename.setText(""); // clear textField.
                rename_DB = false; // to get out from ops_rename loop.

                ops_delete.setLocation(-100, -100); // hide delete ops.
                delete_DB = false; // to get out from ops_delete loop.

                ops_createNew.setLocation(-1000, -1000);// hide create new ops.
                if(create_DB)abort_ = true;// to get out from ops_createNew loop.
                create_DB = false;
                break;
            }
        }

        
        // operation btn:
        if(e.getSource() == open)
        { 
            open_DB = true; 
            open.setBackground(new Color(27, 28, 33));
            open.setForeground(new Color(140, 140, 140));
        }
        else if(e.getSource() == rename)
        {
            rename_DB = true;
            ops_rename.setLocation(0, selected.getY());
        }
        else if(e.getSource() == delete)
        {
            delete_DB = true;
            ops_delete.setLocation(300, 250);
        }
        else if(e.getSource() == createNew)
        {
            if(rename_DB || delete_DB)return; // dont allow if any ops is in progress.
            reArrangeDB();
            if(db_count >= 4)
            {
                create_DB = true;
                pan2.setText("Only FOUR databases allowed for a account");
                return;
            }
            create_DB = true;
            ops_createNew.setLocation(270, 190);
        }



        // rename ops:
        if(e.getSource() == apply){ apply_ = true; ops_rename.setLocation(-100, -100); }
        else if(e.getSource() == cancel){ cancel_ = true; ops_rename.setLocation(-100, -100); }

        // delete ops:
        if(e.getSource() == confirm){ confirm_ = true; ops_delete.setLocation(-100, -100); }
        else if( e.getSource() == back)
        { 
            back_ = true; ops_delete.setLocation(-100, -100);
            selected.setBackground(new Color(37, 38, 43));
        }

        // create new ops:
        else if(e.getSource() == abort){ abort_ = true; ops_createNew.setLocation(-1000, -1000); }
        else if(e.getSource() == next){ next_ = true; }


        // if clicked anywhere else:
        if(( e.getSource() != dataBase[0] && e.getSource() != dataBase[1] ) && ( e.getSource() != dataBase[2] && e.getSource() != dataBase[3] ))
        {
            dbOperation.setLocation(-100, -100);
        }

    }






    @Override
    public void mouseEntered(MouseEvent e)
    {
        // logout:
        if(e.getSource() == logout)
        {
            logout.setBackground(new Color(70, 70, 70));
            logout.setForeground(new Color(70, 0, 0));
        }
        else if(e.getSource() == refresh) // refresh:
        {
            refresh.setBackground(new Color(70, 70, 70));
            refresh.setForeground(new Color(0, 0, 40));
        }

        // databases 1-4:
        for(int i=0; i<4; i++)
        {
            if(e.getSource() == dataBase[i] && dataBase[i] != selected)
            {
                dataBase[i].setBackground(new Color(33, 34, 39));
            }
        }
        if(e.getSource() == createNew)
        {
            createNew.setBackground(new Color(33, 34, 39));
            createNew.setForeground(new Color(190,190, 190));
        }

        // operation btn:
        else if(e.getSource() == open)
        {
            open.setBackground(new Color(47, 48, 53));
            open.setForeground(new Color(10, 10, 30));
        }
        else if(e.getSource() == rename)
        {
            rename.setBackground(new Color(47, 48, 53));
            rename.setForeground(new Color(10, 10, 30));
        }
        else if(e.getSource() == delete)
        {
            delete.setBackground(new Color(47, 48, 53));
            delete.setForeground(new Color(30, 10, 10));
        }

        // rename ops:
        else if(e.getSource() == apply)
        {
            apply.setBackground(new Color(47, 48, 53));
            apply.setForeground(new Color(10, 10, 30));
        }
        else if(e.getSource() == cancel)
        {
            cancel.setBackground(new Color(47, 48, 53));
            cancel.setForeground(new Color(30, 10, 10));
        }

        // delete ops:
        else if(e.getSource() == confirm)
        { 
            confirm.setBackground(new Color(57, 28, 33));
            confirm.setForeground(new Color(137, 128, 133));
        }
        else if( e.getSource() == back)
        { 
            back.setBackground(new Color(127, 128, 133));
            back.setForeground(new Color(27, 28, 43));
        }


        // create new database ops:
        else if(e.getSource() == abort)
        {
            abort.setBackground(new Color(65, 67, 70));
            abort.setForeground(new ColorUIResource(31, 11, 11));
        }
        else if(e.getSource() == next)
        {
            next.setBackground(new Color(65, 67, 70));
            next.setForeground(new ColorUIResource(11, 11, 31));
        }

    }





    @Override
    public void mouseExited(MouseEvent e)
    {
        // logout:
        if(e.getSource() == logout)
        {
            logout.setBackground(new Color(50, 50, 50));
            logout.setForeground(new Color(0, 0, 0));
        }
        else if(e.getSource() == refresh)
        {
            refresh.setBackground(new Color(50, 50, 50));
            refresh.setForeground(new Color(0, 0, 0));
        }

        // databases 1-4:
        for(int i=0; i<4; i++)
        {
            if(e.getSource() == dataBase[i] && dataBase[i] != selected)
            {
                dataBase[i].setBackground(new Color(27, 28, 33));
            }
        }
        if(e.getSource() == createNew)
        {
            createNew.setBackground(new Color(27, 28, 33));
            createNew.setForeground(new Color(140, 140, 140));
        }

        // operation btn:
        else if(e.getSource() == open)
        {
            open.setBackground(new Color(27, 28, 33));
            open.setForeground(new Color(140, 140, 140));
        }
        else if(e.getSource() == rename)
        {
            rename.setBackground(new Color(27, 28, 33));
            rename.setForeground(new Color(140, 140, 140));
        }
        else if(e.getSource() == delete)
        {
            delete.setBackground(new Color(27, 28, 33));
            delete.setForeground(new Color(140, 140, 140));
        }

        // rename ops:
        else if(e.getSource() == apply)
        {
            apply.setBackground(new Color(27, 28, 33));
            apply.setForeground(new Color(127, 128, 143));
        }
        else if(e.getSource() == cancel)
        {
            cancel.setBackground(new Color(27, 28, 33));
            cancel.setForeground(new Color(137, 128, 133));
        }
        
        // delete ops:
        else if(e.getSource() == confirm)
        { 
            confirm.setBackground(new Color(27, 28, 33));
            confirm.setForeground(new Color(137, 128, 133));
        }
        else if( e.getSource() == back)
        { 
            back.setBackground(new Color(27, 28, 33));
            back.setForeground(new Color(127, 128, 143));
        }


    
        // create new database ops:
        else if(e.getSource() == abort)
        {
            abort.setBackground(new Color(25, 27, 30));
            abort.setForeground(new ColorUIResource(61, 61, 61));
        }
        else if(e.getSource() == next)
        {
            next.setBackground(new Color(25, 27, 30));
            next.setForeground(new ColorUIResource(61, 61, 61));
        }
    }


    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e){}
    
}