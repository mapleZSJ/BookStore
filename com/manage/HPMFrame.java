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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.bean.Book;
import com.dao.BookDAO;
import com.dao.DAO;
import com.panel.BackgroundPanel;

public class HPMFrame extends JFrame {
	
	private static final long serialVersionUID = -2684903226140425359L;
	private JPanel jContentPane = null;
    private URL url = null;
    private Image image = null;
    private BackgroundPanel jPanel = null;
    private JLabel jl1 = null;
    private JLabel jl2 = null;
    private JLabel jl3 = null;
    private JLabel jl4 = null;
    private JLabel jl5 = null;
    private JButton jb_search = null;
    private JButton jb_reset = null;
    private JButton jb_add = null;
    private JButton jb_update = null;
    private JButton jb_delete = null;
    private JTextField tf_bno = null;
    private JTextField tf_bname = null;
    private JTextField tf_author = null;
    private JScrollPane jsp=null;
    private JComboBox<String> jcb_kind;
    private JComboBox<String> jcb_state;
    private DefaultTableModel model;
    private JTable table;
    List<Book> list = new ArrayList<>();
    String[] biaotou = { "书号", "书名", "作者","简介","价格","库存量","是否下架","种类号","种类"}; 
    

    
    private BackgroundPanel getJPanel() {
        if (jPanel == null) {
        	jl1 = getJLabel(35, 10,"图书ID：");
        	jl2 = getJLabel(260, 10,"图书种类：");
        	jl3 = getJLabel(530, 10,"书  名：");
        	jl4 = getJLabel(35, 45,"作  者：");
        	jl5 = getJLabel(350, 45,"是否下架：");
            url = HPMFrame.class.getResource("/image/背景1.jpg"); // 获得图片的URL
            image = new ImageIcon(url).getImage(); // 创建图像对象
            jPanel = new BackgroundPanel(image);
            jPanel.setLayout(null);
            jPanel.add(jl1);
            jPanel.add(jl2);
            jPanel.add(jl3);
            jPanel.add(jl4);
            jPanel.add(jl5);
            tf_bno=getTf(90,11);
            tf_bname=getTf(580,11);
            tf_author=getTf(90,46);
            jPanel.add(tf_bno, null);
            jPanel.add(tf_bname, null);
            jPanel.add(tf_author, null);
            jPanel.add(getJB_search(), null);
            jPanel.add(getJB_reset(), null);
            jPanel.add(getJB_add(), null);
            jPanel.add(getJB_update(), null);
            jPanel.add(getJB_delete(), null);
            
            jcb_kind = DAO.get_CBox("select kind from bkind");
            jcb_kind.setBounds(335, 11, 170, 27);
            jPanel.add(jcb_kind);
            jcb_state = new JComboBox<String>(new String[] {"","true","false"});
            jcb_state.setBounds(425, 46, 100, 27);
            jPanel.add(jcb_state);
            
            jsp = new JScrollPane();
            jsp.setBounds(30,90,720,400);

    		list=executeBook("select binfo.*,kind from binfo left join bkind on binfo.kno=bkind.kno");
    		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    		jsp.setViewportView(table);
    		jPanel.add(jsp);
            
    	}
        return jPanel;
    }
    
    private JTextField getTf(int x,int y) {
    	JTextField jtf= new JTextField();
        jtf.setBounds(new Rectangle(x, y, 150, 28));
        return jtf;
    }
    
