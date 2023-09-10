package com.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.*;

import com.manage.*;
import com.panel.BackgroundPanel;
import com.tool.SaveUserStateTool;

public class MainFrame extends JFrame {
	 
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
    private URL url = null;
    private Image image = null;
    private BackgroundPanel jPanel = null;
    private JLabel jl1 = null;
    private JLabel jl2 = null;
    private JMenuBar jJMenuBar = null;
    private JMenuItem jMenuItem1 = null;
    private JMenuItem jMenuItem2 = null;
    private JMenuItem jMenuItem3 = null;
    private JMenuItem jMenuItem4 = null;
    private JButton jb_login = null;
    
    private String user=SaveUserStateTool.getUsername();
    private String pwd=SaveUserStateTool.getPassword();
    private boolean state=SaveUserStateTool.getState();

    
    private BackgroundPanel getJPanel() {
        if (jPanel == null) {
        	jl1 = new JLabel("网上图书销售系统");
            jl1.setBounds(330, 75, 500, 80);
            jl1.setFont(new Font("华文楷体",Font.BOLD,50));
        	jl2 = new JLabel("",JLabel.RIGHT);
            jl2.setBounds(440, 15, 150, 20);
            jl2.setFont(new Font("",Font.BOLD,15));
            if(user==null) {
            	jl2.setText("请登录");
            }else {
            	jl2.setText(user);
            }
            url = MainFrame.class.getResource("/image/主界面.jpg"); // 获得图片的URL
            image = new ImageIcon(url).getImage(); // 创建图像对象
            jPanel = new BackgroundPanel(image);
            jPanel.setLayout(null);
            jPanel.add(jl1);
            jPanel.add(jl2);
            jPanel.add(getJB_login(), null);
        }
        return jPanel;
    }
    
    private JMenuBar getJJMenuBar() {
        if (jJMenuBar == null) {
            jJMenuBar = new JMenuBar();
            jJMenuBar.add(getJMenuItem1());
            jJMenuBar.add(getJMenuItem2());
            jJMenuBar.add(getJMenuItem3());
            jJMenuBar.add(getJMenuItem4());
        }
        return jJMenuBar;
    }

    
    private JMenuItem getJMenuItem1() {
        if (jMenuItem1 == null) {
            jMenuItem1 = new JMenuItem();
            jMenuItem1.setText("首页");
            jMenuItem1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(state==false) {
                		HomePageFrame hpf = new HomePageFrame();
                	}else {
                		HPMFrame hpmf=new HPMFrame();
                	}
                }
            });
        }
        return jMenuItem1;
    }

    private JMenuItem getJMenuItem2() {
        if (jMenuItem2 == null) {
            jMenuItem2 = new JMenuItem();
            jMenuItem2.setText("购物车");
            jMenuItem2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(user==null) {
                		JOptionPane.showMessageDialog(null, "请登录");
                	}else {
                		if(state==false) {
                			ShoppingCartFrame scf = new ShoppingCartFrame();
                    	}else {
                    		SCMFrame scmf=new SCMFrame();
                    	}
                	}
                }
            });
        }
        return jMenuItem2;
    }
    
    
    private JMenuItem getJMenuItem3() {
        if (jMenuItem3 == null) {
            jMenuItem3 = new JMenuItem();
            jMenuItem3.setText("订单中心");
            jMenuItem3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(user==null) {
                		JOptionPane.showMessageDialog(null, "请登录");
                	}else {
                		if(state==false) {
                    		OrderCenterFrame ocf = new OrderCenterFrame();
                    	}else {
                    		OCMFrame ocmf=new OCMFrame();
                    	}
                	}
                }
            });
        }
        return jMenuItem3;
    }

    private JMenuItem getJMenuItem4() {
        if (jMenuItem4 == null) {
            jMenuItem4 = new JMenuItem();
            jMenuItem4.setText("用户中心");
            jMenuItem4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(user==null) {
                		JOptionPane.showMessageDialog(null, "请登录");
                	}else {
                		if(state==false) {
                    		UserCenterFrame ucf = new UserCenterFrame();
                    	}else {
                    		UCMFrame ucmf=new UCMFrame();
                    	}
                	}
                }
            });
        }
        return jMenuItem4;
    }
    
    private JButton getJB_login() {
        if (jb_login == null) {
        	jb_login = new JButton("登录/注册");
        	jb_login.setBounds(new Rectangle(600, 10, 75, 30));
        	jb_login.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_login.setMargin(new Insets(0, 0, 0, 0));
        	jb_login.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    LoginFrame lf=new LoginFrame();
                    dispose();
                }
            });
        }
        return jb_login;
    }
    
    public static void main(String[] arg) {
    	new MainFrame();
    }
    
    public MainFrame() {
        super();
        setSize(800, 580);
        setJMenuBar(getJJMenuBar());
        setContentPane(getJContentPane());
        setTitle("网上图书销售系统");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);
        
        addWindowListener (new WindowAdapter (){
        	@Override
        	public void windowClosing (WindowEvent e){
        		int exit = JOptionPane.showConfirmDialog (null, "确定退出？", "友情提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        		if (exit == JOptionPane.YES_OPTION){
        			System.exit (0);
        		}else {
        			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        		}
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







