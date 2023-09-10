package com.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.*;

import com.bean.User;
import com.dao.UserDao;
import com.panel.BackgroundPanel;
import com.tool.SaveUserStateTool;

public class LoginFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private URL url = null;
    private Image image = null;
    private BackgroundPanel jPanel = null;// 声明自定义背景面板对象
    private JLabel jl1 = null;
    private JLabel jl2 = null;
    private JTextField tf_username = null;
    private JPasswordField pf_password = null;
    private JButton jb_login = null;
    private JButton jb_reg = null;
    private JButton jb_exit = null;
    

    private JPanel getJPanel() {
        if (jPanel == null) {
        	Font font=new Font("",Font.BOLD,15);
        	jl1 = new JLabel("用 户 名：");
            jl1.setBounds(new Rectangle(100, 95, 80, 20));
            jl1.setFont(font);
            jl2 = new JLabel("密     码：");
            jl2.setBounds(new Rectangle(100, 155, 80, 20));
            jl2.setFont(font);
            url = LoginFrame.class.getResource("/image/背景.jpg"); // 获得图片的URL
            image = new ImageIcon(url).getImage(); // 创建图像对象
            jPanel = new BackgroundPanel(image);
            jPanel.setLayout(null);
            jPanel.add(jl1, null);
            jPanel.add(jl2, null);
            jPanel.add(getTf_username(), null);
            jPanel.add(getPf_password(), null);
            jPanel.add(getJB_login(), null);
            jPanel.add(getJB_reg(), null);
            jPanel.add(getJB_exit(), null);
        }
        return jPanel;
    }

    private JTextField getTf_username() {
        if (tf_username == null) {
            tf_username = new JTextField();
            tf_username.setBounds(new Rectangle(180, 90, 180, 30));
            tf_username.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pf_password.requestFocus();
                }
            });
        }
        return tf_username;
    }
    
    private JPasswordField getPf_password() {
        if (pf_password == null) {
            pf_password = new JPasswordField();
            pf_password.setBounds(new Rectangle(180, 150, 180, 30));
            pf_password.setEchoChar('*');
            pf_password.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = tf_username.getText().trim();
                    String password = new String(pf_password.getPassword());
                    User user = new User();
                    user.setName(username);
                    user.setPwd(password);
                    if (UserDao.okUser(user)) {
                        MainFrame mf = new MainFrame();
                        dispose();
                    }
                }
            });
        }
        return pf_password;
    }
    
    private JButton getJB_login() {
        if (jb_login == null) {
        	jb_login = new JButton("登录");
        	jb_login.setBounds(new Rectangle(98, 220, 70, 30));
        	jb_login.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_login.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = tf_username.getText().trim();
                    String password = new String(pf_password.getPassword());
                    User user = new User();
                    user.setName(username);
                    user.setPwd(password);
                    if (UserDao.okUser(user)) {
                        MainFrame mf = new MainFrame();
                        dispose();
                    }
                }
            });
        }
        return jb_login;
    }
    
    private JButton getJB_reg() {
        if (jb_reg == null) {
        	jb_reg = new JButton("注册");
        	jb_reg.setBounds(new Rectangle(198, 220, 70, 30));
        	jb_reg.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_reg.setMargin(new Insets(0, 0, 0, 0));
        	jb_reg.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RegFrame rf=new RegFrame();
                }
            });
        }
        return jb_reg;
    }
    
    private JButton getJB_exit() {
        if (jb_exit == null) {
        	jb_exit = new JButton("退出登录");
        	jb_exit.setBounds(new Rectangle(298, 220, 70, 30));
        	jb_exit.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_exit.setMargin(new Insets(0, 0, 0, 0));
        	jb_exit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	SaveUserStateTool.setUsername(null);
                	SaveUserStateTool.setPassword(null);
                	SaveUserStateTool.setState(false);
                	MainFrame mf = new MainFrame();
                    dispose();
                }
            });
        }
        return jb_exit;
    }
    
    public LoginFrame() {
        super();
        setSize(500, 380);
        setContentPane(getJContentPane());
        setTitle("登录");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);
        
        
        addWindowListener (new WindowAdapter (){
        	@Override
        	public void windowClosing (WindowEvent e){
        		MainFrame mf = new MainFrame();
        	}
        });
    }
    
    
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanel(), BorderLayout.CENTER);
        }
        return jContentPane;
    }
}
