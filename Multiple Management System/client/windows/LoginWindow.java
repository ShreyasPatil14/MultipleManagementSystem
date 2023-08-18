package windows;



import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.plaf.ColorUIResource;

import authentication_.Authentication;
import component.SetDimension;
import encryption.Encrypt;


/* class description:
        name: LoginWindow
        work: authenticate user(sign in)  or  create new account(sign up)
        main function: getLogin()
        sub functions: getLoginChoice(), getSignIn(), getSignUp(), checkInput()
        output: return a valid user name (existing or newly created)
*/



public class LoginWindow extends SetDimension implements KeyListener, MouseListener
{
    Authentication aut = new Authentication();
    Encrypt enc = new Encrypt();
    
    // flags:
    public static boolean fsignup = false;
    public static boolean fsignin = false;
    public static boolean fnext = false;
    public static boolean fback = false;

    // labels and textfields:
    public JLabel loginLabel = new JLabel();
    public JLabel loginTitle = new JLabel("LOGIN");

    public JLabel username = new JLabel();
    public JLabel userText = new JLabel("Username  ");
    public JTextField usernameInput = new JTextField();

    public JLabel password = new JLabel();
    public JLabel passwordText = new JLabel("Password   ");
    public JPasswordField passwordInput = new JPasswordField();

    public JLabel confirm = new JLabel();
    public JLabel confirmText = new JLabel("   Confirm   ");
    public JPasswordField confirmInput = new JPasswordField();

    public JLabel warning = new JLabel("warning!");

    public JLabel bgbtnlabel = new JLabel();
    public JLabel signIn = new JLabel("Sign in");
    public JLabel signUp = new JLabel("Sign up");
    public JLabel next = new JLabel("none");
    public JLabel back = new JLabel("Back");
    
    // int lwHeight = sb.getMyHeight(hr.loginLabel);
    public int lwWidth = sb.getMyWidth(wr.loginLabel);

