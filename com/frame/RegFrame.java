package com.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.*;

import com.bean.User;
import com.dao.UserDao;
import com.panel.BackgroundPanel;

public class RegFrame extends JFrame{
	
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
    private JTextField tf_user = null;
    private JPasswordField pf_pwd = null;
    private JPasswordField pf_okPwd = null;
    private JTextField tf_card = null;
    private JTextField tf_pay = null;
    private JTextField tf_birth = null;
    private JButton jb_reg = null;
    private JButton jb_exit = null;
    private JRadioButton maleRadioButton=null;
    private JRadioButton femaleRadioButton=null;
    
    
    public RegFrame() {
        super();
        setSize(750, 500);
        setContentPane(getJContentPane());
        setTitle("注册");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);
    }
    
    
    private BackgroundPanel getJPanel() {
        if (jPanel == null) {
        	jl1 = getJLabel(53,"用户名：");
        	jl2 = getJLabel(103,"密 码：");
            jl3 = getJLabel(153,"确认密码：");
            jl4 = getJLabel(203,"性 别：");
            jl5 = getJLabel(253,"银行卡号：");
            jl6 = getJLabel(303,"支付密码：");
            jl7 = getJLabel(353,"出生日期：");
            url = RegFrame.class.getResource("/image/注册.jpg"); // 获得图片的URL
            image = new ImageIcon(url).getImage(); // 创建图像对象
            jPanel = new BackgroundPanel(image);
            jPanel.setLayout(null);
            jPanel.add(jl1);
            jPanel.add(jl2);
            jPanel.add(jl3);
            jPanel.add(jl4);
            jPanel.add(jl5);
            jPanel.add(jl6);
            jPanel.add(jl7);
            jPanel.add(getTf_user(), null);
            jPanel.add(getPf_pwd(), null);
            jPanel.add(getPf_okPwd(), null);
            jPanel.add(getTf_card(), null);
            jPanel.add(getTf_pay(), null);
            jPanel.add(getTf_birth(), null);
            jPanel.add(getJB_reg(), null);
            jPanel.add(getJB_exit(), null);
            
            maleRadioButton = new JRadioButton("男");
            maleRadioButton.setFont(new Font("楷体", Font.PLAIN, 20));
            maleRadioButton.setBounds(320, 200, 50, 30);
            jPanel.add(maleRadioButton);
            femaleRadioButton = new JRadioButton("女");
            femaleRadioButton.setFont(new Font("楷体", Font.PLAIN, 20));
            femaleRadioButton.setBounds(400, 200, 50, 30);
            jPanel.add(femaleRadioButton);

            ButtonGroup group = new ButtonGroup();
            group.add(maleRadioButton);
            group.add(femaleRadioButton);
        }
        return jPanel;
    }
    

    private JTextField getTf_user() {
        if (tf_user == null) {
            tf_user = new JTextField();
            tf_user.setBounds(new Rectangle(320, 50, 160, 30));
        }
        return tf_user;
    }
    

    private JPasswordField getPf_pwd() {
        if (pf_pwd == null) {
            pf_pwd = new JPasswordField();
            pf_pwd.setBounds(new Rectangle(320, 100, 160, 30));
            pf_pwd.setEchoChar('*');
        }
        return pf_pwd;
    }
    

    private JPasswordField getPf_okPwd() {
        if (pf_okPwd == null) {
            pf_okPwd = new JPasswordField();
            pf_okPwd.setBounds(new Rectangle(320, 150, 160, 30));
            pf_okPwd.setEchoChar('*');
        }
        return pf_okPwd;
    }
    
    private JTextField getTf_card() {
        if (tf_card == null) {
            tf_card = new JTextField();
            tf_card.setBounds(new Rectangle(320, 250, 160, 30));
        }
        return tf_card;
    }
    
    private JTextField getTf_pay() {
        if (tf_pay == null) {
            tf_pay = new JTextField();
            tf_pay.setBounds(new Rectangle(320, 300, 160, 30));
            tf_pay.addKeyListener(new KeyAdapter() {
            	@Override
            	public void keyTyped(KeyEvent e) {
            		if(tf_pay.getText().length()>=6) {
            			e.consume();
            		}
            	}
            });
        }
        return tf_pay;
    }
    
    private JTextField getTf_birth() {
        if (tf_birth == null) {
            tf_birth = new JTextField();
            tf_birth.setBounds(new Rectangle(320, 350, 160, 30));
            tf_birth.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseClicked(MouseEvent e) {
            		JOptionPane.showMessageDialog(null, "日期格式：年-月-日\nXXXX-XX-XX");
            	}
            });
        }
        return tf_birth;
    }
    
    private JButton getJB_reg() {
        if (jb_reg == null) {
        	jb_reg = new JButton("注册");
        	jb_reg.setBounds(new Rectangle(510, 400, 80, 30));
        	jb_reg.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = tf_user.getText().trim(); 
                    String password = new String(pf_pwd.getPassword());
                    String okPassword = new String(pf_okPwd.getPassword());
                    String sex=maleRadioButton.getText().toString();
                    if(femaleRadioButton.isSelected()) {
                    	sex=femaleRadioButton.getText().toString();
                    }
                    String card=tf_card.getText().trim();
                    String pay=tf_pay.getText().trim();
                    String birth=tf_birth.getText().trim();
                    User user = new User(); 
                    user.setName(username); 
                    user.setPwd(password);
                    user.setOkPwd(okPassword);
                    user.setSex(sex);
                    user.setCard(card);
                    user.setPay(pay);
                    user.setBirth(birth);
                    UserDao.insertUser(user);
                }
            });
            
        }
        return jb_reg;
    }
    
    private JButton getJB_exit() {
        if (jb_exit == null) {
        	jb_exit = new JButton("返回");
        	jb_exit.setBounds(new Rectangle(125, 400, 80, 30));
        	jb_exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return jb_exit;
    }
    
    private JLabel getJLabel(int y,String t) {
    	Font font=new Font("楷体",Font.BOLD,20);
    	JLabel jlabel = new JLabel(t);
        jlabel.setBounds(new Rectangle(220, y, 200, 20));
        jlabel.setFont(font);
        return jlabel;
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