    private String get_sql() {
    	String sql="select binfo.*,kind from binfo left join bkind on binfo.kno=bkind.kno  \r\n"
        		+ "where bno like '%"+tf_bno.getText()+"%' and bname like '%"+tf_bname.getText()+"%' \r\n"
        		+" and author like '%"+tf_author.getText()+"%' ";
    	
    	if(!jcb_kind.getSelectedItem().toString().equals("")) {
    		sql+=" and kind='"+jcb_kind.getSelectedItem().toString()+"' ";
    	}
    	if(!jcb_state.getSelectedItem().toString().equals("")) {
    		sql+=" and bookstate='"+jcb_state.getSelectedItem().toString()+"' ";
    	}
        return sql;
    }

    
    private JButton getJB_search() {
        if (jb_search == null) {
        	jb_search = new JButton("搜索");
        	jb_search.setBounds(new Rectangle(670, 45, 60, 30));
        	jb_search.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_search.setMargin(new Insets(0, 0, 0, 0));
        	jb_search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	
                	list=executeBook(get_sql());
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
        	jb_reset.setBounds(new Rectangle(100, 500, 65, 30));
        	jb_reset.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_reset.setMargin(new Insets(0, 0, 0, 0));
        	jb_reset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                	list=executeBook("select binfo.*,kind from binfo left join bkind on binfo.kno=bkind.kno");
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
        	jb_add.setBounds(new Rectangle(410, 500, 65, 30));
        	jb_add.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_add.setMargin(new Insets(0, 0, 0, 0));
        	jb_add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	BookFrame bf=new BookFrame();
                }
            });
        }
        return jb_add;
    }
    
    private JButton getJB_update() {
        if (jb_update == null) {
        	jb_update = new JButton("修改");
        	jb_update.setBounds(new Rectangle(505, 500, 65, 30));
        	jb_update.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_update.setMargin(new Insets(0, 0, 0, 0));
        	jb_update.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	Book book=new Book();
                	book.setBno(table.getValueAt(table.getSelectedRow(), 0).toString());
                	book.setBname(table.getValueAt(table.getSelectedRow(), 1).toString());
                	book.setAuthor(table.getValueAt(table.getSelectedRow(), 2).toString());
                	book.setIntro(table.getValueAt(table.getSelectedRow(), 3).toString());
                	book.setPrice(Double.parseDouble(table.getValueAt(table.getSelectedRow(), 4).toString()));
                	book.setStock(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 5).toString()));
                	book.setBstate(Boolean.parseBoolean(table.getValueAt(table.getSelectedRow(), 6).toString()));
                	book.setKno(table.getValueAt(table.getSelectedRow(), 7).toString());
                	book.setKind(table.getValueAt(table.getSelectedRow(), 8).toString());
                	BookFrame bf=new BookFrame(book);
                }
            });
        }
        return jb_update;
    }
    
    private JButton getJB_delete() {
        if (jb_delete == null) {
        	jb_delete = new JButton("删除");
        	jb_delete.setBounds(new Rectangle(600, 500, 65, 30));
        	jb_delete.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_delete.setMargin(new Insets(0, 0, 0, 0));
        	jb_delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	Book book=new Book();
                	book.setBno(table.getValueAt(table.getSelectedRow(), 0).toString());
                	BookDAO.deleteBook(book);
                	list=executeBook("select binfo.*,kind from binfo left join bkind on binfo.kno=bkind.kno");
                	jsp.setViewportView(table);
            		jPanel.add(jsp);
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
            ResultSet rs = ps.executeQuery(); 
            while (rs.next()) { // 循环遍历查询结果集
            	Book book= new Book();
            	book.setBno(rs.getString(1));
            	book.setBname(rs.getString(2));
            	book.setAuthor(rs.getString(3));
            	book.setIntro(rs.getNString(4));
            	book.setPrice(rs.getDouble(5));
            	book.setStock(rs.getInt(6));
            	book.setBstate(rs.getBoolean(7));
            	book.setKno(rs.getNString(8));
            	book.setKind(rs.getString(9));
                list.add(book);
            }
            for(int i=0;i<list.size();i++) {
        		Book book=list.get(i);
        		model.addRow(new Object[] {book.getBno(),book.getBname(),book.getAuthor(),book.getIntro(),book.getPrice(),book.getStock(),book.getBstate(),book.getKno(),book.getKind()});
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

    
    public HPMFrame() {
        super();
        setSize(800, 580);
        setContentPane(getJContentPane());
        setTitle("图书管理");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);
        
    }
    
    private JLabel getJLabel(int x,int y,String t) {
    	Font font=new Font("",Font.BOLD,15);
    	JLabel jlabel = new JLabel(t);
        jlabel.setBounds(new Rectangle(x, y, 100, 30));
        jlabel.setFont(font);
        jlabel.setForeground(Color.white);
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
