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

import com.bean.Order;
import com.dao.DAO;
import com.panel.BackgroundPanel;

public class OCMFrame extends JFrame {
	
	private static final long serialVersionUID = -9040769674438464428L;
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
    private JButton jb_search = null;
    private JButton jb_reset = null;
    private JButton jb_update = null;
    private JTextField tf_ono = null;
    private JTextField tf_user = null;
    private JTextField tf_bname = null;
    private JTextField tf_btime = null;
    private JTextField tf_etime = null;
    private JScrollPane jsp1=null;
    private JScrollPane jsp2=null;
    private JComboBox<String> jcb_state;
    private DefaultTableModel model1;
    private DefaultTableModel model2;
    private JTable table1;
    private JTable table2;
    List<Order> list = new ArrayList<>();
    String[] biaotou1 = { "订单ID",  "总数目", "总金额","运费","支付状态","创建时间","用户名","地址"}; 
    String[] biaotou2 = { "明细号", "数量","价格", "书号","书名"}; 
    private int ono;

    
    private BackgroundPanel getJPanel() {
        if (jPanel == null) {
        	jl1 = getJLabel(30, 10,"订单ID：");
         	jl2 = getJLabel(280, 10,"用户名：");
         	jl3 = getJLabel(540, 10,"书  名：");
         	jl4 = getJLabel(30, 45,"时间段：从");
         	jl5 = getJLabel(270, 45,"到");
         	jl6 = getJLabel(475, 45,"支付状态：");
            url = OCMFrame.class.getResource("/image/背景1.jpg"); // 获得图片的URL
            image = new ImageIcon(url).getImage(); // 创建图像对象
            jPanel = new BackgroundPanel(image);
            jPanel.setLayout(null);
        	jPanel.add(jl1);
            jPanel.add(jl2);
            jPanel.add(jl3);
            jPanel.add(jl4);
            jPanel.add(jl5);
            jPanel.add(jl6);
            tf_ono=getTf(85,11);
            tf_user=getTf(340,11);
            tf_bname=getTf(590,11);
            tf_btime=getTf(115,46);
            tf_etime=getTf(290,46);
            jPanel.add(tf_ono, null);
            jPanel.add(tf_user, null);
            jPanel.add(tf_bname, null);
            jPanel.add(tf_btime, null);
            jPanel.add(tf_etime, null);
            jPanel.add(getJB_search(), null);
            jPanel.add(getJB_reset(), null);
            jPanel.add(getJB_update(), null);
            
            jcb_state = new JComboBox<String>(new String[] {"","true","false"});
            jcb_state.setBounds(550, 46, 100, 27);
            jPanel.add(jcb_state);
            
            jsp1 = new JScrollPane();
            jsp1.setBounds(30,88,720,200);
            jsp2 = new JScrollPane();
            jsp2.setBounds(30,295,720,200);

    		list=executeOrder(1,"select ono,amount,bmoney,texpenses,paymentstate,ctime,uname,addr "
    				+ "from (orderinfo left join userinfo on orderinfo.uno=userinfo.uno) left join ainfo on orderinfo.ano=ainfo.ano");
    		jsp1.setViewportView(table1);
    		jPanel.add(jsp1);
    		jsp2.setViewportView(table2);
    		jPanel.add(jsp2);
            
    	}
        return jPanel;
    }
    
    private JTextField getTf(int x,int y) {
    	JTextField jtf= new JTextField();
        jtf.setBounds(new Rectangle(x, y, 150, 28));
        return jtf;
    }
    
