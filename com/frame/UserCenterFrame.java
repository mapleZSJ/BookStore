package com.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.bean.Book;
import com.bean.User;
import com.dao.DAO;
import com.manage.UpdateFrame;
import com.panel.BackgroundPanel;
import com.tool.SaveUserStateTool;

public class UserCenterFrame extends JFrame {
	
	private static final long serialVersionUID = 3811812378911641186L;
	private JPanel jContentPane = null;
    private URL url = null;
    private Image image = null;
    private BackgroundPanel jPanel = null;
    private JLabel jl1 = null;
    private JLabel jl2 = null;
    private JLabel jl3 = null;
    private JLabel jl4 = null;
    private JTextField tf_user = null;
    private JTextField tf_sex = null;
    private JTextField tf_card = null;
    private JTextField tf_birth = null;
    private JButton jb_add = null;
    private JButton jb_update = null;
    private JButton jb_delete = null;
    private JButton jb_pwd = null;
    private JButton jb_paypwd = null;
    private JButton jb_user = null;
    private JButton jb_reset = null;
    private JScrollPane jsp=null;
    private DefaultTableModel model;
    private JTable table;
    List<User> list = new ArrayList<>();
    String[] biaotou = { "收件人", "收件地址","邮编","联系电话","是否默认"}; 
    
    private String user=SaveUserStateTool.getUsername();
    private String pwd=SaveUserStateTool.getPassword();

    
    private BackgroundPanel getJPanel() {
        if (jPanel == null) {
        	jl1 = getJLabel(33,"用 户 名：");
            jl2 = getJLabel(88,"性    别：");
            jl3 = getJLabel(143,"银行卡号：");
            jl4 = getJLabel(198,"出生日期：");
            tf_user = getTf(33,user);
            tf_sex = getTf(85,"");
            tf_card = getTf(140,"");
            tf_birth = getTf(195,"");
            url = UserCenterFrame.class.getResource("/image/背景.jpg"); // 获得图片的URL
            image = new ImageIcon(url).getImage(); // 创建图像对象
            jPanel = new BackgroundPanel(image);
            jPanel.setLayout(null);
            jPanel.add(jl1);
            jPanel.add(jl2);
            jPanel.add(jl3);
            jPanel.add(jl4);
            jPanel.add(tf_user);
            jPanel.add(tf_sex);
            jPanel.add(tf_card);
            jPanel.add(tf_birth);
            jPanel.add(getJB_add(), null);
            jPanel.add(getJB_update(), null);
            jPanel.add(getJB_delete(), null);
            jPanel.add(getJB_pwd(), null);
            jPanel.add(getJB_paypwd(), null);
            jPanel.add(getJB_user(), null);
            jPanel.add(getJB_reset(), null);
            
            jsp = new JScrollPane();
            jsp.setBounds(30,260,720,220);
            
            list=executeUser("exec user_address ?,?");
    		jsp.setViewportView(table);
    		jPanel.add(jsp);

            
    	}
        return jPanel;
    }

