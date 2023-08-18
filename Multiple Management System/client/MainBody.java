// importing inbuilt packages:
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.ColorUIResource;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.Color;

// importing user defined packages:
import bounds.SetBounds;
import ratios.WidthRatio;
import windows.HomeWindow;
import windows.LoginWindow;
import windows.WorkWindow;
import ratios.HeightRatio;
import component.Components;
import metadata.*;


class FSWT extends Metadata implements KeyListener, MouseListener
{

    // creating objects of user defined class:
    SetBounds sb = new SetBounds();
    WidthRatio wr = new WidthRatio();
    HeightRatio hr = new HeightRatio();
    Components cmp = new Components();
    LoginWindow lw = new LoginWindow();
    HomeWindow hw = new HomeWindow();
    WorkWindow ww = new WorkWindow();

    // flags:
    // escape flag (shows taskbar):
    public static boolean allowEsc = false;
    public static boolean escapeFlag = false;
    
    

    // creating Gui components:
    JFrame frame = cmp.frame;
    JLabel minimizeBtn = cmp.minimizeBtn;
    JLabel closeBtn = cmp.closeBtn;
    JLabel titleBar = cmp.titleBar;
    
    JLabel panBottom = cmp.panBottom;


    // constructor:
    public FSWT() throws IOException
    {
        processManager();
    }



    // key events (escape button):
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e)
    {   
        // escape function
        if(e.getKeyCode() == 27)
        {
            if(escapeFlag && allowEsc)
            {
                frame.setSize(sb.screenWidth, sb.screenHeight);
                escapeFlag = false;
            }
            else if(allowEsc)
            {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                escapeFlag = true;
            }
        }
    }

    
    // mouse event (close button, minimize button):
    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e)
    {
        if(e.getSource() == closeBtn)
        {
            closeBtn.setBackground(Color.red);
        }
        else if(e.getSource() == minimizeBtn)
        {
            minimizeBtn.setBackground(new ColorUIResource(56, 56, 61));
        }
    } 
    @Override
    public void mouseExited(MouseEvent e)
    {
        if(e.getSource() == closeBtn)
        {
            closeBtn.setBackground(new ColorUIResource(38, 38, 42));
        }
        else if(e.getSource() == minimizeBtn)
        {
            minimizeBtn.setBackground(new ColorUIResource(38, 38, 42));
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if(e.getSource() == closeBtn)
        {
            frame.dispose();
            System.exit(0);
        }
        else if(e.getSource() == minimizeBtn)
        {
            frame.setState(JFrame.ICONIFIED);
        }
    }


    // process managing function:
    public void processManager() throws IOException
    {

        // adding root components:
        frame.add(minimizeBtn);
        frame.add(closeBtn);
        frame.add(titleBar);
        frame.add(panBottom);
        
        frame.addKeyListener(this);
        minimizeBtn.addMouseListener(this);
        closeBtn.addMouseListener(this);

        frame.setVisible(true);


        while(true)
        {
            try { Thread.sleep(10); } catch (Exception e){e.printStackTrace();}

            // login phase:
            userName = lw.getLogin(frame);
            allowEsc = true;
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            

            while(true)
            {
                // updating metadata; getting users databases:
                getDataBases();

                // get selected database for manipulation and further operations:
                selectedDB = hw.runHomeWindow(frame, userName, dataBases);


                // if user logged out from home window:
                if(selectedDB == "LOGOUT")
                {
                    clearMetaData();
                    frame.setVisible(false);
                    frame.setVisible(true);
                    frame.setSize(sb.screenWidth, sb.screenHeight);
                    allowEsc = false;
                    break;
                }
                else // else continue:
                {                    
                    ww.runWorkWindow(frame, userName, selectedDB);
                }
            }
            
        }

    }

}



public class MainBody
{
    public static void main(String args[]) throws InterruptedException, IOException
    {
        new FSWT(); 
    }
}