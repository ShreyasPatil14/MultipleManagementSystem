package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;

import component.SetDimension;
import component.Components;
import socket.ClientSocket;

public class WorkWindow extends SetDimension implements MouseListener 
{
    ClientSocket cs = new ClientSocket();

    // globals:
    private String user;     // username.
    private String database; // database name.
    private JLabel selected; // selected table.
    private int selectedIDX; // index of selectted table.
    

    // root components:
    Components cmp = new Components();
    // refresh btn-label:
    JLabel refresh = new JLabel("Refresh");
    private JLabel panMenu = cmp.panMenu;
    private JLabel pan1 = cmp.pan1;
    private JLabel pan2 = cmp.pan2;
    private JTable table;

    private JFrame tFrame = new JFrame();
    private JLabel viewedBy = new JLabel("");

    private JLabel messageBox = new JLabel();

    
    // metadata:
    private String[] tables; // table names.
    private Integer selected_cols_count; // attributes/colums count of selected table.
    private String[] attributes; // original.
    private String[][] data; // original.            

    // main flags:
    private boolean f_back = false;
    private boolean f_nothing = true;
    public boolean refresh_ = false;


    
    // operation flags:
    private boolean f_addData = false;
    private boolean f_updateData = false;
    private boolean f_deleteRec = false;
    private boolean f_viewTable = false; 
    private boolean f_view = false;

    // add record ops flags:
    private boolean f_next = false;
    private boolean f_cancel = false;

    // update record ops flags:
    private boolean f_allowUpdate = false;
    private boolean f_cancelUpdate = false;
    private boolean f_updtVal = false;
    private boolean f_nextUpdt = false;

    // delete record ops flags:
    private boolean f_cancelDelete = false;
    private boolean f_delet = false;


    // all components:
    // back button:
    private JLabel back_btn = new JLabel("<");

    // databases title label:
    private JLabel dataBase_name = new JLabel();

    // table labels array:
    private JLabel[] table_label;


   // table operation bg label:
    private JLabel tableOperation = new JLabel("<");
    // all operation selector labels:
    private JLabel view_table = new JLabel("View data");
    private JLabel add_data = new JLabel("Add data");
    private JLabel update_record = new JLabel("Update data");
    private JLabel delete_recodr = new JLabel("Delete record");


    // operation performing labels:
    // add data operation:
    private JLabel ops_addData = new JLabel();
    private JLabel input_att_name = new JLabel("attribute1attribute1");
    private JTextField input_att_text = new JTextField();
    private JLabel cancelW = new JLabel("Cancel");
    private JLabel nextW = new JLabel("Next");


    // update record operation:
    private JLabel ops_updateRecord = new JLabel("Select a record to update");
    private JLabel updateThisRec = new JLabel("Update");
    private JLabel att_name = new JLabel();
    private JTextField att_val = new JTextField();
    private JLabel cancelUpdt = new JLabel("Cancel");
    private JLabel uptdVal = new JLabel("Change");
    private JLabel nextUpdt = new JLabel("Next");


    // delete record operation:
    private JLabel ops_deleteRecord = new JLabel("Select a record to delete");
    private JLabel cancelDelete = new JLabel("Cancel");
    private JLabel delete = new JLabel("Delete");




