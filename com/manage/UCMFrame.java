package com.manage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.bean.Book;
import com.bean.Order;
import com.bean.User;
import com.dao.DAO;
import com.frame.RegFrame;
import com.panel.BackgroundPanel;
import com.tool.SaveUserStateTool;

public class UCMFrame extends JFrame {

	private static final long serialVersionUID = 6311132153212365519L;
	private JPanel jContentPane = null;
    private URL url = null;
    private Image image = null;
    private BackgroundPanel jPanel = null;
    private JLabel jl1 = null;
    private JLabel jl2 = null;
    private JTextField tf_uno = null;
    private JTextField tf_user = null;
    private JButton jb_search = null;
    private JButton jb_reset = null;
    private JButton jb_add = null;
    private JButton jb_user = null;
    private JButton jb_address = null;
    private JScrollPane jsp1=null;
    private JScrollPane jsp2=null;
    private DefaultTableModel model1;
    private DefaultTableModel model2;
    private JTable table1;
    private JTable table2;
    List<User> list = new ArrayList<>();
    String[] biaotou1 = { "用户ID", "用户名","性别","银行卡号","权限标识","出生日期"}; 
    String[] biaotou2 = { "地址ID", "收件人","收件地址","邮编","联系电话","是否默认"}; 
    private int uno;
    

    
    private BackgroundPanel getJPanel() {
        if (jPanel == null) {
        	jl1 = getJLabel(33,"用户ID：");
        	jl1.setForeground(Color.white);
            jl2 = getJLabel(330,"用户名：");
            jl2.setForeground(Color.white);
            tf_uno=getTf(110,10);
            tf_user=getTf(400,10);
            url = UCMFrame.class.getResource("/image/背景1.jpg"); // 获得图片的URL
            image = new ImageIcon(url).getImage(); // 创建图像对象
            jPanel = new BackgroundPanel(image);
            jPanel.setLayout(null);
            jPanel.add(jl1);
            jPanel.add(jl2);
            jPanel.add(tf_uno);
            jPanel.add(tf_user);
            jPanel.add(getJB_search(), null);
            jPanel.add(getJB_reset(), null);
            jPanel.add(getJB_add(), null);
            jPanel.add(getJB_user(), null);
            jPanel.add(getJB_address(), null);
            

    		jsp1=getJsp(50,model1); 		
    		jsp2=getJsp(272,model2);

    		list=executeUser(1,"select uno,uname,sex,cardnum,authority,birthday from userinfo");
    		jsp1.setViewportView(table1);
    		jPanel.add(jsp1);
    		jsp2.setViewportView(table2);
    		jPanel.add(jsp2);

            
    	}
        return jPanel;
    }
    
    private JScrollPane getJsp(int y,DefaultTableModel model) {
    	JScrollPane jsp = new JScrollPane();
        jsp.setBounds(30,y,720,210);
		JTable table=new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jsp.setViewportView(table);
		return jsp;
    }
    
