package com.manage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.*;

import com.bean.Book;
import com.dao.BookDAO;
import com.panel.BackgroundPanel;

public class BookFrame extends JFrame{
	
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
    private JLabel jl9 = null;
    private JTextField tf_bno = null;
    private JTextField tf_bname = null;
    private JTextField tf_author = null;
    private JTextField tf_intro=null;
    private JTextField tf_price = null;
    private JTextField tf_stock = null;
    private JComboBox<String> jcb_state;
    private JTextField tf_kno = null;
    private JTextField tf_kind = null;
    private JButton jb_add = null;
    private JButton jb_exit = null;
    
    
    public BookFrame() {
        super();
        setSize(800, 550);
        setContentPane(getJContentPane(0));
        setTitle("添加");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);
    }
    
    public BookFrame(Book book) {
        super();
        setSize(800, 550);
        setContentPane(getJContentPane(2));
        setTitle("修改");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        tf_bno.setText(book.getBno());
        tf_bname.setText(book.getBname());
        tf_author.setText(book.getAuthor());
        tf_intro.setText(book.getIntro());
        tf_price.setText(String.valueOf(book.getPrice()));
        tf_stock.setText(String.valueOf(book.getStock()));
        jcb_state.setSelectedItem(book.getBstate());
        tf_kno.setText(book.getKno());
        tf_kind.setText(book.getKind());
        Toolkit toolkit = this.getToolkit();
        Dimension dm = toolkit.getScreenSize();
        setLocation((dm.width - this.getWidth()) / 2, (dm.height - this.getHeight()) / 2);
        setVisible(true);
    }
    
    
    private BackgroundPanel getJPanel(int id) {
        if (jPanel == null) {
        	initialize();
            url = BookFrame.class.getResource("/image/注册.jpg"); // 获得图片的URL
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
            jPanel.add(jl9);
            jPanel.add(tf_bno, null);
            jPanel.add(tf_bname, null);
            jPanel.add(tf_author, null);
            jPanel.add(tf_price, null);
            jPanel.add(tf_stock, null);
            jPanel.add(tf_kno, null);
            jPanel.add(tf_kind, null);
            jPanel.add(getTf_intro(), null);
            jPanel.add(getJB_add(id), null);
            jPanel.add(getJB_exit(), null);
            
            jcb_state = new JComboBox<String>(new String[] {"false","true"});
            jcb_state.setBounds(350, 330, 160, 30);
            jPanel.add(jcb_state);
            
        }
        return jPanel;
    }
    
   
    private JTextField getTf(int y) {
    	JTextField jtf=new JTextField();
        jtf.setBounds(new Rectangle(350, y, 160, 30));
        return jtf;
    }
    
    private JTextField getTf_intro() {
        if (tf_intro == null) {
            tf_intro = new JTextField();
            tf_intro.setBounds(new Rectangle(350, 180, 160, 30));
            tf_intro.addKeyListener(new KeyAdapter() {
            	@Override
            	public void keyTyped(KeyEvent e) {
            		if(tf_intro.getText().length()>=80) {
            			e.consume();
            		}
            	}
            });
        }
        return tf_intro;
    }
    
    
    private JButton getJB_add(int id) {
        if (jb_add == null) {
        	jb_add = new JButton("添加");
        	if(id==2) {
        		jb_add.setText("修改");
        	}
        	jb_add.setBounds(new Rectangle(550, 430, 80, 30));
        	jb_add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String bno = tf_bno.getText().trim(); 
                    String bname=tf_bname.getText().trim();
                    String author=tf_author.getText().trim();
                    String intro=tf_intro.getText().trim();
                    double price=Double.parseDouble(tf_price.getText().trim());
                    int stock=Integer.parseInt(tf_stock.getText().trim());
                    boolean state=Boolean.parseBoolean(jcb_state.getSelectedItem().toString());
                    String kno=tf_kno.getText().trim();
                    String kind=tf_kind.getText().trim();
                    Book book = new Book(); 
                    book.setBno(bno); 
                    book.setBname(bname);
                    book.setAuthor(author);
                    book.setIntro(intro);
                    book.setPrice(price);
                    book.setStock(stock);
                    book.setBstate(state);
                    book.setKno(kno);
                    book.setKind(kind);
                    if(id==2) {
                    	BookDAO.updateBook(book);
                    }else {
                    	BookDAO.insertBook(book);
                    }
                }
            });
            
        }
        return jb_add;
    }
    
    private JButton getJB_exit() {
        if (jb_exit == null) {
        	jb_exit = new JButton("返回");
        	jb_exit.setBounds(new Rectangle(135, 430, 80, 30));
        	jb_exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return jb_exit;
    }
    
    private void initialize() {
    	jl1 = getJLabel(33,"书    号：");
    	jl2 = getJLabel(83,"书    名：");
        jl3 = getJLabel(133,"作    者：");
        jl4 = getJLabel(183,"简    介：");
        jl5 = getJLabel(233,"价    格：");
        jl6 = getJLabel(283,"库 存 量：");
        jl7 = getJLabel(333,"是否下架：");
        jl8 = getJLabel(383,"种 类 号：");
        jl9 = getJLabel(433,"种    类：");
        tf_bno=getTf(30);
        tf_bname=getTf(80);
        tf_author=getTf(130);
        tf_price=getTf(230);
        tf_stock=getTf(280);
        tf_kno=getTf(380);
        tf_kind=getTf(430);
    }
    
    private JLabel getJLabel(int y,String t) {
    	Font font=new Font("楷体",Font.BOLD,20);
    	JLabel jlabel = new JLabel(t);
        jlabel.setBounds(new Rectangle(250, y, 200, 20));
        jlabel.setFont(font);
        return jlabel;
    }
    
    private JPanel getJContentPane(int id) {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanel(id), BorderLayout.CENTER);
        }
        return jContentPane;
    }
}