    private String get_sql() {
    	String sql="select distinct orderinfo.ono,amount,bmoney,texpenses,paymentstate,ctime,uname,addr \r\n"
    			+ "from (orderinfo left join userinfo on orderinfo.uno=userinfo.uno) left join ainfo on orderinfo.ano=ainfo.ano,orderdetail,binfo \r\n"
    			+ " where orderdetail.ono=orderinfo.ono and orderdetail.bno=binfo.bno "
        		+ " and orderinfo.ono like '%"+tf_ono.getText()+"%' and uname like '%"+tf_user.getText()+"%' \r\n"
        		+" and bname like '%"+tf_bname.getText()+"%' ";
    	
    	if(!tf_btime.getText().equals("") && !tf_etime.getText().equals("")) {
    		sql+=" and ctime between '"+tf_btime.getText()+"' and '"+tf_etime.getText()+"' ";
    	}
    	 
    	if(!jcb_state.getSelectedItem().toString().equals("")) {
    		sql+=" and paymentstate='"+jcb_state.getSelectedItem().toString()+"' ";
    	}
        return sql;
    }
    
    private JButton getJB_search() {
        if (jb_search == null) {
        	jb_search = new JButton("搜索");
        	jb_search.setBounds(new Rectangle(680, 45, 60, 30));
        	jb_search.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_search.setMargin(new Insets(0, 0, 0, 0));
        	jb_search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	list=executeOrder(1,get_sql());
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
        	jb_reset.setBounds(new Rectangle(100, 503, 65, 30));
        	jb_reset.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_reset.setMargin(new Insets(0, 0, 0, 0));
        	jb_reset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	list=executeOrder(1,"select ono,amount,bmoney,texpenses,paymentstate,ctime,uname,addr "
            				+ "from (orderinfo left join userinfo on orderinfo.uno=userinfo.uno) left join ainfo on orderinfo.ano=ainfo.ano");
                	jsp1.setViewportView(table1);
            		jPanel.add(jsp1);
                }
            });
        }
        return jb_reset;
    }
    
    
    private JButton getJB_update() {
        if (jb_update == null) {
        	jb_update = new JButton("修改");
        	jb_update.setBounds(new Rectangle(600, 503, 65, 30));
        	jb_update.setIcon(new ImageIcon(getClass().getResource("")));
        	jb_update.setMargin(new Insets(0, 0, 0, 0));
        	jb_update.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                }
            });
        }
        return jb_update;
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
    				list=executeOrder(2,"select dno,bamount,bprice,orderdetail.bno,bname from binfo,orderdetail where binfo.bno=orderdetail.bno and ono=?");
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
                	Order order= new Order();
                	order.setOno(rs.getInt(1));
                	order.setAmount(rs.getInt(2));
                	order.setBmoney(rs.getDouble(3));
                	order.setExpenses(rs.getDouble(4));
                	order.setState(rs.getBoolean(5));
                	order.setTime(rs.getDate(6).toString()+"  "+rs.getTime(6).toString());
                	order.setUname(rs.getNString(7));
                	order.setAddr(rs.getNString(8));
                    list.add(order);
                }
                for(int i=0;i<list.size();i++) {
                	Order order=list.get(i);
            		model1.addRow(new Object[] {order.getOno(),order.getAmount(),order.getBmoney(),order.getExpenses(),order.getState(),order.getTime(),order.getUname(),order.getAddr()});
            	}
        	}else {
        		ps.setString(1, String.valueOf(ono)) ;
                ResultSet rs = ps.executeQuery(); 
                while (rs.next()) { // 循环遍历查询结果集
                	Order order= new Order();
                	order.setOno(rs.getInt(1));
                	order.setAmount(rs.getInt(2));
                	order.setBmoney(rs.getDouble(3));
                	order.setAddr(rs.getString(4));
                	order.setUname(rs.getString(5));
                    list.add(order);
                }
                for(int i=0;i<list.size();i++) {
                	Order order=list.get(i);
        			model2.addRow(new Object[] {order.getOno(),order.getAmount(),order.getBmoney(),order.getAddr(),order.getUname()});
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
    
    public OCMFrame() {
        super();
        setSize(800, 580);
        setContentPane(getJContentPane());
        setTitle("订单管理");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);
        
    }
    
    private JLabel getJLabel(int x,int y,String t) {
    	Font font=new Font("",Font.BOLD,15);
    	JLabel jlabel = new JLabel(t);
        jlabel.setBounds(new Rectangle(x, y, 400, 30));
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
