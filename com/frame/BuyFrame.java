package com.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.bean.Book;
import com.bean.Order;
import com.dao.DAO;
import com.dao.OrderDao;
import com.manage.BookFrame;
import com.panel.BackgroundPanel;
import com.tool.SaveUserStateTool;

public class BuyFrame  extends JFrame{
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
    private JTextField tf_bno = null;
    private JTextField tf_bname = null;
    private JTextField tf_amount = null;
    private JTextField tf_price = null;
    private JTextField tf_expense = null;
    private JTextField tf_receiver = null;
    private JComboBox<String> jcb_address;
    private JTextField tf_all = null;
    private JButton jb_buy = null;
    private JButton jb_exit = null;
    private int stock = 0;
    private int uno = 0;
    private int ano = 0;
    private String all=null;
    
    private String user=SaveUserStateTool.getUsername();
    private String pwd=SaveUserStateTool.getPassword();
    
    public BuyFrame(Book book) {
        super();
        setSize(800, 550);
        setContentPane(getJContentPane());
        setTitle("购买");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        tf_bno.setText(book.getBno());
        tf_bname.setText(book.getBname());
        tf_price.setText(String.valueOf(book.getPrice()));
        stock=book.getStock();
    	tf_amount.setText("1");
        tf_expense.setText("10.0");
        tf_all.setText(""+(book.getPrice()*Integer.parseInt(tf_amount.getText().toString())+Double.parseDouble(tf_expense.getText().toString())));
        tf_amount.getDocument().addDocumentListener (new DocumentListener() {
        	@Override
        	public void insertUpdate(DocumentEvent e) {
        		all=(""+(book.getPrice()*Integer.parseInt(tf_amount.getText().toString())+Double.parseDouble(tf_expense.getText().toString())));
        		tf_all.setText(all);
        	}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				
				
			}
        }); 
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);
    }
    
    
    private BackgroundPanel getJPanel() {
        if (jPanel == null) {
        	initialize();
            url = BookFrame.class.getResource(""); // 获得图片的URL
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
            jPanel.add(jl8);
            jPanel.add(tf_bno, null);
            jPanel.add(tf_bname, null);
            jPanel.add(tf_amount, null);
            jPanel.add(tf_price, null);
            jPanel.add(tf_expense, null);
            jPanel.add(tf_receiver, null);
            jPanel.add(tf_all, null);
            jPanel.add(getJB_buy(), null);
            jPanel.add(getJB_exit(), null);
            
            
            uno=Integer.parseInt(DAO.get_result("select uno from userinfo where uname='"+user+"' and pwd='"+pwd+"'"));
            jcb_address = DAO.get_CBox("select addr from ainfo where uno=(select uno from userinfo where uname='"+user+"' and pwd='"+pwd+"')");
            jcb_address.setBounds(350, 330, 160, 30);
            jcb_address.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		String r1 =DAO.get_result("select name from ainfo right join userinfo on ainfo.uno=userinfo.uno  "
            				+ " where addr='"+jcb_address.getSelectedItem().toString()+"' and userinfo.uno='"+String.valueOf(uno)+"'");
                    tf_receiver.setText(r1);
                    String r2 =DAO.get_result("select ano from ainfo right join userinfo on ainfo.uno=userinfo.uno  "
            				+ " where addr='"+jcb_address.getSelectedItem().toString()+"' and userinfo.uno='"+String.valueOf(uno)+"'");
                    ano=Integer.parseInt(r2);
            	}
            });
            jPanel.add(jcb_address);
            
        }
        return jPanel;
    }
    
   
    private JTextField getTf(int y) {
    	JTextField jtf=new JTextField();
        jtf.setBounds(new Rectangle(350, y, 160, 30));
        return jtf;
    }
    
    
    
    private JButton getJB_buy() {
        if (jb_buy == null) {
        	jb_buy = new JButton("购买");
        	jb_buy.setBounds(new Rectangle(550, 430, 80, 30));
        	jb_buy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String bno = tf_bno.getText().trim(); 
                    int amount=Integer.parseInt(tf_amount.getText().trim());
                    double price=Double.parseDouble(tf_price.getText().trim());
                    double expense=Double.parseDouble(tf_expense.getText().trim());
                    String address=jcb_address.getSelectedItem().toString();
                    double all=Double.parseDouble(tf_all.getText().trim());
                    Order order = new Order(); 
                    order.setBno(bno); 
                    order.setAmount(amount);
                    order.setBmoney(all);
                    order.setExpenses(expense);
                    order.setPrice(price);
                    order.setUno(uno);
                    order.setAno(ano);
                    if(stock-amount<0) {
                    	JOptionPane.showMessageDialog(null, "库存不足!");
                    }else {
                    	OrderDao.insertOrder(order,1);
                    }
                }
            });
            
        }
        return jb_buy;
    }
    
    private JButton getJB_exit() {
        if (jb_exit == null) {
        	jb_exit = new JButton("返回");
        	jb_exit.setBounds(new Rectangle(135, 430, 80, 30));
        	jb_exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	String bno = tf_bno.getText().trim(); 
                    int amount=Integer.parseInt(tf_amount.getText().trim());
                    double price=Double.parseDouble(tf_price.getText().trim());
                    double expense=Double.parseDouble(tf_expense.getText().trim());
                    String address=jcb_address.getSelectedItem().toString();
                    double all=Double.parseDouble(tf_all.getText().trim());
                    Order order = new Order(); 
                    order.setBno(bno); 
                    order.setAmount(amount);
                    order.setBmoney(all);
                    order.setExpenses(expense);
                    order.setPrice(price);
                    order.setUno(uno);
                    order.setAno(ano);
                    if(stock-amount<0) {
                    	JOptionPane.showMessageDialog(null, "库存不足!");
                    	dispose();
                    }else {
                    	OrderDao.insertOrder(order,0);
                    	dispose();
                    }
                }
            });
        }
        return jb_exit;
    }
    
    private void initialize() {
    	jl1 = getJLabel(33,"书    号：");
    	jl2 = getJLabel(83,"书    名：");
        jl3 = getJLabel(133,"数    量：");
        jl4 = getJLabel(183,"价    格：");
        jl5 = getJLabel(233,"运    费：");
        jl6 = getJLabel(283,"收 件 人：");
        jl7 = getJLabel(333,"地    址：");
        jl8 = getJLabel(383,"总 金 额：");
        tf_bno=getTf(30);
        tf_bname=getTf(80);
        tf_amount=getTf(130);
        tf_price=getTf(180);
        tf_expense=getTf(230);
        tf_receiver=getTf(280);
        tf_all=getTf(380);
    }
    
    private JLabel getJLabel(int y,String t) {
    	Font font=new Font("楷体",Font.BOLD,20);
    	JLabel jlabel = new JLabel(t);
        jlabel.setBounds(new Rectangle(250, y, 200, 20));
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
