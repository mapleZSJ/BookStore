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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.bean.Order;
import com.dao.DAO;
import com.panel.BackgroundPanel;
import com.tool.SaveUserStateTool;

public class OrderCenterFrame extends JFrame {

	private static final long serialVersionUID = 4384118735231268980L;
	private JPanel jContentPane = null;
    private URL url = null;
    private Image image = null;
    private BackgroundPanel jPanel = null;
    private JLabel jl1=null;
    private JButton jb_search = null;
    private JButton jb_reset = null;
    private JButton jb_delete = null;
    private JButton jb_buy = null;
    private JTextField tf_search = null;
    private JScrollPane jsp1=null;
    private JScrollPane jsp2=null;
    private DefaultTableModel model1;
    private DefaultTableModel model2;
    private JTable table1;
    private JTable table2;
    List<Order> list = new ArrayList<>();
    String[] biaotou1 = { "订单号", "总数目", "总金额","运费","支付状态","创建时间","收货地址"}; 
    String[] biaotou2 = { "书号", "书名", "数量","价格"}; 
    private int ono;
    
    private String user=SaveUserStateTool.getUsername();
    private String pwd=SaveUserStateTool.getPassword();

    
    private BackgroundPanel getJPanel() {
        if (jPanel == null) {
        	jl1=new JLabel("请输入书名：");
        	jl1.setBounds(new Rectangle(35, 13, 200, 25));
        	jl1.setFont(new Font("楷体",Font.BOLD,18));
            url = OrderCenterFrame.class.getResource("/image/背景.jpg"); // 获得图片的URL
            image = new ImageIcon(url).getImage(); // 创建图像对象
            jPanel = new BackgroundPanel(image);
            jPanel.setLayout(null);
            jPanel.add(jl1);
            jPanel.add(getTf_search(), null);
            jPanel.add(getJB_search(), null);
            jPanel.add(getJB_reset(), null);
            jPanel.add(getJB_delete(), null);
            jPanel.add(getJB_buy(), null);
            
            jsp1 = new JScrollPane();
            jsp1.setBounds(30,50,720,210);
            jsp2 = new JScrollPane();
            jsp2.setBounds(30,280,720,210);

    		list=executeOrder(1,"exec user_order ?,?");
    		jsp1.setViewportView(table1);
    		jPanel.add(jsp1);
    		jsp2.setViewportView(table2);
    		jPanel.add(jsp2);
    		

            
    	}
        return jPanel;
    }
    