    // constructor:
    public WorkWindow()
    {
        // refresh btn-label:
        refresh.setBounds(sb.getMyXPose(xp.refreshW), sb.getMyYPose(yp.refreshW), sb.getMyWidth(wr.refreshW), sb.getMyHeight(hr.refreshW));
        refresh.setBackground(new Color(50, 50, 50));
        refresh.setForeground(new Color(0, 0, 0));
        refresh.setFont(new Font("Baskerville", Font.BOLD, 14));
        refresh.setVerticalAlignment(JLabel.CENTER);
        refresh.setHorizontalAlignment(JLabel.CENTER);
        refresh.setOpaque(true);
        refresh.addMouseListener(this);

        // meassage box:
        messageBox.setBounds(-1000, -1000, sb.getMyWidth(wr.messageBox), sb.getMyHeight(hr.messageBox));
        messageBox.setBackground(new Color(30, 20, 24));
        messageBox.setForeground(new Color(220, 220,240));
        messageBox.setFont(new Font("Baskerville", Font.PLAIN, 16));
        messageBox.setVerticalAlignment(JLabel.CENTER);
        messageBox.setHorizontalAlignment(JLabel.CENTER);
        messageBox.setOpaque(true);

        // viewedBy:
        viewedBy.setBounds(-1000, -1000, sb.getMyWidth(wr.viewedBy), sb.getMyHeight(hr.viewedBy));
        viewedBy.setBackground(new Color(36, 30, 36));
        viewedBy.setForeground(new Color(100, 100, 110));
        viewedBy.setFont(new Font("Baskerville", Font.BOLD, 20));
        viewedBy.setHorizontalAlignment(JLabel.CENTER);
        viewedBy.setVerticalAlignment(JLabel.CENTER);
        viewedBy.setOpaque(true);
        
        
        
        // database name bg:
        dataBase_name.setBounds(sb.getMyXPose(xp.dataBase_name), sb.getMyYPose(yp.dataBase_name), sb.getMyWidth(wr.dataBase_name), sb.getMyHeight(hr.dataBase_name));
        dataBase_name.setBackground(new Color(38, 38, 42));
        dataBase_name.setForeground(new Color(100, 100, 100));
        dataBase_name.setFont(new Font("Monospaced", Font.BOLD, 25));
        dataBase_name.setVerticalAlignment(JLabel.CENTER);
        dataBase_name.setHorizontalAlignment(JLabel.CENTER);
        dataBase_name.setOpaque(true);


        // back btn-label:
        back_btn.setBounds(sb.getMyXPose(xp.back_btn), sb.getMyYPose(yp.back_btn), sb.getMyWidth(wr.back_btn), sb.getMyHeight(hr.back_btn));
        back_btn.setBackground(new ColorUIResource(36, 36, 40));
        back_btn.setForeground(new Color(0, 0, 0));
        back_btn.setFont(new Font("Arial", Font.PLAIN , 30));
        back_btn.setVerticalAlignment(JLabel.CENTER);
        back_btn.setHorizontalAlignment(JLabel.CENTER);
        back_btn.setOpaque(true);
        back_btn.addMouseListener(this);


        // table operation labels:
        // view table:
        view_table.setBounds(sb.getMyXPose(xp.view_table), sb.getMyYPose(yp.view_table), sb.getMyWidth(wr.view_table), sb.getMyHeight(hr.view_table));
        view_table.setBackground(new Color(27, 28, 33));
        view_table.setForeground(new Color(140, 140, 140));
        view_table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        view_table.setVerticalAlignment(JLabel.CENTER);
        view_table.setHorizontalAlignment(JLabel.CENTER);
        view_table.setOpaque(true);
        view_table.addMouseListener(this);

        // add data:
        add_data.setBounds(sb.getMyXPose(xp.add_data), sb.getMyYPose(yp.add_data), sb.getMyWidth(wr.add_data), sb.getMyHeight(hr.add_data));
        add_data.setBackground(new Color(27, 28, 33));
        add_data.setForeground(new Color(140, 140, 140));
        add_data.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add_data.setVerticalAlignment(JLabel.CENTER);
        add_data.setHorizontalAlignment(JLabel.CENTER);
        add_data.setOpaque(true);
        add_data.addMouseListener(this);

        // update record:
        update_record.setBounds(sb.getMyXPose(xp.update_record), sb.getMyYPose(yp.update_record), sb.getMyWidth(wr.update_record), sb.getMyHeight(hr.update_record));
        update_record.setBackground(new Color(27, 28, 33));
        update_record.setForeground(new Color(140, 140, 140));
        update_record.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        update_record.setVerticalAlignment(JLabel.CENTER);
        update_record.setHorizontalAlignment(JLabel.CENTER);
        update_record.setOpaque(true);
        update_record.addMouseListener(this);

        // delete record:
        delete_recodr.setBounds(sb.getMyXPose(xp.delete_recodr), sb.getMyYPose(yp.delete_recodr), sb.getMyWidth(wr.delete_recodr), sb.getMyHeight(hr.delete_recodr));
        delete_recodr.setBackground(new Color(27, 28, 33));
        delete_recodr.setForeground(new Color(140, 140, 140));
        delete_recodr.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        delete_recodr.setVerticalAlignment(JLabel.CENTER);
        delete_recodr.setHorizontalAlignment(JLabel.CENTER);
        delete_recodr.setOpaque(true);
        delete_recodr.addMouseListener(this);

        // table operation background label:
        tableOperation.setBounds(-1000, -1000, sb.getMyWidth(wr.tableOperation), sb.getMyHeight(hr.tableOperation));
        tableOperation.setBackground(new Color(37, 38, 43));
        tableOperation.setForeground(new Color(17, 18, 23));
        tableOperation.setFont(new Font("colonna", Font.ITALIC, 20));
        tableOperation.setVerticalTextPosition(JLabel.CENTER);
        tableOperation.setHorizontalTextPosition(JLabel.LEFT);
        tableOperation.setOpaque(true);
        tableOperation.add(view_table);
        tableOperation.add(add_data);
        tableOperation.add(update_record);
        tableOperation.add(delete_recodr);
        tableOperation.setOpaque(true);


        // operation add record:
        // cancel button label:
        cancelW.setBounds(sb.getMyXPose(xp.cancelW), sb.getMyYPose(yp.cancelW), sb.getMyWidth(wr.cancelW), sb.getMyHeight(hr.cancelW));
        cancelW.setBackground(new Color(25, 27, 30));
        cancelW.setForeground(new Color(61, 61, 61));
        cancelW.setFont(new Font("Baskerville", Font.PLAIN,16));
        cancelW.setHorizontalAlignment(JLabel.CENTER);
        cancelW.setOpaque(true);
        cancelW.addMouseListener(this);
        

        // next button label:
        nextW.setBounds(sb.getMyXPose(xp.nextW), sb.getMyYPose(yp.nextW), sb.getMyWidth(wr.nextW), sb.getMyHeight(hr.nextW));
        nextW.setBackground(new Color(25, 27, 30));
        nextW.setForeground(new Color(61, 61, 61));
        nextW.setFont(new Font("Baskerville", Font.PLAIN,16));
        nextW.setHorizontalAlignment(JLabel.CENTER);
        nextW.setOpaque(true);
        nextW.addMouseListener(this);

        // add data attribute name:
        input_att_name.setBounds(sb.getMyXPose(xp.input_att_name), sb.getMyYPose(yp.input_att_name), sb.getMyWidth(wr.input_att_name), sb.getMyHeight(hr.input_att_name));
        input_att_name.setBackground(new Color(30, 40, 47));
        input_att_name.setForeground(new Color(112, 118, 125));
        input_att_name.setFont(new Font("Baskerville", Font.ITALIC,20));
        input_att_name.setHorizontalAlignment(JLabel.RIGHT);
        input_att_name.setOpaque(true);
        
        // inpute attribute value/text:
        input_att_text.setBounds(sb.getMyXPose(xp.input_att_text), sb.getMyYPose(yp.input_att_text), sb.getMyWidth(wr.input_att_text), sb.getMyHeight(hr.input_att_text));
        input_att_text.setBackground(new Color(14, 24, 31));
        input_att_text.setForeground(new Color(130, 138,145));
        input_att_text.setFont(new Font("Baskerville", Font.PLAIN,20));
        input_att_text.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        input_att_text.setOpaque(true);

        // add record background label:
        ops_addData.setBounds(-1000, -1000, sb.getMyWidth(wr.ops_addData), sb.getMyHeight(hr.ops_addData));
        ops_addData.setBackground(new Color(30, 40, 47));
        ops_addData.setForeground(new Color(230, 238,245));
        ops_addData.setFont(new Font("Baskerville", Font.PLAIN, 20));
        ops_addData.setVerticalAlignment(JLabel.TOP);
        ops_addData.setHorizontalAlignment(JLabel.CENTER);
        ops_addData.add(input_att_name);
        ops_addData.add(input_att_text);
        ops_addData.add(cancelW);
        ops_addData.add(nextW);
        ops_addData.setOpaque(true);


        // operation update record:
        // update this record btn:
        updateThisRec.setBounds(-1000, -1000, sb.getMyWidth(wr.updateThisRec), sb.getMyHeight(hr.updateThisRec));
        updateThisRec.setBackground(new Color(25, 27, 30));
        updateThisRec.setForeground(new Color(61, 61, 61));
        updateThisRec.setFont(new Font("Baskerville", Font.ITALIC,20));
        updateThisRec.setHorizontalAlignment(JLabel.CENTER);
        updateThisRec.setOpaque(true);
        updateThisRec.addMouseListener(this);

        // display attribute name label:
        att_name.setBounds(-1000, -1000, sb.getMyWidth(wr.att_name), sb.getMyHeight(hr.att_name));
        att_name.setBackground(new Color(40, 37, 40));
        att_name.setForeground(new Color(152, 158, 165));
        att_name.setFont(new Font("Baskerville", Font.ITALIC,20));
        att_name.setHorizontalAlignment(JLabel.RIGHT);
        att_name.setOpaque(true);

        // display att val textarea:
        att_val.setBounds(-1000, -1000, sb.getMyWidth(wr.att_val), sb.getMyHeight(hr.att_val));
        att_val.setBackground(new Color(14, 24, 31));
        att_val.setForeground(new Color(230, 238,245));
        att_val.setFont(new Font("Baskerville", Font.PLAIN,20));
        att_val.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        att_val.setEditable(false);
        att_val.setEnabled(false);
        att_val.setOpaque(true);

        // cancel updation btn:
        cancelUpdt.setBounds(-1000, -1000,sb.getMyWidth(wr.cancelUpdt), sb.getMyHeight(hr.cancelUpdt));
        cancelUpdt.setBackground(new Color(25, 27, 30));
        cancelUpdt.setForeground(new Color(61, 61, 61));
        cancelUpdt.setFont(new Font("Baskerville", Font.PLAIN,16));
        cancelUpdt.setHorizontalAlignment(JLabel.CENTER);
        cancelUpdt.setOpaque(true);
        cancelUpdt.addMouseListener(this);
        cancelUpdt.addMouseListener(this);

        // update value btn:
        uptdVal.setBounds(-1000, -1000,sb.getMyWidth(wr.uptdVal), sb.getMyHeight(hr.uptdVal));
        uptdVal.setBackground(new Color(25, 27, 30));
        uptdVal.setForeground(new Color(61, 61, 61));
        uptdVal.setFont(new Font("Baskerville", Font.PLAIN,16));
        uptdVal.setHorizontalAlignment(JLabel.CENTER);
        uptdVal.setOpaque(true);
        uptdVal.addMouseListener(this);
        uptdVal.addMouseListener(this);

        // check next attribute btn:
        nextUpdt.setBounds(-1000, -1000, sb.getMyWidth(wr.nextUpdt), sb.getMyHeight(hr.nextUpdt));
        nextUpdt.setBackground(new Color(25, 27, 30));
        nextUpdt.setForeground(new Color(61, 61, 61));
        nextUpdt.setFont(new Font("Baskerville", Font.PLAIN,16));
        nextUpdt.setHorizontalAlignment(JLabel.CENTER);
        nextUpdt.setOpaque(true);
        nextUpdt.addMouseListener(this);
        nextUpdt.addMouseListener(this);

        // update ops bg label:
        ops_updateRecord.setBounds(-1000, -1000, sb.getMyWidth(wr.ops_updateRecord), sb.getMyHeight(hr.ops_updateRecord));
        ops_updateRecord.setBackground(new Color(40, 37, 40));
        ops_updateRecord.setForeground(new Color(230, 238,245));
        ops_updateRecord.setFont(new Font("Baskerville", Font.PLAIN, 20));
        ops_updateRecord.setVerticalAlignment(JLabel.CENTER);
        ops_updateRecord.setHorizontalAlignment(JLabel.CENTER);
        ops_updateRecord.add(updateThisRec);
        ops_updateRecord.add(att_name);
        ops_updateRecord.add(att_val);
        ops_updateRecord.add(cancelUpdt);
        ops_updateRecord.add(uptdVal);
        ops_updateRecord.add(nextUpdt);
        ops_updateRecord.setOpaque(true);


        // operation  delete record:
        // cancel delete btn:
        cancelDelete.setBounds(sb.getMyXPose(xp.cancelDelete), sb.getMyYPose(yp.cancelDelete), sb.getMyWidth(wr.cancelDelete), sb.getMyHeight(hr.cancelDelete));
        cancelDelete.setBackground(new Color(25, 27, 30));
        cancelDelete.setForeground(new Color(61, 61, 61));
        cancelDelete.setFont(new Font("Baskerville", Font.PLAIN,16));
        cancelDelete.setHorizontalAlignment(JLabel.CENTER);
        cancelDelete.setOpaque(true);
        cancelDelete.addMouseListener(this);
        // delete btn:
        delete.setBounds(sb.getMyXPose(xp.Wdelete), sb.getMyYPose(yp.Wdelete), sb.getMyWidth(wr.Wdelete), sb.getMyHeight(hr.Wdelete));
        delete.setBackground(new Color(25, 27, 30));
        delete.setForeground(new Color(61, 61, 61));
        delete.setFont(new Font("Baskerville", Font.PLAIN,16));
        delete.setHorizontalAlignment(JLabel.CENTER);
        delete.setOpaque(true);
        delete.addMouseListener(this);
        // dalete operation bg label:
        ops_deleteRecord.setBounds(-1000, -1000, sb.getMyWidth(wr.ops_deleteRecord), sb.getMyHeight(hr.ops_deleteRecord));
        ops_deleteRecord.setBackground(new Color(40, 37, 40));
        ops_deleteRecord.setForeground(new Color(230, 238,245));
        ops_deleteRecord.setFont(new Font("Baskerville", Font.PLAIN, 20));
        ops_deleteRecord.setVerticalAlignment(JLabel.CENTER);
        ops_deleteRecord.setHorizontalAlignment(JLabel.CENTER); 
        ops_deleteRecord.add(cancelDelete);
        ops_deleteRecord.add(delete);
        ops_deleteRecord.setOpaque(true);


        panMenu.addMouseListener(this);
        pan1.addMouseListener(this);
        pan2.addMouseListener(this);

    }


