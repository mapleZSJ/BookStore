package com.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.bean.Book;
import com.dao.DAO;
import com.panel.BackgroundPanel;
import com.tool.SaveUserStateTool;

public class ShoppingCartFrame extends JFrame {

	private static final long serialVersionUID = 9010068349977450378L;
	private JPanel jContentPane = null;
    private URL url = null;
    private Image image = null;
    private BackgroundPanel jPanel = null;
    private JButton jb_buy = null;
    private JButton jb_update = null;
    private JButton jb_delete = null;
    private JScrollPane jsp=null;
    private DefaultTableModel model;
    private JTable table;
    List<Book> list = new ArrayList<>();
    String[] biaotou = {"书名", "单价","数量"}; 
    
    private String user=SaveUserStateTool.getUsername();
    private String pwd=SaveUserStateTool.getPassword();

    
    private BackgroundPanel getJPanel() {
        if (jPanel == null) {
            url = ShoppingCartFrame.class.getResource("/image/背景.jpg"); // 获得图片的URL
            image = new ImageIcon(url).getImage(); // 创建图像对象
            jPanel = new BackgroundPanel(image);
            jPanel.setLayout(null);
            jPanel.add(getJB_buy(), null);
            jPanel.add(getJB_update(), null);
            jPanel.add(getJB_delete(), null);
            
            jsp = new JScrollPane();
            jsp.setBounds(30,30,720,440);
            
            list=executeBook("exec user_cart ?,?");
    		jsp.setViewportView(table);
    		jPanel.add(jsp);
    		
          
    	}
        return jPanel;
    }

    
    private JButton getJB_buy() {
        if (jb_buy == null) {
        	jb_buy = new JButton("购买");
        	jb_buy.setBounds(new Rectangle(400, 490, 75, 30));
        	jb_buy.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_buy.setMargin(new Insets(0, 0, 0, 0));
        	jb_buy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	Book book=new Book();
                	String bno=DAO.get_result("select bno from binfo where bname='"+table.getValueAt(table.getSelectedRow(), 0).toString()+"'");
                	book.setBno(bno);
                	book.setBname(table.getValueAt(table.getSelectedRow(), 0).toString());
                	book.setPrice(Double.parseDouble(table.getValueAt(table.getSelectedRow(), 1).toString()));
                	book.setStock(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 2).toString()));
                	if(Integer.parseInt(DAO.get_result("select stock from binfo where bno='"+bno+"'"))==0) {
                    	JOptionPane.showMessageDialog(null, "库存不足!");
                    }else if(Boolean.parseBoolean(DAO.get_result("select bookstate from binfo where bno='"+bno+"'"))==true) {
                    	JOptionPane.showMessageDialog(null, "图书已下架!");
                    }else {
                    	BuyFrame bf=new BuyFrame(book);
                    }
                }
            });
        }
        return jb_buy;
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
                	int uno=Integer.parseInt(DAO.get_result("select uno from userinfo where uname='"+user+"' and pwd='"+pwd+"'"));
                	int flag=DAO.get_flag("update shoppingcart set amount='"+table.getValueAt(table.getSelectedRow(), 2).toString()+"' where uno='"+uno+"' and bno='"+table.getValueAt(table.getSelectedRow(), 0).toString()+"' ");
                    if (flag > 0) {
                        JOptionPane.showMessageDialog(null, "修改成功。");
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败。");
                    }
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
                	int flag=DAO.get_flag("delete from shoppingcart where uno='"+uno+"' and bno='"+table.getValueAt(table.getSelectedRow(), 0).toString()+"' ");
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
    
    
    public List<Book> executeBook(String sql) {
    	Connection conn = DAO.getConn(); // 获取数据库连接
    	Object [][]cellData={};
        model=new DefaultTableModel(cellData,biaotou);
		table=new JTable(model);
    	list = new ArrayList<>();
        try {
        	PreparedStatement ps = conn.prepareStatement(sql);
        	ps.setString(1, user); 
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery(); 
            while (rs.next()) { // 循环遍历查询结果集
            	Book book= new Book();
            	book.setBname(rs.getString(1));
            	book.setPrice(rs.getDouble(2));
            	book.setStock(rs.getInt(3));
                list.add(book);
            }
            for(int i=0;i<list.size();i++) {
    			Book book=list.get(i);
    			model.addRow(new Object[] {book.getBname(),book.getPrice(),book.getStock()});
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
    
    public ShoppingCartFrame() {
        super();
        setSize(800, 580);
        setContentPane(getJContentPane());
        setTitle("购物车");
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
