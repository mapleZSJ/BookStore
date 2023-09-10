package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.bean.Book;

public class DAO {
	
	private static DAO dao = new DAO(); 
    
    public DAO() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // 加载数据库驱动
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "数据库驱动加载失败！\n" + e.getMessage());
        }
    }
    
    public static Connection getConn() {
        try {
            Connection conn = null; 
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Onlinebooksales"; // 数据库的URL
            String username = "sa"; 
            String password = ""; 
            conn = DriverManager.getConnection(url, username, password); 
            return conn; 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数据库连接失败。\n请检查数据库用户名和密码是否正确！\n" + e.getMessage());
            return null;
        }
    }
    
    public static JComboBox<String> get_CBox(String sql) {
		JComboBox<String>  cbb=new JComboBox<String> ();
		cbb.addItem("");
        Connection conn = null;
        try {
            conn = DAO.getConn(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); // 获得查询结果集
            while (rs.next()) { // 查询到用户信息
                cbb.addItem(rs.getString(1));
            } 
            return cbb;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "数据库异常！\n" + ex.getMessage());
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // 关闭数据库连接对象
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static String get_result(String sql) {
    	String r = null;
        Connection conn = null;
        try {
            conn = DAO.getConn(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); // 获得查询结果集
            while (rs.next()) { // 查询到用户信息
            	r=rs.getString(1);
            } 
            return r;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "数据库异常！\n" + ex.getMessage());
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // 关闭数据库连接对象
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static int get_flag(String sql) {
        Connection conn = null;
        try {
            conn = DAO.getConn(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            int flag = ps.executeUpdate(); 
            return flag;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "数据库异常！\n" + ex.getMessage());
            return 0;
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // 关闭数据库连接对象
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
