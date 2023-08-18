// creates GUI components
package component;

// inbuilt packages
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.ColorUIResource;




public class Components extends SetDimension
{

    public JFrame frame = new JFrame();
    public JLabel minimizeBtn = new JLabel();
    public JLabel closeBtn = new JLabel();
    public JLabel titleBar = new JLabel("  Multiple Management System");
    public JLabel pan1 = new JLabel();
    public JLabel pan2 = new JLabel();
    public JLabel panMenu = new JLabel();
    public JLabel panBottom = new JLabel();

    
    public Components()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setUndecorated(true);
        frame.setSize(sb.screenWidth, sb.screenHeight);
        

        minimizeBtn.setBackground(new ColorUIResource(38, 38, 42));
        minimizeBtn.setText("-");
        minimizeBtn.setForeground(new ColorUIResource(163, 161, 152));
        minimizeBtn.setFont(new Font("Cascadia Mono Light",Font.PLAIN, 20));
        minimizeBtn.setHorizontalAlignment(JLabel.CENTER);
        minimizeBtn.setVerticalAlignment(JLabel.CENTER);
        minimizeBtn.setBounds(sb.getMyXPose(xp.minimizeBtn), sb.getMyYPose(yp.minimizeBtn), sb.getMyWidth(wr.minimizeBtn), sb.getMyHeight(hr.minimizeBtn));
        minimizeBtn.setOpaque(true);

        closeBtn.setBackground(new ColorUIResource(38, 38, 42));
        closeBtn.setText("x");
        closeBtn.setForeground(new ColorUIResource(163, 161, 152));
        closeBtn.setFont(new Font("Cascadia Mono Light",Font.PLAIN, 20));
        closeBtn.setHorizontalAlignment(JLabel.CENTER);
        closeBtn.setVerticalAlignment(JLabel.CENTER);
        closeBtn.setBounds(sb.getMyXPose(xp.closeBtn), sb.getMyYPose(yp.closeBtn), sb.getMyWidth(wr.closeBtn), sb.getMyHeight(hr.closeBtn));
        closeBtn.setOpaque(true);


        titleBar.setBackground(new ColorUIResource(43, 44, 48));
        titleBar.setForeground(new Color(100, 100, 100));
        titleBar.setFont(new Font("Monospaced", Font.PLAIN, 17));
        titleBar.setBounds(sb.getMyXPose(xp.titleBar), sb.getMyYPose(yp.titleBar), sb.getMyWidth(wr.titleBar), sb.getMyHeight(hr.titleBar));
        titleBar.setOpaque(true);

        panMenu.setBackground(new ColorUIResource(38, 38, 42));
        panMenu.setBounds(sb.getMyXPose(xp.panMenu), sb.getMyYPose(yp.panMenu), sb.getMyWidth(wr.panMenu), sb.getMyHeight(hr.panMenu));
        panMenu.setOpaque(true);

        pan1.setBackground(new ColorUIResource(25, 26, 31));
        pan1.setBounds(sb.getMyXPose(xp.pan1), sb.getMyYPose(yp.pan1), sb.getMyWidth(wr.pan1), sb.getMyHeight(hr.pan1));
        pan1.setOpaque(true);

        pan2.setBackground(new ColorUIResource(29, 30, 36));
        pan2.setBounds(sb.getMyXPose(xp.pan2), sb.getMyYPose(yp.pan2), sb.getMyWidth(wr.pan2), sb.getMyHeight(hr.pan2));
        pan2.setOpaque(true);
        
        panBottom.setText("Press [Esc] or 'Refresh' button to show Taskbar");
        panBottom.setForeground(new Color(50, 50, 50));
        panBottom.setHorizontalAlignment(JLabel.CENTER);
        panBottom.setVerticalAlignment(JLabel.CENTER);
        panBottom.setBackground(new ColorUIResource(30, 30, 30));
        panBottom.setBounds(sb.getMyXPose(xp.panBottom), sb.getMyYPose(yp.panBottom), sb.getMyWidth(wr.panBottom), sb.getMyHeight(hr.panBottom));
        panBottom.setOpaque(true);

    }

    
    
}
