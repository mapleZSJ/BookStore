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
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.bean.Book;
import com.dao.DAO;
import com.panel.BackgroundPanel;
import com.tool.SaveUserStateTool;

public class SCMFrame extends JFrame {
	
	private static final long serialVersionUID = 4574592672502021070L;
	private JPanel jContentPane = null;
    private URL url = null;
    private Image image = null;
    private BackgroundPanel jPanel = null;
    private JLabel jl1 = null;
    private JLabel jl2 = null;
    private JTextField tf_user = null;
    private JTextField tf_bname = null;
    private JButton jb_search = null;
    private JButton jb_reset = null;
    private JScrollPane jsp=null;
    private DefaultTableModel model;
    private JTable table;
    List<Book> list = new ArrayList<>();
    String[] biaotou = { "购物车ID","用户名","书名", "单价","总数目"}; 
    

    
    private BackgroundPanel getJPanel() {
        if (jPanel == null) {
        	Font font=new Font("楷体",Font.BOLD,18);
        	jl1 = new JLabel("用户名：");
        	jl1.setBounds(new Rectangle(35, 22, 200, 20));
        	jl1.setFont(font);
        	jl1.setForeground(Color.white);
        	jl2 = new JLabel("书 名：");
        	jl2.setBounds(new Rectangle(325, 22, 200, 20));
        	jl2.setFont(font);
        	jl2.setForeground(Color.white);
            url = SCMFrame.class.getResource("/image/背景1.jpg"); // 获得图片的URL
            image = new ImageIcon(url).getImage(); // 创建图像对象
            tf_user=new JTextField();
            tf_user.setBounds(new Rectangle(110, 18, 160, 28));
            tf_bname=new JTextField();
            tf_bname.setBounds(new Rectangle(385, 18, 160, 28));
            jPanel = new BackgroundPanel(image);
            jPanel.setLayout(null);
            jPanel.add(jl1);
            jPanel.add(jl2);
            jPanel.add(tf_user);
            jPanel.add(tf_bname);
            jPanel.add(getJB_search(), null);
            jPanel.add(getJB_reset(), null);
            
            jsp = new JScrollPane();
            jsp.setBounds(30,65,720,440);
            
            list=executeBook("select cno,uname,bname,shoppingcart.price,amount from shoppingcart,userinfo,binfo where shoppingcart.uno=userinfo.uno and shoppingcart.bno=binfo.bno");
    		table.setEnabled(false);
    		jsp.setViewportView(table);
    		jPanel.add(jsp);

            
    	}
        return jPanel;
    }

    
    private JButton getJB_search() {
        if (jb_search == null) {
        	jb_search = new JButton("搜索");
        	jb_search.setBounds(new Rectangle(610, 18, 55, 30));
        	jb_search.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_search.setMargin(new Insets(0, 0, 0, 0));
        	jb_search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	String sql="select cno,uname,bname,shoppingcart.price,amount from shoppingcart,userinfo,binfo where shoppingcart.uno=userinfo.uno and shoppingcart.bno=binfo.bno "
                			+" and uname like '%"+tf_user.getText()+"%' and bname like '%"+tf_bname.getText()+"%'";
                	
                	list=executeBook(sql);
                	jsp.setViewportView(table);
            		jPanel.add(jsp);
                }
            });
        }
        return jb_search;
    }
    
    private JButton getJB_reset() {
        if (jb_reset == null) {
        	jb_reset = new JButton("重置");
        	jb_reset.setBounds(new Rectangle(680, 18, 55, 30));
        	jb_reset.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_reset.setMargin(new Insets(0, 0, 0, 0));
        	jb_reset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	list=executeBook("select cno,uname,bname,shoppingcart.price,amount from shoppingcart,userinfo,binfo where shoppingcart.uno=userinfo.uno and shoppingcart.bno=binfo.bno");
                	jsp.setViewportView(table);
            		jPanel.add(jsp);
                }
            });
        }
        return jb_reset;
    }
    
    public List<Book> executeBook(String sql) {
    	Connection conn = DAO.getConn(); // 获取数据库连接
    	Object [][]cellData={};
        model=new DefaultTableModel(cellData,biaotou);
		table=new JTable(model);
    	list = new ArrayList<>();
        try {
        	PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); 
            while (rs.next()) { // 循环遍历查询结果集
            	Book book= new Book();
            	book.setBno(rs.getString(1));
            	book.setAuthor(rs.getString(2));
            	book.setBname(rs.getString(3));
            	book.setPrice(rs.getDouble(4));
            	book.setStock(rs.getInt(5));
                list.add(book);
            }
            for(int i=0;i<list.size();i++) {
        		Book book=list.get(i);
        		model.addRow(new Object[] {book.getBno(),book.getAuthor(),book.getBname(),book.getPrice(),book.getStock()});
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
    
    public SCMFrame() {
        super();
        setSize(800, 580);
        setContentPane(getJContentPane());
        setTitle("购物车管理");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);
        

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