    // defining all components of login label in:
    public LoginWindow()
    {
        
        // main login window:
        loginLabel.setBounds(sb.getMyXPose(xp.loginLabel), sb.getMyYPose(yp.loginLabel), sb.getMyWidth(wr.loginLabel), sb.getMyHeight(hr.loginLabel));
        loginLabel.setBackground(new ColorUIResource(7, 31, 59));

        // window_title (LOGIN):
        loginTitle.setBounds(sb.getMyXPose(xp.loginTitle), sb.getMyYPose(yp.loginTitle), lwWidth, sb.getMyHeight(hr.loginTitle));
        loginTitle.setFont(new Font("colonna MT", Font.PLAIN, 50));
        loginTitle.setVerticalAlignment(JLabel.CENTER);
        loginTitle.setHorizontalAlignment(JLabel.CENTER);
        loginTitle.setOpaque(true);
        loginLabel.add(loginTitle);
        loginLabel.setOpaque(true);

    
        // username text label:
        userText.setFont(new Font("Dialog", Font.PLAIN, 20));
        userText.setBackground(new ColorUIResource(11, 38, 66));
        userText.setForeground(new ColorUIResource(61, 97, 128));
        userText.setOpaque(true);
        // username textField:
        usernameInput.setPreferredSize(new Dimension(sb.getMyWidth(wr.usernameInput),sb.getMyHeight(hr.usernameInput)));
        usernameInput.setFont(new Font("Dialog", Font.PLAIN, 20));
        usernameInput.setBackground(new ColorUIResource(7, 31, 59));
        usernameInput.setForeground(new ColorUIResource(255, 255, 255));
        usernameInput.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        // username bg label:
        username.setBounds(sb.getMyXPose(xp.username), sb.getMyYPose(yp.username), lwWidth, sb.getMyHeight(hr.username));
        username.setBackground(new ColorUIResource(11, 38, 66));
        username.setLayout(new FlowLayout());
        username.add(userText);
        username.add(usernameInput);
        username.setOpaque(true);

        
        // password text label:
        passwordText.setFont(new Font("Dialog", Font.PLAIN, 20));
        passwordText.setBackground(new ColorUIResource(11, 38, 66));
        passwordText.setForeground(new ColorUIResource(61, 97, 128));
        passwordText.setOpaque(true);
        // password textField:
        passwordInput.setPreferredSize(new Dimension(sb.getMyWidth(wr.passwordInput),sb.getMyHeight(hr.passwordInput)));
        passwordInput.setFont(new Font("Dialog", Font.PLAIN, 20));
        passwordInput.setBackground(new ColorUIResource(7, 31, 59));
        passwordInput.setForeground(new ColorUIResource(255, 255, 255));
        passwordInput.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        passwordInput.setEchoChar('*');
        // password bg label:
        password.setBounds(sb.getMyXPose(xp.password), sb.getMyYPose(yp.password), lwWidth, sb.getMyHeight(hr.password));
        password.setBackground(new ColorUIResource(11, 38, 66));
        password.setLayout(new FlowLayout());
        password.add(passwordText);
        password.add(passwordInput);
        password.setOpaque(true);

        // confirm password text label:
        confirmText.setFont(new Font("Dialog", Font.PLAIN, 20));
        confirmText.setBackground(new ColorUIResource(11, 38, 66));
        confirmText.setForeground(new ColorUIResource(61, 97, 128));
        confirmText.setOpaque(true);
        // confirm password textField:
        confirmInput.setPreferredSize(new Dimension(sb.getMyWidth(wr.confirmInput),sb.getMyHeight(hr.confirmInput)));
        confirmInput.setFont(new Font("Dialog", Font.PLAIN, 20));
        confirmInput.setBackground(new ColorUIResource(7, 31, 59));
        confirmInput.setForeground(new ColorUIResource(255, 255, 255));
        confirmInput.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        confirmInput.setEchoChar('*');
        // confirm password bg label:
        confirm.setBounds(sb.getMyXPose(xp.confirm), sb.getMyYPose(yp.confirm), lwWidth, sb.getMyHeight(hr.confirm));
        confirm.setBackground(new ColorUIResource(11, 38, 66));
        confirm.setLayout(new FlowLayout());
        confirm.add(confirmText);
        confirm.add(confirmInput);
        confirm.setOpaque(true);
        
        // warning label:
        warning.setBounds(-100, -100, lwWidth, sb.getMyHeight(hr.warning));
        warning.setFont(new Font("Consolas", Font.PLAIN, 20));
        warning.setHorizontalAlignment(JLabel.CENTER);
        warning.setVerticalAlignment(JLabel.CENTER);
        warning.setBackground(new ColorUIResource(30, 31, 59));
        warning.setForeground(new ColorUIResource(255, 0, 0));
        warning.setOpaque(true);
        loginLabel.add(warning);

        
        // sign in:
        signIn.setPreferredSize(new Dimension(sb.getMyWidth(wr.signIn),sb.getMyHeight(hr.signIn)));
        signIn.setHorizontalAlignment(JLabel.CENTER);
        signIn.setVerticalTextPosition(JLabel.CENTER);
        signIn.setFont(new Font("Dialog", Font.PLAIN, 20));
        signIn.setBackground(new ColorUIResource(11, 38, 66));
        signIn.setForeground(new ColorUIResource(61, 97, 128));
        signIn.setOpaque(true);
        signIn.addMouseListener(this);
        // sign up:
        signUp.setPreferredSize(new Dimension(sb.getMyWidth(wr.signUp),sb.getMyHeight(hr.signUp)));
        signUp.setHorizontalAlignment(JLabel.CENTER);
        signUp.setVerticalAlignment(JLabel.CENTER);
        signUp.setFont(new Font("Dialog", Font.PLAIN, 20));
        signUp.setBackground(new ColorUIResource(11, 38, 66));
        signUp.setForeground(new ColorUIResource(61, 97, 128));
        signUp.setOpaque(true);
        signUp.addMouseListener(this);
        // back:
        back.setPreferredSize(new Dimension(sb.getMyWidth(wr.backl),sb.getMyHeight(hr.backl)));
        back.setHorizontalAlignment(JLabel.CENTER);
        back.setVerticalAlignment(JLabel.CENTER);
        back.setFont(new Font("Dialog", Font.PLAIN, 20));
        back.setBackground(new ColorUIResource(11, 38, 66));
        back.setForeground(new ColorUIResource(61, 97, 128));
        back.setOpaque(true);
        back.addMouseListener(this);
        // next:
        next.setPreferredSize(new Dimension(sb.getMyWidth(wr.nextl),sb.getMyHeight(hr.nextl)));
        next.setHorizontalAlignment(JLabel.CENTER);
        next.setVerticalAlignment(JLabel.CENTER);
        next.setFont(new Font("Dialog", Font.PLAIN, 20));
        next.setBackground(new ColorUIResource(11, 38, 66));
        next.setForeground(new ColorUIResource(61, 97, 128));
        next.setOpaque(true);
        next.addMouseListener(this);
        //button bg label:
        bgbtnlabel.setBounds(sb.getMyXPose(xp.bgbtnlabel), sb.getMyYPose(yp.bgbtnlabel), lwWidth, sb.getMyHeight(hr.bgbtnlabel));
        bgbtnlabel.setBackground(new ColorUIResource(7, 31, 59));
        bgbtnlabel.setLayout(new FlowLayout());
    }