    private JButton getJB_search() {
        if (jb_search == null) {
        	jb_search = new JButton("搜索");
        	jb_search.setBounds(new Rectangle(660, 11, 60, 30));
        	jb_search.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_search.setMargin(new Insets(0, 0, 0, 0));
        	jb_search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	String sql="select uno,uname,sex,cardnum,authority,birthday from userinfo where uno like '%"
                			+tf_uno.getText()+"%' and uname like '%"+tf_user.getText()+"%'";
                	list=executeUser(1,sql);
            		jsp1.setViewportView(table1);
            		jPanel.add(jsp1);
                }
            });
        }
        return jb_search;
    }

    private JButton getJB_reset() {
        if (jb_reset == null) {
        	jb_reset = new JButton("重置");
        	jb_reset.setBounds(new Rectangle(100, 495, 60, 30));
        	jb_reset.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_reset.setMargin(new Insets(0, 0, 0, 0));
        	jb_reset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	list=executeUser(1,"select uno,uname,sex,cardnum,authority,birthday from userinfo");
            		jsp1.setViewportView(table1);
            		jPanel.add(jsp1);

            		jsp2.setViewportView(table2);
            		jPanel.add(jsp2);
                }
            });
        }
        return jb_reset;
    }
    
    private JButton getJB_add() {
        if (jb_add == null) {
        	jb_add = new JButton("添加用户");
        	jb_add.setBounds(new Rectangle(400, 495, 75, 30));
        	jb_add.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_add.setMargin(new Insets(0, 0, 0, 0));
        	jb_add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RegFrame rf=new RegFrame();
                }
            });
        }
        return jb_add;
    }
    
    private JButton getJB_user() {
        if (jb_user == null) {
        	jb_user = new JButton("修改用户权限");
        	jb_user.setBounds(new Rectangle(492, 495, 90, 30));
        	jb_user.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_user.setMargin(new Insets(0, 0, 0, 0));
        	jb_user.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int flag=DAO.get_flag("update userinfo set authority='"+table1.getValueAt(table1.getSelectedRow(), 4)+"' where uno='"+table1.getValueAt(table1.getSelectedRow(), 0)+"'");
                    if (flag > 0) {
                        JOptionPane.showMessageDialog(null, "修改成功。");
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败。");
                    }
                }
            });
        }
        return jb_user;
    }
    
    private JButton getJB_address() {
        if (jb_address == null) {
        	jb_address = new JButton("修改地址");
        	jb_address.setBounds(new Rectangle(600, 495, 75, 30));
        	jb_address.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_address.setMargin(new Insets(0, 0, 0, 0));
        	jb_address.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	User user=new User();
                	user.setAno(Integer.parseInt(table2.getValueAt(table2.getSelectedRow(), 0).toString()));
                	user.setReceiver(table2.getValueAt(table2.getSelectedRow(), 1).toString());
                	user.setAddress(table2.getValueAt(table2.getSelectedRow(), 2).toString());
                	user.setPostcode(String.valueOf(table2.getValueAt(table2.getSelectedRow(), 3)));
                	user.setPhone(table2.getValueAt(table2.getSelectedRow(), 4).toString());
                	user.setState(Boolean.parseBoolean(table2.getValueAt(table2.getSelectedRow(), 5).toString()));
                	UpdateFrame uf=new UpdateFrame(4,user);
                }
            });
        }
        return jb_address;
    }
    
    public List<User> executeUser(int id,String sql) {
    	Connection conn = DAO.getConn(); // 获取数据库连接
    	Object [][]cellData={};
    	if(id==1) {
    		model1=new DefaultTableModel(cellData,biaotou1);
    		table1=new JTable(model1);
    		table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    		table1.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e) {
    				uno=Integer.parseInt(table1.getValueAt(table1.getSelectedRow(), 0).toString());
    				list=executeUser(2,"select ano,name,addr,postcode,phone,defaultstate from ainfo where uno=?");
    				jsp2.setViewportView(table2);
    	    		jPanel.add(jsp2);
    			}
    		});
    	}
    	model2=new DefaultTableModel(cellData,biaotou2);
		table2=new JTable(model2);
    	list = new ArrayList<>();
    	try {
        	PreparedStatement ps = conn.prepareStatement(sql);
        	if(id==1) {
        		ResultSet rs = ps.executeQuery(); 
        		while (rs.next()) { // 循环遍历查询结果集
                	User user= new User();
                	user.setUno(rs.getInt(1));
                	user.setName(rs.getString(2));
                	user.setSex(rs.getString(3));
                	user.setCard(rs.getNString(4));
                	user.setState(rs.getBoolean(5));
                	user.setBirth(rs.getDate(6).toString()+"  "+rs.getTime(6).toString());
                    list.add(user);
                }
                for(int i=0;i<list.size();i++) {
                	User user=list.get(i);
            		model1.addRow(new Object[] {user.getUno(),user.getName(),user.getSex(),user.getCard(),user.getState(),user.getBirth()});
            	}
        	}else {
        		ps.setString(1, String.valueOf(uno)) ;
                ResultSet rs = ps.executeQuery(); 
                while (rs.next()) { // 循环遍历查询结果集
                	User user= new User();
                	user.setAno(rs.getInt(1));
                	user.setReceiver(rs.getString(2));
                	user.setAddress(rs.getString(3));
                	user.setPostcode(rs.getString(4));
                	user.setPhone(rs.getString(5));
                	user.setState(rs.getBoolean(6));
                    list.add(user);
                }
                for(int i=0;i<list.size();i++) {
                	User user=list.get(i);
            		model2.addRow(new Object[] {user.getAno(),user.getReceiver(),user.getAddress(),user.getPostcode(),user.getPhone(),user.getState()});
            	}
                }
        	}catch (SQLException e) {
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
    
    public UCMFrame() {
        super();
        setSize(800, 580);
        setContentPane(getJContentPane());
        setTitle("用户管理");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);      

    }
    
    private JLabel getJLabel(int x,String t) {
    	Font font=new Font("楷体",Font.BOLD,18);
    	JLabel jlabel = new JLabel(t);
        jlabel.setBounds(new Rectangle(x, 12, 200, 25));
        jlabel.setFont(font);
        return jlabel;
    }
    
    private JTextField getTf(int x,int y) {
    	JTextField jtf= new JTextField();
        jtf.setBounds(new Rectangle(x, y, 150, 28));
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
