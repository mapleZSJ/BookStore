package com.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.bean.Book;
import com.bean.Order;
import com.dao.DAO;
import com.dao.OrderDao;
import com.manage.HPMFrame;
import com.panel.BackgroundPanel;
import com.tool.SaveUserStateTool;

public class HomePageFrame extends JFrame{
	
	private static final long serialVersionUID = 1431005584406021518L;
	private JPanel jContentPane = null;
    private URL url = null;
    private Image image = null;
    private BackgroundPanel jPanel = null;
    private JLabel jl1 = null;
    private JButton jb_search = null;
    private JButton jb_reset = null;
    private JButton jb_add = null;
    private JButton jb_buy = null;
    private JTextField tf_search = null;
    private JComboBox<String> jcb;
    private JScrollPane jsp=null;
    private DefaultTableModel model;
    private JTable table;
    List<Book> list = new ArrayList<>();
    String[] biaotou = { "书号", "书名", "作者","简介","价格","库存量","是否下架","种类"}; 
    
    private String user=SaveUserStateTool.getUsername();
    private String pwd=SaveUserStateTool.getPassword();

    
    private BackgroundPanel getJPanel() {
        if (jPanel == null) {
        	Font font=new Font("",Font.BOLD,15);
        	jl1 = new JLabel("请选择图书种类：");
            jl1.setBounds(30, 11, 200, 30);
            jl1.setFont(font);
            url = HomePageFrame.class.getResource("/image/背景.jpg"); // 获得图片的URL
            image = new ImageIcon(url).getImage(); // 创建图像对象
            jPanel = new BackgroundPanel(image);
            jPanel.setLayout(null);
            jPanel.add(jl1);
            jPanel.add(getTf_search(), null);
            jPanel.add(getJB_search(), null);
            jPanel.add(getJB_reset(), null);
            jPanel.add(getJB_add(), null);
            jPanel.add(getJB_buy(), null);
            

            jcb = DAO.get_CBox("select kind from bkind");
            jcb.setBounds(150, 11, 180, 28);
            jPanel.add(jcb);
            
            jsp = new JScrollPane();
            jsp.setBounds(30,50,720,440);
           

            list=executeBook("select bno,bname,author,introduction,price,stock,bookstate,kind from binfo left join bkind on binfo.kno=bkind.kno");
    		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    		jsp.setViewportView(table);
    		jPanel.add(jsp);
    		
    		
            
    	}
        return jPanel;
    }
    
    private JTextField getTf_search() {
        if (tf_search == null) {
            tf_search = new JTextField();
            tf_search.setBounds(new Rectangle(460, 11, 180, 28));
        }
        return tf_search;
    }

    private String get_sql() {
    	String sql="select bno,bname,author,introduction,price,stock,bookstate,kind from binfo left join bkind on binfo.kno=bkind.kno  \r\n"
        		+ " where (bname like '%"+tf_search.getText()+"%' or author like '%"+tf_search.getText()+"%') ";
    	
    	if(!jcb.getSelectedItem().toString().equals("")) {
    		sql+=" and kind='"+jcb.getSelectedItem().toString()+"' ";
    	}
        return sql;
    }
    
    private JButton getJB_search() {
        if (jb_search == null) {
        	jb_search = new JButton("搜索");
        	jb_search.setBounds(new Rectangle(650, 10, 60, 30));
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
        	jb_reset.setBounds(new Rectangle(100, 500, 75, 30));
        	jb_reset.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_reset.setMargin(new Insets(0, 0, 0, 0));
        	jb_reset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	list=executeBook("select bno,bname,author,introduction,price,stock,bookstate,kind from binfo left join bkind on binfo.kno=bkind.kno");
                	jsp.setViewportView(table);
            		jPanel.add(jsp);
                }
            });
        }
        return jb_reset;
    }
    
    private JButton getJB_add() {
        if (jb_add == null) {
        	jb_add = new JButton("加入购物车");
        	jb_add.setBounds(new Rectangle(500, 500, 75, 30));
        	jb_add.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_add.setMargin(new Insets(0, 0, 0, 0));
        	jb_add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(user==null) {
                		JOptionPane.showMessageDialog(null, "请登录");
                	}else {
                		double price = Double.parseDouble(table.getValueAt(table.getSelectedRow(), 4).toString());
                        String bno = table.getValueAt(table.getSelectedRow(), 0).toString();
                        Book book = new Book(); 
                        book.setPrice(price); 
                        book.setBno(bno);
                        book.setKno(user);
                        book.setKind(pwd);
                    	int uno=Integer.parseInt(DAO.get_result("select uno from userinfo where uname='"+user+"' and pwd='"+pwd+"'"));
                        String r=DAO.get_result("select bno from shoppingcart where uno='"+uno+"' and bno='"+bno+"'");
                        if(r!=null && !r.equals("")) {
                        	DAO.get_flag("update shoppingcart set amount=amount+1 where uno='"+uno+"' and bno='"+bno+"' ");
                        }else {
                            OrderDao.insertCart(book);
                        }
                	}
                }
            });
        }
        return jb_add;
    }
    
    private JButton getJB_buy() {
        if (jb_buy == null) {
        	jb_buy = new JButton("购买");
        	jb_buy.setBounds(new Rectangle(600, 500, 75, 30));
        	jb_buy.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_buy.setMargin(new Insets(0, 0, 0, 0));
        	jb_buy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(user==null) {
                		JOptionPane.showMessageDialog(null, "请登录");
                	}else {
                		Book book=new Book();
                    	book.setBno(table.getValueAt(table.getSelectedRow(), 0).toString());
                    	book.setBname(table.getValueAt(table.getSelectedRow(), 1).toString());
                    	book.setPrice(Double.parseDouble(table.getValueAt(table.getSelectedRow(), 4).toString()));
                    	book.setStock(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 5).toString()));
                    	if(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 5).toString())==0) {
                        	JOptionPane.showMessageDialog(null, "库存不足!");
                        }else if(Boolean.parseBoolean(table.getValueAt(table.getSelectedRow(), 6).toString())==true) {
                        	JOptionPane.showMessageDialog(null, "图书已下架!");
                        }else {
                        	BuyFrame bf=new BuyFrame(book);
                        }
                	}
                	
                }
            });
        }
        return jb_buy;
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
            	book.setKind(rs.getString(8));
                list.add(book);
            }
            for(int i=0;i<list.size();i++) {
        		Book book=list.get(i);
        		model.addRow(new Object[] {book.getBno(),book.getBname(),book.getAuthor(),book.getIntro(),book.getPrice(),book.getStock(),book.getBstate(),book.getKind()});
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
    
    public HomePageFrame() {
        super();
        setSize(800, 580);
        setContentPane(getJContentPane());
        setTitle("首页");
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