    private JTextField getTf_search() {
        if (tf_search == null) {
            tf_search = new JTextField();
            tf_search.setBounds(new Rectangle(150, 11, 180, 28));
            tf_search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   
                }
            });
        }
        return tf_search;
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
                	String sql="select distinct orderinfo.ono,amount,bmoney,texpenses,paymentstate,ctime,addr \r\n"
                			+ "from (orderinfo left join userinfo on orderinfo.uno=userinfo.uno) left join ainfo on orderinfo.ano=ainfo.ano,orderdetail,binfo \r\n"
                			+ " where orderdetail.ono=orderinfo.ono and orderdetail.bno=binfo.bno "
                    		+ " and uname=? and pwd=? and bname like '%"+tf_search.getText()+"%' "
                    		+ " order by orderinfo.ono desc ";
                	
                	list=executeOrder(1,sql);
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
        	jb_reset.setBounds(new Rectangle(100, 503, 75, 30));
        	jb_reset.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_reset.setMargin(new Insets(0, 0, 0, 0));
        	jb_reset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	list=executeOrder(1,"exec user_order ?,?");
            		jsp1.setViewportView(table1);
            		jPanel.add(jsp1);
                }
            });
        }
        return jb_reset;
    }
    
    private JButton getJB_delete() {
        if (jb_delete == null) {
        	jb_delete = new JButton("删除");
        	jb_delete.setBounds(new Rectangle(500, 503, 75, 30));
        	jb_delete.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_delete.setMargin(new Insets(0, 0, 0, 0));
        	jb_delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(Boolean.parseBoolean(table1.getValueAt(table1.getSelectedRow(), 4).toString())==false) {
                    	int flag=DAO.get_flag("delete from orderdetail where ono='"+table1.getValueAt(table1.getSelectedRow(), 0).toString()+"'");
                    	if(flag>0) {
                    		int flag1=DAO.get_flag("delete from orderinfo where ono='"+table1.getValueAt(table1.getSelectedRow(), 0).toString()+"'");
                    		if (flag1 > 0) {
                                JOptionPane.showMessageDialog(null, "删除成功。");
                            } else {
                                JOptionPane.showMessageDialog(null, "删除失败。");
                            }
                    	}else {
                    		JOptionPane.showMessageDialog(null, "删除失败。");
                    	}
                    }else {
                    	JOptionPane.showMessageDialog(null, "订单已支付，不可删除！");
                    }
                }
            });
        }
        return jb_delete;
    }
    
    private JButton getJB_buy() {
        if (jb_buy == null) {
        	jb_buy = new JButton("支付");
        	jb_buy.setBounds(new Rectangle(600, 503, 75, 30));
        	jb_buy.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_buy.setMargin(new Insets(0, 0, 0, 0));
        	jb_buy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	if(Boolean.parseBoolean(table1.getValueAt(table1.getSelectedRow(), 4).toString())==false) {
                    	int flag=DAO.get_flag("update orderinfo set paymentstate='true' where ono='"+table1.getValueAt(table1.getSelectedRow(), 0).toString()+"'");
                    	if(flag>0) {
                    		JOptionPane.showMessageDialog(null, "支付成功。");
                    	}else {
                    		JOptionPane.showMessageDialog(null, "支付失败。");
                    	}
                    }else {
                    	JOptionPane.showMessageDialog(null, "订单已支付！");
                    }
                }
            });
        }
        return jb_buy;
    }
    
    
    public List<Order> executeOrder(int id,String sql) {
    	Connection conn = DAO.getConn(); // 获取数据库连接
    	Object [][]cellData={};
    	if(id==1) {
    		model1=new DefaultTableModel(cellData,biaotou1);
    		table1=new JTable(model1);
    		table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    		table1.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e) {
    				ono=Integer.parseInt(table1.getValueAt(table1.getSelectedRow(), 0).toString());
    				list=executeOrder(2,"select orderdetail.bno,bname,bamount,bprice from binfo,orderdetail where binfo.bno=orderdetail.bno and ono=?");
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
        		ps.setString(1, user); 
                ps.setString(2, pwd);
                ResultSet rs = ps.executeQuery(); 
                while (rs.next()) { // 循环遍历查询结果集
                	Order order= new Order();
                	order.setOno(rs.getInt(1));
                	order.setAmount(rs.getInt(2));
                	order.setBmoney(rs.getDouble(3));
                	order.setExpenses(rs.getDouble(4));
                	order.setState(rs.getBoolean(5));
                	order.setTime(rs.getDate(6).toString()+rs.getTime(6).toString());
                	order.setAddr(rs.getString(7));
                    list.add(order);
                }
                for(int i=0;i<list.size();i++) {
        			Order order=list.get(i);
        			model1.addRow(new Object[] {order.getOno(),order.getAmount(),order.getBmoney(),order.getExpenses(),order.getState(),order.getTime(),order.getAddr()});
        		}
        	}else{
                ps.setString(1, String.valueOf(ono)) ;
                ResultSet rs = ps.executeQuery(); 
                while (rs.next()) { // 循环遍历查询结果集
                	Order order= new Order();
                	order.setAddr(rs.getString(1));
                	order.setUname(rs.getString(2));
                	order.setAmount(rs.getInt(3));
                	order.setBmoney(rs.getDouble(4));
                    list.add(order);
                }
                for(int i=0;i<list.size();i++) {
                	Order order=list.get(i);
        			model2.addRow(new Object[] {order.getAddr(),order.getUname(),order.getAmount(),order.getBmoney()});
        		}
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
    
    public OrderCenterFrame() {
        super();
        setSize(800, 580);
        setContentPane(getJContentPane());
        setTitle("订单中心");
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