    private JButton getJB_pwd() {
        if (jb_pwd == null) {
        	jb_pwd = new JButton("修改登录密码");
        	jb_pwd.setBounds(new Rectangle(500, 50, 100, 30));
        	jb_pwd.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_pwd.setMargin(new Insets(0, 0, 0, 0));
        	jb_pwd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	UpdateFrame uf=new UpdateFrame(0);
                }
            });
        }
        return jb_pwd;
    }
    
    private JButton getJB_paypwd() {
        if (jb_paypwd == null) {
        	jb_paypwd = new JButton("修改支付密码");
        	jb_paypwd.setBounds(new Rectangle(500, 100, 100, 30));
        	jb_paypwd.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_paypwd.setMargin(new Insets(0, 0, 0, 0));
        	jb_paypwd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	UpdateFrame uf=new UpdateFrame(1);
                }
            });
        }
        return jb_paypwd;
    }
    
    private JButton getJB_user() {
        if (jb_user == null) {
        	jb_user = new JButton("修改用户信息");
        	jb_user.setBounds(new Rectangle(500, 150, 100, 30));
        	jb_user.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_user.setMargin(new Insets(0, 0, 0, 0));
        	jb_user.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
        }
        return jb_user;
    }
    
    private JButton getJB_reset() {
        if (jb_reset == null) {
        	jb_reset = new JButton("重置");
        	jb_reset.setBounds(new Rectangle(100, 490, 75, 30));
        	jb_reset.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_reset.setMargin(new Insets(0, 0, 0, 0));
        	jb_reset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	list=executeUser("exec user_address ?,?");
            		jsp.setViewportView(table);
            		jPanel.add(jsp);
                }
            });
        }
        return jb_reset;
    }
    
    private JButton getJB_add() {
        if (jb_add == null) {
        	jb_add = new JButton("添加");
        	jb_add.setBounds(new Rectangle(400, 490, 75, 30));
        	jb_add.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_add.setMargin(new Insets(0, 0, 0, 0));
        	jb_add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	UpdateFrame uf=new UpdateFrame(3);
                }
            });
        }
        return jb_add;
    }
   
    
    private JButton getJB_update() {
        if (jb_update == null) {
        	jb_update = new JButton("修改");
        	jb_update.setBounds(new Rectangle(500, 490, 75, 30));
        	jb_update.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_update.setMargin(new Insets(0, 0, 0, 0));
        	jb_update.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	User user=new User();
                	int uno=Integer.parseInt(DAO.get_result("select uno from userinfo where uname='"+user+"' and pwd='"+pwd+"'"));
                	int ano=Integer.parseInt(DAO.get_result("select ano from ainfo where uno='"+uno+"' and addr='"+table.getValueAt(table.getSelectedRow(), 1).toString()+"'"));
                	user.setAno(ano);
                	user.setReceiver(table.getValueAt(table.getSelectedRow(), 0).toString());
                	user.setAddress(table.getValueAt(table.getSelectedRow(), 1).toString());
                	user.setPostcode(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
                	user.setPhone(table.getValueAt(table.getSelectedRow(), 3).toString());
                	user.setState(Boolean.parseBoolean(table.getValueAt(table.getSelectedRow(), 4).toString()));
                	UpdateFrame uf=new UpdateFrame(4,user);
                }
            });
        }
        return jb_update;
    }
    
    private JButton getJB_delete() {
        if (jb_delete == null) {
        	jb_delete = new JButton("删除");
        	jb_delete.setBounds(new Rectangle(600, 490, 75, 30));
        	jb_delete.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_delete.setMargin(new Insets(0, 0, 0, 0));
        	jb_delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	int uno=Integer.parseInt(DAO.get_result("select uno from userinfo where uname='"+user+"' and pwd='"+pwd+"'"));
                	int flag=DAO.get_flag("delete from ainfo where uno='"+uno+"' and addr='"+table.getValueAt(table.getSelectedRow(), 1).toString()+"' ");
                    if (flag > 0) {
                        JOptionPane.showMessageDialog(null, "删除成功。");
                    } else {
                        JOptionPane.showMessageDialog(null, "删除失败。");
                    }
                }
            });
        }
        return jb_delete;
    }
    
    
    public List<User> executeUser(String sql) {
    	Connection conn = DAO.getConn(); // 获取数据库连接
    	Object [][]cellData={};
        model=new DefaultTableModel(cellData,biaotou);
		table=new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	list = new ArrayList<>();
        try {
        	PreparedStatement ps = conn.prepareStatement("select sex,cardnum,birthday from userinfo where uname=? and pwd=?");
        	ps.setString(1, user); 
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { // 循环遍历查询结果集
            	tf_sex.setText(rs.getString(1));
            	tf_card.setText(rs.getNString(2));
            	tf_birth.setText(rs.getDate(3).toString()+"  "+rs.getTime(3).toString());
            }
        	ps = conn.prepareStatement(sql);
        	ps.setString(1, user); 
            ps.setString(2, pwd);
            rs = ps.executeQuery();
            while (rs.next()) { // 循环遍历查询结果集
        		User user= new User();
            	user.setReceiver(rs.getString(1));
            	user.setAddress(rs.getString(2));
            	user.setPostcode(rs.getString(3));
            	user.setPhone(rs.getString(4));
            	user.setState(rs.getBoolean(5));
                list.add(user);
            }
        	for(int i=0;i<list.size();i++) {
    			User user=list.get(i);
    			model.addRow(new Object[] {user.getReceiver(),user.getAddress(),user.getPostcode(),user.getPhone(),user.getState()});
    		}
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	try {
                conn.close(); // 关闭数据库连接对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    public UserCenterFrame() {
        super();
        setSize(800, 580);
        setContentPane(getJContentPane());
        setTitle("用户中心");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);      

    }
    
    private JLabel getJLabel(int y,String t) {
    	Font font=new Font("楷体",Font.BOLD,20);
    	JLabel jlabel = new JLabel(t);
        jlabel.setBounds(new Rectangle(50, y, 200, 25));
        jlabel.setFont(font);
        return jlabel;
    }
    
    private JTextField getTf(int y,String s) {
    	JTextField jtf=new JTextField();
    	jtf.setText(s);
        jtf.setBounds(new Rectangle(150, y, 200, 30));
        jtf.setEditable(false);
        return jtf;
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
