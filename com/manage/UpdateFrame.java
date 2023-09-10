package com.manage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.*;

import com.bean.User;
import com.dao.DAO;
import com.dao.UserDao;
import com.panel.BackgroundPanel;
import com.tool.SaveUserStateTool;

public class UpdateFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private URL url = null;
    private Image image = null;
    private BackgroundPanel jPanel = null;
    private JLabel jl1 = null;
    private JLabel jl2 = null;
    private JLabel jl3 = null;
    private JLabel jl4 = null;
    private JLabel jl5 = null;
    private JLabel jl6 = null;
    private JLabel jl7 = null;
    private JLabel jl8 = null;
    private JPasswordField pf_pwd = null;
    private JPasswordField pf_newpwd = null;
    private JPasswordField pf_okpwd = null;
    private JTextField tf_receiver=null;
    private JTextField tf_address = null;
    private JTextField tf_postcode= null;
    private JTextField tf_phone = null;
    private JComboBox<String> jcb_state;
    private JButton jb_add = null;
    private JButton jb_update = null;
    private JButton jb_exit = null;
    private int id = -1;
    
    private String user=SaveUserStateTool.getUsername();
    private String pwd=SaveUserStateTool.getPassword();
    private int ano;
    
    public UpdateFrame(int i) {
        super();
        setSize(500, 400);
        id=i;
        if(id<3) {
        	setTitle("修改");
        }else {
        	setTitle("添加");
        }
        setContentPane(getJContentPane());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);
    }
    public UpdateFrame(int i,User user) {
        super();
        setSize(500, 400);
        id=i;
        setTitle("修改");
        ano=user.getAno();
        setContentPane(getJContentPane());
        tf_receiver.setText(user.getReceiver());
        tf_address.setText(user.getAddress());
        tf_postcode.setText(user.getPostcode());
        tf_phone.setText(user.getPhone());
        jcb_state.setToolTipText(""+user.getState());
        jcb_state.setEnabled(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);
    }
    
    
    
    private BackgroundPanel getJPanel() {
        if (jPanel == null) {
            url = BookFrame.class.getResource(""); // 获得图片的URL
            image = new ImageIcon(url).getImage(); // 创建图像对象
            jPanel = new BackgroundPanel(image);
            jPanel.setLayout(null);
            if(id<3) {
            	initialize1();
            	jPanel.add(jl1);
                jPanel.add(jl2);
                jPanel.add(jl3);
                jPanel.add(pf_pwd, null);
                jPanel.add(pf_newpwd, null);
                jPanel.add(pf_okpwd, null);
                jPanel.add(getJB_update(), null);
            }else {
            	initialize2();
            	jPanel.add(jl4);
                jPanel.add(jl5);
                jPanel.add(jl6);
                jPanel.add(jl7);
                jPanel.add(jl8);
                jPanel.add(tf_receiver, null);
                jPanel.add(tf_address, null);
                jPanel.add(tf_postcode, null);
                jPanel.add(tf_phone, null);
                jPanel.add(getJB_add(), null);
                
                jcb_state = new JComboBox<String>(new String[] {"false","true"});
                jcb_state.setBounds(210, 250, 160, 30);
                jPanel.add(jcb_state);
            }
            
            jPanel.add(getJB_exit(), null);
           
            
        }
        return jPanel;
    }
    
    private JLabel getJLabel(int y,String t) {
    	Font font=new Font("楷体",Font.BOLD,20);
    	JLabel jlabel = new JLabel(t);
        jlabel.setBounds(new Rectangle(110, y, 200, 20));
        jlabel.setFont(font);
        return jlabel;
    }
    
    private JTextField getTf(int y) {
    	JTextField jtf=new JTextField();
        jtf.setBounds(new Rectangle(210, y, 160, 30));
        return jtf;
    }
    
    private JPasswordField getPf(int y) {
    	JPasswordField jpf = new JPasswordField();
        jpf.setBounds(new Rectangle(210, y, 160, 30));
        jpf.setEchoChar('*');
        return jpf;
    }
    
    
    
    private JButton getJB_update() {
        if (jb_update == null) {
        	jb_update = new JButton("修改");
            jb_update.setBounds(new Rectangle(300, 250, 80, 30));
        	jb_update.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	String oldpwd=pf_pwd.getText().trim();
                	String newpwd=pf_newpwd.getText().trim();
					String okpwd=pf_okpwd.getText().trim();
                	if(id==0) {
                        UserDao.updateUser(oldpwd, newpwd, okpwd,0);
                        dispose();
                	}else if(id==1){
                		UserDao.updateUser(oldpwd, newpwd, okpwd,1);
                        dispose();
                	}
                		
                    
                }
            });
            
        }
        return jb_update;
    }
    
    private JButton getJB_add() {
        if (jb_add == null) {
        	jb_add = new JButton("修改");
        	if(id==3) {
        		jb_add.setText("添加");
        		}
        	jb_add.setBounds(new Rectangle(300, 310, 80, 30));
        	jb_add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(id==3) {
                		String receiver=tf_receiver.getText().trim();
						String addr=tf_address.getText().trim();
						String postcode=tf_postcode.getText().trim();
						String phone=tf_phone.getText().trim();
						boolean state=Boolean.parseBoolean(jcb_state.getSelectedItem().toString());
						int uno=Integer.parseInt(DAO.get_result("select uno from userinfo where uname='"+user+"' and pwd='"+pwd+"'"));
						String sql="insert into ainfo(name,addr,postcode,phone,defaultstate,uno) "
								+ " values('"+receiver+"','"+addr+"','"+postcode+"','"+phone+"','"+state+"','"+uno+"')";
                        int flag=DAO.get_flag(sql);
                        if (flag > 0) {
                            JOptionPane.showMessageDialog(null, "添加成功。");
                        } else {
                            JOptionPane.showMessageDialog(null, "添加失败。");
                        }
                        dispose();
                	}else {
                		String receiver=tf_receiver.getText().trim();
						String addr=tf_address.getText().trim();
						String postcode=tf_postcode.getText().trim();
						String phone=tf_phone.getText().trim();
						boolean state=Boolean.parseBoolean(jcb_state.getSelectedItem().toString());
						String sql="update ainfo set name='"+receiver+"',addr='"+addr+"',postcode='"+postcode+"',phone='"+phone+"',defaultstate='"+state+"' "
								+ " where ano='"+ano+"'";
                        int flag=DAO.get_flag(sql);
                        if (flag > 0) {
                            JOptionPane.showMessageDialog(null, "修改成功。");
                        } else {
                            JOptionPane.showMessageDialog(null, "修改失败。");
                        }
                        dispose();
                	}
                		
  
                }
            });
            
        }
        return jb_add;
    }
    
    
    private JButton getJB_exit() {
        if (jb_exit == null) {
        	jb_exit = new JButton("返回");
        	if(id<3) {
            	jb_exit.setBounds(new Rectangle(100, 250, 80, 30));
        	}else {
        		jb_exit.setBounds(new Rectangle(100, 310, 80, 30));
        	}
        	jb_exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return jb_exit;
    }
    
    private void initialize1() {
    	jl1 = getJLabel(73,"原 密 码：");
    	jl2 = getJLabel(123,"新 密 码：");
        jl3 = getJLabel(173,"确认密码：");
        pf_pwd=getPf(70);
        pf_newpwd=getPf(120);
        pf_okpwd=getPf(170);
    }
    
    private void initialize2() {
        jl4 = getJLabel(53,"收 件 人：");
        jl5 = getJLabel(103,"地    址：");
        jl6 = getJLabel(153,"邮    编：");
        jl7 = getJLabel(203,"联系电话：");
        jl8 = getJLabel(253,"是否默认：");
        tf_receiver=getTf(50);
        tf_address=getTf(100);
        tf_postcode=getTf(150);
        tf_phone=getTf(200);
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