    // check input validation:
    public boolean checkInput()
    {
        String uName = usernameInput.getText();
        char[] passarr = passwordInput.getPassword();
        char[] confarr;
        String pass = new String(passarr);
    
        if(fsignup)
        {
            
            confarr = confirmInput.getPassword();
            String conf = new String(confarr);
            
            if(uName.length() == 0 || pass.length() == 0 || conf.length() == 0)
            {
                warning.setText("Incomplete information!");
                warning.setLocation(sb.getMyXPose(xp.warning), sb.getMyYPose(yp.warning));
                warning.setOpaque(true);
                try {
                    Thread.sleep(1400);
                } catch (Exception e) {
                e.printStackTrace();
                }
                warning.setLocation(-100, -100);
                warning.setOpaque(true);

                return false;
            }
            
            if(!pass.equals(conf))
            {
                warning.setText("confirm password again!");
                warning.setLocation(sb.getMyXPose(xp.warning), sb.getMyYPose(yp.warning));
                warning.setOpaque(true);
                try {
                    Thread.sleep(1400);
                } catch (Exception e) {
                e.printStackTrace();
                }
                confirmInput.setText("");
                warning.setLocation(-100, -100);
                warning.setOpaque(true);
                
                return false;
            }
        }
        else
        {
            if(uName.length() == 0 || pass.length() == 0)
            {
                warning.setText("Incomplete login credentials!");
                warning.setLocation(sb.getMyXPose(xp.warning), sb.getMyYPose(yp.warning));
                warning.setOpaque(true);
                try {
                    Thread.sleep(1400);
                } catch (Exception e) {
                e.printStackTrace();
                }
                warning.setLocation(-100, -100);
                warning.setOpaque(true);
                
                return false;
            }
        }
        
        return true;
    }

   

    // sign-in or sign-up:
    public JFrame getLoginChoice(JFrame frame)
    {
        frame.setVisible(false);
        bgbtnlabel.add(signIn);
        bgbtnlabel.add(signUp);
        bgbtnlabel.setOpaque(true);

        loginLabel.add(bgbtnlabel);
        frame.add(loginLabel);
        frame.setVisible(true);

        return frame;
    }

    // sign-in
    public JFrame getSignIn(JFrame frame)
    {
        next.setText("Sign In");
        frame.setVisible(false);
        loginLabel.add(username);
        loginLabel.add(password);
        frame.add(loginLabel);
        frame.setVisible(true);
        return frame;
    }

    // sign-up
    public JFrame getSignUp(JFrame frame)
    {
        next.setText("Sign Up");
        frame.setVisible(false);
        loginLabel.add(username);
        loginLabel.add(password);
        loginLabel.add(confirm);
        frame.add(loginLabel);
        frame.setVisible(true);
        return frame;
    }


    // initialize loginlabel:
    public void clearLoginLabel()
    {
        fnext = false;
        fback = false;

        usernameInput.setText("");
        passwordInput.setText("");
        confirmInput.setText("");

        loginLabel.remove(username);
        loginLabel.remove(password);
        loginLabel.remove(confirm);
        bgbtnlabel.remove(back);
        bgbtnlabel.remove(next);
        bgbtnlabel.setOpaque(true);
        loginLabel.remove(bgbtnlabel);
        loginLabel.setOpaque(true);
        
    }