    // show message:
    private void showMessage()
    {
        messageBox.setLocation(sb.getMyXPose(xp.messageBox), sb.getMyYPose(yp.messageBox));
    }

    // hide message
    private void hideMessage()
    {
        try {Thread.sleep(1600);} catch (InterruptedException e) {e.printStackTrace();}
        messageBox.setLocation(-1000, -1000);
    }




    // get header (attributes) of selected table:
    private void getAttributes() throws IOException
    {
        // get attribute/column count first:
        String[] request = new String[5];
        request[0] = "GET_ATTRI_COUNTS";
        request[1] = user;
        request[2] = database;
        request[3] = tables[selectedIDX];
        request[4] = "1";

        String[] response = cs.request(request); 
        selected_cols_count = Integer.parseInt(response[0]); // get integer value of record counts.

        response = null;

        request = new String[5];
        request[0] = "GET_ATTRIBUTES";
        request[1] = user;
        request[2] = database;
        request[3] = tables[selectedIDX];
        request[4] = selected_cols_count.toString();

        attributes = cs.request(request); 

    }




    // get records of selected table:
    private void getRecords() throws IOException
    {
        // get record count first:
        String[] request = new String[5];
        request[0] = "GET_RECORD_COUNTS";
        request[1] = user;
        request[2] = database;
        request[3] = tables[selectedIDX];
        request[4] = "1";

        String[] response = cs.request(request); 

        if(response[0].equals("0"))
        {
            if(f_addData)return;
            messageBox.setText("No records");
            showMessage();
            hideMessage();
            return;
        }

        int record = Integer.parseInt(response[0]); // get integer value of record counts.

        request = null;

        // assigning 2d array for data:
        data = new String[record][];

        Integer i=0;

        // get record 1-1:
        request = new String[6];
        request[0] = "GET_NEXT_RECORD";
        request[1] = user;
        request[2] = database;
        request[3] = tables[selectedIDX];
        request[4] = i.toString();
        request[5] = selected_cols_count.toString();

        String[] row;

        
        while(true)
        {
            
            request[4] = i.toString();
            row = cs.request(request);
            data[i] = row;
            if(i == record-1)
            {
                break;
            }
            i += 1;
        }


    }