    // [Main Func.]
    // operates login process:
    public String getLogin(JFrame frame) throws IOException
    {
        while(true) // loop until successful login or go back for another option
        {
            fsignin = false;
            fsignup = false;
            frame = getLoginChoice(frame); // choose: sign-in or sign-up
           

            while(true) // wait until user choice: (sign-in/sign-up)
            {
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                e.printStackTrace();
                }

                if(fsignin) break;
                else if(fsignup)break;
                
            }

            // re arranging button:
            bgbtnlabel.remove(signIn);
            bgbtnlabel.remove(signUp);
    
            bgbtnlabel.add(back);
            bgbtnlabel.add(next);
            bgbtnlabel.setOpaque(true);

            // providing selected option:
            if(fsignin)
            {
                frame = getSignIn(frame);
            }
            else
            {
                frame = getSignUp(frame);
            }


            // waiting to next or back:
            while(true)
            {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if(fback) // re-choice:
                { 
                    frame.setVisible(false);

                    usernameInput.setText("");
                    passwordInput.setText("");
                    confirmInput.setText("");

                    bgbtnlabel.remove(back);
                    bgbtnlabel.remove(next);
                    bgbtnlabel.setOpaque(true);
                    loginLabel.remove(username);
                    loginLabel.remove(password);
                    loginLabel.remove(confirm);
                    loginLabel.setOpaque(true);  
                    frame.add(loginLabel);

                    frame.setVisible(true);    

                    fback = false;
                    break;
                }
                

                if(fnext) // input check and send data:
                {
                    
                    boolean checked = checkInput();

                    if(checked) // valid input:
                    {
                        
                        String aut_info[] = new String[4];
                        

                        // convert all data into the string array and return to the main body.
                        aut_info[1] = usernameInput.getText();
                        
                        char[] passwdCh = passwordInput.getPassword();
                        String passwd = new String(passwdCh);
                        aut_info[2] = enc.getCipherText(passwd);
                        System.out.println("input password enc: " + aut_info[2]);

                        
                        if(fsignin)
                        {
                            aut_info[0] = "signin";
                            aut_info[3] = "1";
                            boolean validation = aut.aut_signIn(aut_info);

                            if(!validation)
                            {
                                warning.setText("Invalid login credentials!");
                                warning.setLocation(sb.getMyXPose(xp.warning), sb.getMyYPose(yp.warning));
                                warning.setOpaque(true);
                                try {
                                    Thread.sleep(1400);
                                } catch (Exception e) {
                                e.printStackTrace();
                                }
                                warning.setLocation(-100, -100);
                                warning.setOpaque(true);
                                fnext = false;
                                continue;
                            }
                        }
                        else
                        {
                            aut_info[0] = "signup";
                            aut_info[3] = "1";
                            boolean validation = aut.aut_signUp(aut_info);

                            if(!validation)
                            {
                                warning.setText("Username already exist! Try another one.");
                                
                                warning.setLocation(sb.getMyXPose(xp.warning), sb.getMyYPose(yp.warning));
                                warning.setOpaque(true);
                                try {
                                    Thread.sleep(2000);
                                } catch (Exception e) {
                                e.printStackTrace();
                                }

                                usernameInput.setText("");
                                warning.setLocation(-100, -100);
                                warning.setOpaque(true);
                                fnext = false;
                                continue;
                            }
                            
                        }

                        // terminating function:
                        clearLoginLabel();
                        frame.setVisible(false);
                        frame.remove(loginLabel);
                        frame.setVisible(true);
                        
                        
                        return aut_info[1];
                            
                    }
                    else
                    { // invalid input:
                        fnext = false;
                        continue;
                    }

                }
            }

        }
    }


    // mouse event:
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == signIn)
        {
            fsignin = true;
        }
        else if(e.getSource() == signUp)
        {
            fsignup = true;
        }
        else if(e.getSource() == next)
        {
            fnext = true;
        }
        else if(e.getSource() == back)
        {
            fback = true;
        }
   }
    @Override
    public void mousePressed(MouseEvent e) {
   }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e)
    {

        if(e.getSource() == signIn)
        {
            signIn.setForeground(new ColorUIResource(101, 127, 158));
            signIn.setBackground(new ColorUIResource(1, 28, 56));
        }
        else if(e.getSource() == signUp)
        {
            signUp.setForeground(new ColorUIResource(101, 127, 158));
            signUp.setBackground(new ColorUIResource(1, 28, 56));
        }
        else if(e.getSource() == next)
        {
            next.setForeground(new ColorUIResource(101, 127, 158));
            next.setBackground(new ColorUIResource(1, 28, 56));
        }
        else if(e.getSource() == back)
        {
            back.setForeground(new ColorUIResource(101, 127, 158));
            back.setBackground(new ColorUIResource(1, 28, 56));
        }
    }
    @Override
    public void mouseExited(MouseEvent e)
    {
        if(e.getSource() == signIn)
        {
            signIn.setForeground(new ColorUIResource(61, 97, 128));
            signIn.setBackground(new ColorUIResource(11, 38, 66));
        }
        else if(e.getSource() == signUp)
        {
            signUp.setForeground(new ColorUIResource(61, 97, 128));
            signUp.setBackground(new ColorUIResource(11, 38, 66));
        }
        else if(e.getSource() == next)
        {
            next.setForeground(new ColorUIResource(61, 97, 128));
            next.setBackground(new ColorUIResource(11, 38, 66));
        }
        else if(e.getSource() == back)
        {
            back.setForeground(new ColorUIResource(61, 97, 128));
            back.setBackground(new ColorUIResource(11, 38, 66));
        }
    }


    // key events:
    @Override
    public void keyTyped(KeyEvent e) {
   }
    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
   }

}