    // display table func.
    private void displayTable()
    {
        tFrame.dispose();
        tFrame = null;
        tFrame = new JFrame();
        
        tFrame.setBounds(-1000, -1000, 1131, 375);
        tFrame.setResizable(false);
        tFrame.setUndecorated(true);
        tFrame.setAlwaysOnTop(true);

        // create table view with header and data ( select row but unable editing):
        table = new JTable(data, attributes){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table.setRowHeight(30); // set row height.
        table.setRowSelectionAllowed(true); // allow row selection.
        table.setGridColor(Color.BLACK);


        // modify header:
        table.getTableHeader().setBackground(new Color(40, 50, 70));
        table.getTableHeader().setForeground(new Color(140, 140, 140));
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 20));
        // modify data:
        table.setFont(new Font("Arial", Font.PLAIN,16));

        // create object to set text alligment of data as CENTER:
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        // if more than 4 cols. then add scroll bar etc:
        if(attributes.length >= 4)
        {
            // for each column:
            for(int i=0; i<attributes.length; i++) 
            {
                // set column width:
                table.getColumnModel().getColumn(i).setPreferredWidth(377);

                // set allignment center:
                table.getColumnModel().getColumn(i).setCellRenderer(center);
            }

            // (a)to visible horizontal scroll bar:
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 

            // (a) add hor-ver scroll bar:
            JScrollPane scrpn = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            
            // (a) add scrollpan:
            tFrame.add(scrpn);

        }
        else // otherwise fit the column width to the window:
        {
            tFrame.getContentPane().add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
            // for each column:
            for(int i=0; i<attributes.length; i++) 
            {
                // set allignment center:
                table.getColumnModel().getColumn(i).setCellRenderer(center);
            }
        }

        tFrame.setVisible(true);


    }




    // view table func:
    private void viewTable() throws IOException
    {
        
        // get attributes:
        getAttributes();

        // // get data: (original)
        getRecords();

        if(data == null)
        {
            return;
        }

        // calling display func:
        displayTable(); 

        

        // update changes:
        tFrame.setLocation(sb.getMyXPose(xp.tFrame), sb.getMyYPose(yp.tFrame));
        tFrame.setVisible(true);
        viewedBy.setText(selected.getText());
        viewedBy.setLocation(sb.getMyXPose(xp.viewedBy), sb.getMyYPose(yp.viewedBy));

        f_view = true;

    }


    private void setAddRecord()
    {
        f_next = false;
        f_cancel = false;
        nextW.setText("Next");  
        cancelW.setText("Cancel");
        input_att_text.setEnabled(true);
        input_att_text.setFocusable(true);
        ops_addData.setLocation(sb.getMyXPose(xp.ops_addData), sb.getMyYPose(yp.ops_addData));
    }




    // add record func:
    private void addRecord() throws IOException
    {
        while(true)
        {
            // show data:
            viewTable();

            setAddRecord();
            
            // get attributes:
            getAttributes();

            String[] attri_values = new String[attributes.length];

            // inpute all values:
            for(int i=0; i<attributes.length; i++)
            {
                input_att_name.setText(attributes[i]);
                input_att_text.setText("");
                while(true)
                {
                    try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
                    
                    if(f_next)
                    {
                        if(input_att_text.getText().length() == 0)
                        {
                            input_att_text.setBackground(new Color(44, 24, 31)); // change bg.
                            while(true)
                            {
                                try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
                                break;
                            }
                        
                            input_att_text.setBackground(new Color(14, 24, 31)); // reset bg.
                            f_next = false;
                            continue;
                        }
                        input_att_text.setBackground(new Color(14, 24, 31)); // reset bg color.
                        attri_values[i] = input_att_text.getText();
                        f_next = false;
                        break;
                    }
                    else if(f_cancel)
                    {
                        f_cancel = false;
                        return;
                    }

                }
            }
            
            input_att_text.setEnabled(false);

            
            nextW.setText("Add");
            cancelW.setText("Abort");

            // submit or cancel:
            while(true)
            {
                try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}

                if(f_next)
                {
                    // send record:
                    String[] request = new String[attributes.length + 5];
                    request[0]  = "ADD_RECORD";
                    request[1] = user;
                    request[2] = database;
                    request[3] = tables[selectedIDX];
                    int j=0;
                    for(int i=4; i<request.length -1; i++)
                    {
                        request[i] = attri_values[j];
                        j += 1;
                    }
                    request[request.length - 1] = "1";

                    String[] response = cs.request(request);
                    
                    if(response[0].equals("TRUE"))
                    {
                        messageBox.setText("One record Added");
                    }
                    else{
                        messageBox.setText("Cannot Add Record");
                    }


                    ops_addData.setLocation(-1000, -1000);
                    f_next = false;

                    showMessage();
                    hideMessage();
                    break;
                        
                }
                else if(f_cancel)
                {
                    ops_addData.setLocation(-1000, -1000);
                    f_cancel = false;
                    return;
                }
            }
        }
    }


    
    private void setUpdateWindow()
    {
        // reset main option:
        ops_updateRecord.setVerticalAlignment(JLabel.CENTER);
        ops_updateRecord.setText("Select a record to update");
        updateThisRec.setLocation(-1000, -1000);
        att_name.setLocation(-1000, -1000);
        att_val.setLocation(-1000, -1000);
        att_val.setFocusable(true);
        cancelUpdt.setLocation(-1000, -1000);
        uptdVal.setLocation(-1000, -1000);
        nextUpdt.setLocation(-1000, -1000);
        ops_updateRecord.setLocation(sb.getMyXPose(xp.ops_updateRecord), sb.getMyYPose(yp.ops_updateRecord));
    }


    // update record func:
    private void updateRecord() throws IOException
    {
        
        while(true)
        {
            setUpdateWindow();
            
            // show data:
            viewTable();

            if(data == null)
            {
                ops_updateRecord.setLocation(-1000, -1000);
                return;
            }

            // reset flags:
            f_cancelUpdate = false;
            f_nextUpdt = false;
            f_updtVal = false;
            // get index of record to be updated:
            int updateRowIdx = -1;

            // wait until select something:
            while(updateRowIdx == -1)
            {
                try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}

                updateRowIdx = table.getSelectedRow();
                // System.out.println("in 1st while: "+updateRowIdx);

                if(f_cancelUpdate){f_cancelUpdate = false; return;} // escape.
            }

            ops_updateRecord.setVerticalAlignment(JLabel.TOP);
            ops_updateRecord.setText("Update selected record");

            updateThisRec.setLocation(sb.getMyXPose(xp.updateThisRec), sb.getMyYPose(yp.updateThisRec));// show update option.

            // wait until click update btn:
            while(!f_allowUpdate)
            {
                try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}

                updateRowIdx = table.getSelectedRow(); // can select any row.

                if(f_cancelUpdate){f_cancelUpdate = false;ops_updateRecord.setLocation(-1000, -1000); return;} // escape.
            }
            f_allowUpdate = false;

            ops_updateRecord.setText("");

            updateThisRec.setLocation(-1000, -1000);// hide update option.

            // show main options:
            att_name.setLocation(sb.getMyXPose(xp.att_name), sb.getMyYPose(yp.att_name));
            att_val.setLocation(sb.getMyXPose(xp.att_val), sb.getMyYPose(yp.att_val));
            cancelUpdt.setLocation(sb.getMyXPose(xp.cancelUpdt), sb.getMyYPose(yp.cancelUpdt));
            uptdVal.setLocation(sb.getMyXPose(xp.uptdVal), sb.getMyYPose(yp.uptdVal));
            nextUpdt.setLocation(sb.getMyXPose(xp.nextUpdt), sb.getMyYPose(yp.nextUpdt));

            // old record:
            String[] update_record = data[updateRowIdx];
            // old primary key:
            String primary_key = data[updateRowIdx][0];

            // updated record:
            String[] new_record = new String[data[updateRowIdx].length];

            // loop till length of record:
            for(int i=0; i<data[updateRowIdx].length; i++)
            {
                // view next attribute and value:
                att_name.setText(attributes[i]);
                att_val.setText(update_record[i]);

                // disable edit:
                att_val.setEditable(false);
                att_val.setEnabled(false);

                // reset bg:
                att_val.setBackground(new Color(14, 24, 31));

                // wait until update or next:
                while(true)
                {
                    try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}

                    
                    if(f_updtVal)// if update.
                    {
                        // allow edit:
                        att_val.setBackground(new Color(44, 24, 31));
                        att_val.setEditable(true);
                        att_val.setEnabled(true);

                        // wait until next or cancel btn press:
                        while(true)
                        {
                            try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}

                            if(f_nextUpdt)
                            {
                                // if value is null then append original:
                                if(att_val.getText().length() == 0)
                                {
                                    new_record[i] = update_record[i];
                                }
                                else // append updated: 
                                {
                                    new_record[i] = att_val.getText();
                                }
                                f_nextUpdt = false;
                                f_updtVal = false;
                                break;
                            }
                            else if(f_cancelUpdate){f_cancelUpdate = false;ops_updateRecord.setLocation(-1000, -1000); return;} // escape.

                        }
                        break;
                    }
                    else if(f_nextUpdt) // if next.
                    {
                        new_record[i] = att_val.getText();
                        f_nextUpdt = false; 
                        break;
                    }
                    else if(f_cancelUpdate){f_cancelUpdate = false;ops_updateRecord.setLocation(-1000, -1000); return;} // escape.
                }

            }
            ops_updateRecord.setLocation(-1000, -1000);



            // request to update record:
            String[] request = new String[new_record.length + 6];
            request[0] = "UPDATE_RECORD";
            request[1] = user;
            request[2] = database;
            request[3] = tables[selectedIDX];

            request[4] = primary_key;

            int j=0;
            for(int i=5; i < request.length - 1; i++)
            {
                request[i] = new_record[j];
                j += 1;
            }

            request[request.length - 1] = "1";

            String[] response = cs.request(request);
            if(response[0].equals("TRUE"))
            {
                messageBox.setText("Record updated successfully");
            }
            else
            {
                messageBox.setText("Cannot update record");
            }

            showMessage();
            hideMessage();
        }
    }


    private void setDeletRecord()
    {
        // reset component:
        ops_deleteRecord.setVerticalAlignment(JLabel.CENTER);
        ops_deleteRecord.setText("Select a record to delete");
        cancelDelete.setLocation(-1000, -1000);
        delete.setLocation(-1000, -1000);
        ops_deleteRecord.setLocation(sb.getMyXPose(xp.ops_deleteRecord), sb.getMyYPose(yp.ops_deleteRecord));

        f_cancelDelete = false;
        f_delet = false;

    }


    // delete record func:
    private void deleteRecord() throws IOException
    {

        while(true)
        {
            setDeletRecord();
       
            // show data:
            viewTable();

            if(data == null)
            {
                ops_deleteRecord.setLocation(-1000, -1000);
                return;
            }

            // selected record index:
            int delete_selected = -1;

            // wait until any record get seleted:
            while(delete_selected == -1)
            {
                try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}

                delete_selected = table.getSelectedRow(); // select any record:

                if(f_cancelDelete){f_cancelDelete = false; return;} // escape.
            
                // System.out.println("in first while " + delete_selected); // temp.
            }
            
            // update text:
            ops_deleteRecord.setVerticalAlignment(JLabel.TOP);
            ops_deleteRecord.setText("Delete this record permanantly?");

            // set buttons:
            cancelDelete.setLocation(sb.getMyXPose(xp.cancelDelete), sb.getMyYPose(yp.cancelDelete));
            delete.setLocation(sb.getMyXPose(xp.Wdelete), sb.getMyYPose(yp.Wdelete));


            // wait until delete or cancel:
            while(true)
            {
                try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}

                delete_selected = table.getSelectedRow(); // can select another record:

                if(f_delet){ f_delet = false; break; }

                else if(f_cancelDelete){ f_cancelDelete = false; ops_deleteRecord.setLocation(-1000, -1000); return;}
            }
            ops_deleteRecord.setLocation(-1000, -1000);


            System.out.println("deleted record is: " + delete_selected); // temp.


            // send request for delete:
            String[] request = new String[6];
            request[0] = "DELETE_RECORD";
            request[1] = user;
            request[2] = database;
            request[3] = tables[selectedIDX];
            request[4] = data[delete_selected][0];
            request[5] = "1";

            String[] response = cs.request(request);


            if(response[0].equals("TRUE"))
            {
                messageBox.setText("Record deleted successfully");
            }
            else
            {
                messageBox.setText("cannot delete record!");
            }

            showMessage();
            hideMessage();
        }

    }




    // get all tables of the database:
    private String[] getTables() throws IOException
    {

        // String[] t_names = {"Table1", "Table2", "Table3"}; // temp list.
        String[] t_names;

        // get table count:
        String[] request = new String[4];
        request[0] = "GET_TABLE_COUNT";
        request[1] = user;
        request[2] = database;
        request[3] = "1";


        String[] response = cs.request(request); 
        Integer table_count = Integer.parseInt(response[0]); // get integer value of table counts.

        request = null;

        // requesting for table names:
        request = new String[4];
        request[0] = "GET_TABLES";
        request[1] = user;
        request[2] = database;
        request[3] = table_count.toString();

        t_names = cs.request(request);

        return t_names;
    }



    // set pan1:
    private void setPan1()
    {
       dataBase_name.setText(database);
       pan1.add(dataBase_name);

       // create and set name to table-name labels:
       int yPose = 50;
       int initial = 90;
    

       table_label = new JLabel[tables.length];
       for(int i=0; i<tables.length; i++)
       {
            table_label[i] = new JLabel();
            table_label[i].setText(tables[i]);
            table_label[i].setBounds(0, (initial + (yPose*i)), 400, 30);
            table_label[i].setBackground(new Color(27, 28, 33));
            table_label[i].setForeground(new Color(140, 140, 140));
            table_label[i].setFont(new Font("Monospaced", Font.BOLD, 20));
            table_label[i].setVerticalAlignment(JLabel.CENTER);
            table_label[i].setHorizontalAlignment(JLabel.CENTER);
            table_label[i].setOpaque(true);
            table_label[i].addMouseListener(this);
            pan1.add(table_label[i]);
         
       }

       
    }


    // set workwindow: (add all components and hide)
    private JFrame setWorkwindow(JFrame frame)
    {
        panMenu.add(back_btn);

        setPan1(); 

        panMenu.add(refresh);
        frame.add(panMenu);
        frame.add(pan1);

        pan2.add(messageBox);
        pan2.add(tableOperation);
        pan2.add(viewedBy);
        pan2.add(ops_addData);
        pan2.add(ops_updateRecord);
        pan2.add(ops_deleteRecord);
        frame.add(pan2);

        frame.setVisible(true);
        return frame;
    }


    // clear work window:
    private void clearWorkWindow(JFrame frame)
    {
        frame.setVisible(false);

        panMenu.removeAll();
        pan1.removeAll();
        pan2.removeAll();

        frame.remove(panMenu);
        frame.remove(pan1);
        frame.remove(pan2);
        
        frame.setVisible(true);
    }


    // refresh window:
    public void refreshWindow(JFrame frame)
    {
        input_att_text.setFocusable(true);
        input_att_text.setFocusable(false);
        att_val.setFocusable(true);
        att_val.setFocusable(false);
        frame.setState(JFrame.ICONIFIED);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(sb.screenWidth, sb.screenHeight);
    }



    // MAIN FUNC:
    // run manipulating operations:
    public void runWorkWindow(JFrame frame, String userName, String selectedDB) throws IOException
    {
    
        user = userName;
        database = selectedDB;
        tables = getTables();
        
        frame = setWorkwindow(frame);
        refreshWindow(frame);

        while(true)
        {
            try{ Thread.sleep(30);} catch (Exception e){};

            if(f_back)
            {
                clearWorkWindow(frame);
                f_back = false;
                return;
            }
            else if(refresh_)
            {
                refreshWindow(frame);
                refresh_ = false;
            }
            else if(f_viewTable)
            {
                viewTable();
                f_viewTable = false;
            }
            else if(f_addData)
            {
                addRecord();
                f_addData = false;
                // refreshWindow(frame);
            }
            else if(f_updateData)
            {
                updateRecord();
                f_updateData = false;
                // refreshWindow(frame);
            }
            else if(f_deleteRec)
            {
                deleteRecord();
                f_deleteRec = false;
            }
            
        }
    }






    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(e.getSource() == refresh){ refresh_ = true; }
        
        // tables:
        for(int i=0; i< tables.length; i++)
        {
            if(e.getSource() == table_label[i])
            {                   
                tFrame.setLocation(-1000, -1000); // hide table frame.
                viewedBy.setLocation(-1000, -1000); // hide table name.

                f_nothing = false;

                if(selected != null)selected.setBackground(new Color(27, 28, 33)); // previous reset.

                selected = table_label[i]; // update selected.

                selectedIDX = i; // update index.

                tableOperation.setLocation(selected.getX(), selected.getY()); // show operation options.

                selected.setBackground(new Color(39, 40, 45)); // highlight selected.

                ops_addData.setLocation(-1000, -1000); // hide add data ops.
                if(f_addData)f_cancel = true; // escape from add data loop.

                ops_updateRecord.setLocation(-1000, -1000); // hide update data ops.
                if(f_updateData)f_cancelUpdate = true; // escape from update loop.

                ops_deleteRecord.setLocation(-1000, -1000); // hide delete data ops.
                if(f_deleteRec)f_cancelDelete = true; // escape from delete loop.
                

                break;
            }
            else
            {
                f_nothing = true;
            }
        }

        // operation:
        if(e.getSource() == view_table)
        {
            // reset previous:
            if(tFrame != null)tFrame.dispose();
            viewedBy.setLocation(-1000, -1000);
            viewedBy.setText(selected.getText());
            f_view = false;

            view_table.setBackground(new Color(27, 28, 33));
            view_table.setForeground(new Color(140, 140, 140));

            f_viewTable = true;
            
        }
        else if(e.getSource() == add_data)
        {
            ops_addData.setText(selected.getText());

            add_data.setBackground(new Color(27, 28, 33));
            add_data.setForeground(new Color(140, 140, 140));

            if(f_view)ops_addData.setLocation(sb.getMyXPose(xp.ops_addData), sb.getMyYPose(yp.ops_addData));
            else ops_addData.setLocation(sb.getMyXPose(xp.ops_addData), sb.getMyYPose(yp.ops_addData));

            f_addData = true;
        }
        else if(e.getSource() == update_record)
        {
            // reset previous:
            if(tFrame != null)tFrame.dispose();
            viewedBy.setText(selected.getText());
            viewedBy.setLocation(-1000, -1000);
            f_view = false;

            update_record.setBackground(new Color(27, 28, 33));
            update_record.setForeground(new Color(140, 140, 140));

            ops_updateRecord.setLocation(sb.getMyXPose(xp.ops_updateRecord), sb.getMyYPose(yp.ops_updateRecord));

            f_updateData = true;
        }
        else if(e.getSource() == delete_recodr)
        {
            // reset previous:
            if(tFrame != null)tFrame.dispose();
            viewedBy.setText(selected.getText());
            viewedBy.setLocation(-1000, -1000);
            f_view = false;

            delete_recodr.setBackground(new Color(27, 28, 33));
            delete_recodr.setForeground(new Color(140, 140, 140));

            ops_deleteRecord.setLocation(sb.getMyXPose(xp.ops_deleteRecord), sb.getMyYPose(yp.ops_deleteRecord));

            f_deleteRec = true;
        }

        // add record:
        if(e.getSource() == nextW)
        {
            f_next = true;
        }
        else if(e.getSource() == cancelW)
        {
            ops_addData.setLocation(-1000, -1000);
            f_cancel = true;
        }

        // update record:
        else if(e.getSource() == updateThisRec)
        {
            updateThisRec.setBackground(new Color(25, 27, 30));
            f_allowUpdate = true;
        }
        else if(e.getSource() == cancelUpdt)
        {
            cancelUpdt.setBackground(new Color(25, 27, 30));
            f_cancelUpdate  = true;
        }
        else if(e.getSource() == uptdVal)
        {
            uptdVal.setBackground(new Color(25, 27, 30));
            f_updtVal = true;
        }
        else if(e.getSource() == nextUpdt)
        {
            nextUpdt.setBackground(new Color(25, 27, 30));
            f_nextUpdt = true;
        }

        // delete record:
        else if(e.getSource() == cancelDelete)
        {
            cancelDelete.setBackground(new Color(25, 27, 30));
            cancelDelete.setForeground(new Color(61, 61, 61));

            f_cancelDelete = true;
        }
        else if(e.getSource() == delete)
        {
            delete.setBackground(new Color(25, 27, 30));
            delete.setForeground(new Color(61, 61, 61));

            f_delet = true;
        }
        

        
        // clicked anywhere else:
        if(f_nothing)
        {
            if(viewedBy.getText() != "")viewedBy.setLocation(sb.getMyXPose(xp.viewedBy), sb.getMyYPose(yp.viewedBy)); // set viewed by.
            tFrame.setLocation(403, 124); // set data table.
            tableOperation.setLocation(-1000, -1000); // hide table operation label.
        }


        // back button:
        if(e.getSource() == back_btn)
        {
            if(tFrame != null)tFrame.dispose(); // remove table.
            // reset viewed by:
            viewedBy.setText("");
            viewedBy.setLocation(-1000, -1000);
            f_view = false;

            back_btn.setBackground(new ColorUIResource(36, 36, 40));
            back_btn.setForeground(new Color(0, 0, 0));

            // reset operation labels:
            tableOperation.setLocation(-1000, -1000);

            ops_addData.setLocation(-1000, -1000);

            ops_updateRecord.setLocation(-1000, -1000);
            updateThisRec.setLocation(-1000, -1000);

            ops_deleteRecord.setLocation(-1000, -1000);

            // reset flags:
            f_back = true;
            f_cancel = true;
            f_cancelUpdate = true;
            f_cancelDelete = true;
            
        }

    }
    @Override
    public void mouseEntered(MouseEvent e)
    {
        for(int i=0; i< table_label.length; i++)
        {
            if(e.getSource() == table_label[i] && table_label[i] != selected)
            {
                table_label[i].setBackground(new Color(33, 34, 39));
            }
        }
        if(e.getSource() == back_btn)
        {
            back_btn.setBackground(new ColorUIResource(42, 42, 46));
            back_btn.setForeground(new Color(50, 20, 20));
        }
        else if(e.getSource() == refresh) 
        {
            refresh.setBackground(new Color(70, 70, 70));
            refresh.setForeground(new Color(0, 0, 40));
        }


        // operation:
        else if(e.getSource() == view_table)
        {
            view_table.setBackground(new Color(53, 54, 59));
            view_table.setForeground(new Color(10, 10, 20));
        }
        else if(e.getSource() == add_data)
        {
            add_data.setBackground(new Color(53, 54, 59));
            add_data.setForeground(new Color(10, 10, 20));
        }
        else if(e.getSource() == update_record)
        {
            update_record.setBackground(new Color(53, 54, 59));
            update_record.setForeground(new Color(10, 10, 20));
        }
        else if(e.getSource() == delete_recodr)
        {
            delete_recodr.setBackground(new Color(53, 54, 59));
            delete_recodr.setForeground(new Color(10, 10, 20));
        }

        // add record:
        else if(e.getSource() == nextW)
        {
            nextW.setBackground(new Color(65, 67, 70));
            nextW.setForeground(new Color(11, 11, 31));

        }
        else if(e.getSource() == cancelW)
        {
            cancelW.setBackground(new Color(65, 67, 70));
            cancelW.setForeground(new Color(31, 11, 11));
        }

        // update record:
        else if(e.getSource() == updateThisRec)
        {
            updateThisRec.setBackground(new Color(45, 47, 50));
            updateThisRec.setForeground(new Color(31, 31, 51));
        }
        else if(e.getSource() == cancelUpdt)
        {
            cancelUpdt.setBackground(new Color(45, 47, 50));
            cancelUpdt.setForeground(new Color(31, 11, 11));
        }
        else if(e.getSource() == uptdVal)
        {
            uptdVal.setBackground(new Color(45, 47, 50));
            uptdVal.setForeground(new Color(11, 11, 31));
        }
        else if(e.getSource() == nextUpdt)
        {
            nextUpdt.setBackground(new Color(45, 47, 50));
            nextUpdt.setForeground(new Color(11, 31, 11));
        }

        // delete record:
        else if(e.getSource() == delete)
        {
            delete.setBackground(new Color(45, 47, 50));
            delete.setForeground(new Color(31, 11, 11));
        }
        else if(e.getSource() == cancelDelete)
        {
            cancelDelete.setBackground(new Color(45, 47, 50));
            cancelDelete.setForeground(new Color(11, 11, 31));
        }


    }   
    @Override
    public void mouseExited(MouseEvent e)
    {
        for(int i=0; i< table_label.length; i++)
        {
            if(e.getSource() == table_label[i] && table_label[i] != selected)
            {
                table_label[i].setBackground(new Color(27, 28, 33));
            }
        }
        if(e.getSource() == back_btn)
        {
            back_btn.setBackground(new ColorUIResource(36, 36, 40));
            back_btn.setForeground(new Color(0, 0, 0));
        }
        else if(e.getSource() == refresh)
        {
            refresh.setBackground(new Color(50, 50, 50));
            refresh.setForeground(new Color(0, 0, 0));
        }

        // operation:
        else if(e.getSource() == view_table)
        {
            view_table.setBackground(new Color(27, 28, 33));
            view_table.setForeground(new Color(140, 140, 140));
            
        }
        else if(e.getSource() == add_data)
        {
            add_data.setBackground(new Color(27, 28, 33));
            add_data.setForeground(new Color(140, 140, 140));
        }
        else if(e.getSource() == update_record)
        {
            update_record.setBackground(new Color(27, 28, 33));
            update_record.setForeground(new Color(140, 140, 140));
        }
        else if(e.getSource() == delete_recodr)
        {
            delete_recodr.setBackground(new Color(27, 28, 33));
            delete_recodr.setForeground(new Color(140, 140, 140));
        }

        // add record:
        else if(e.getSource() == nextW)
        {
            nextW.setBackground(new Color(25, 27, 30));
            nextW.setForeground(new Color(61, 61, 61));
        }
        else if(e.getSource() == cancelW)
        {
            cancelW.setBackground(new Color(25, 27, 30));
            cancelW.setForeground(new Color(61, 61, 61));
        }

        // update record:
        else if(e.getSource() == updateThisRec)
        {
            updateThisRec.setBackground(new Color(25, 27, 30));
            updateThisRec.setForeground(new Color(61, 61, 61));
        }
        else if(e.getSource() == cancelUpdt)
        {
            cancelUpdt.setBackground(new Color(25, 27, 30));
            cancelUpdt.setForeground(new Color(61, 61, 61));
        }
        else if(e.getSource() == uptdVal)
        {
            uptdVal.setBackground(new Color(25, 27, 30));
            uptdVal.setForeground(new Color(61, 61, 61));
        }
        else if(e.getSource() == nextUpdt)
        {
            nextUpdt.setBackground(new Color(25, 27, 30));
            nextUpdt.setForeground(new Color(61, 61, 61));
        }

        // delete record:
        else if(e.getSource() == delete)
        {
            delete.setBackground(new Color(25, 27, 30));
            delete.setForeground(new Color(61, 61, 61));
        }
        else if(e.getSource() == cancelDelete)
        {
            cancelDelete.setBackground(new Color(25, 27, 30));
            cancelDelete.setForeground(new Color(61, 61, 61));
        }


    }

    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e){}

}