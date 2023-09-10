package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import com.bean.Book;

public class BookDAO {
	/**
     * 添加图书信息的方法
     * 
     * @param book
     */
    public static void insertBook(Book book) {
        Connection conn = null;
        try {
            String bno=book.getBno();
            String bname=book.getBname();
            String author=book.getAuthor();
            String intro=book.getIntro();
            double price=book.getPrice();
            int stock=book.getStock();
            boolean state=book.getBstate();
            String kno=book.getKno();
            String kind=book.getKind();
            if (bno == null || bno.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "书号不能为空。");
                return;
            }
            if (bname == null || bname.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "书名不能为空。");
                return;
            }
            if (author == null || author.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "作者不能为空。");
                return;
            }
            if ((kno == null || kno.trim().equals("")) && !(kind == null || kind.trim().equals(""))) {
                JOptionPane.showMessageDialog(null, "种类号不能为空。");
                return;
            }
            conn = DAO.getConn();
            PreparedStatement ps=null;
            if(!(kno == null || kno.trim().equals(""))) {
                ps = conn.prepareStatement("select kno from bkind where kno=?");
                ps.setString(1, kno.trim());
                ResultSet rs = ps.executeQuery();
                String r=null;
                while (rs.next()) { // 循环遍历查询结果集
                	r=rs.getString(1);
                }
                if(r == null || r.trim().equals("")) {
                    ps = conn.prepareStatement("insert into bkind values(?,?)");
                    ps.setString(1, kno.trim());
                    ps.setString(2, kind.trim());
                    int flag = ps.executeUpdate(); 
                    if (flag > 0) {
                        JOptionPane.showMessageDialog(null, "种类添加成功。");
                    } else {
                        JOptionPane.showMessageDialog(null, "种类添加失败。");
                    }
                }
            }
            ps = conn.prepareStatement("insert into binfo values(?,?,?,?,?,?,?,?)");
            ps.setString(1, bno.trim()); 
            ps.setString(2, bname.trim());
            ps.setString(3, author.trim());
            ps.setString(4, intro.trim());
            ps.setDouble(5, price);
            ps.setInt(6, stock);
            ps.setBoolean(7, state);
            ps.setString(8, kno.trim());
            int flag = ps.executeUpdate(); 
            if (flag > 0) {
                JOptionPane.showMessageDialog(null, "添加成功。");
            } else {
                JOptionPane.showMessageDialog(null, "添加失败。");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "添加失败，请注意填写！");
            return;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                
            }
        }
    }
    
    /**
     * 修改图书信息的方法
     * 
     * @param book
     */
    public static void updateBook(Book book) {
        Connection conn = null;
        try {
            String bno=book.getBno();
            String bname=book.getBname();
            String author=book.getAuthor();
            String intro=book.getIntro();
            double price=book.getPrice();
            int stock=book.getStock();
            boolean state=book.getBstate();
            String kno=book.getKno();
            String kind=book.getKind();
           
            conn = DAO.getConn();
            PreparedStatement ps=null;
            if(!(kno == null || kno.trim().equals(""))) {
                ps = conn.prepareStatement("select kno from bkind where kno=?");
                ps.setString(1, kno.trim());
                ResultSet rs = ps.executeQuery();
                String r=null;
                while (rs.next()) { // 循环遍历查询结果集
                	r=rs.getString(1);
                }
                if(r == null || r.trim().equals("")) {
                    ps = conn.prepareStatement("insert into bkind values(?,?)");
                    ps.setString(1, kno.trim());
                    ps.setString(2, kind.trim());
                    int flag = ps.executeUpdate(); 
                    if (flag > 0) {
                        //JOptionPane.showMessageDialog(null, "种类添加成功。");
                    } else {
                        JOptionPane.showMessageDialog(null, "种类添加失败。");
                    }
                }
            }
            ps = conn.prepareStatement("update binfo set bno=?,bname=?,author=?,introduction=?,price=?,stock=?,bookstate=?,kno=?  where bno =?");
            ps.setString(1, bno.trim()); 
            ps.setString(2, bname.trim());
            ps.setString(3, author.trim());
            ps.setString(4, intro.trim());
            ps.setDouble(5, price);
            ps.setInt(6, stock);
            ps.setBoolean(7, state);
            ps.setString(8, kno.trim());
            ps.setString(9, bno.trim());
            int flag = ps.executeUpdate(); 
            if (flag > 0) {
                JOptionPane.showMessageDialog(null, "修改成功。");
            } else {
                JOptionPane.showMessageDialog(null, "修改失败。");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "修改失败，请注意填写！");
            return;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                
            }
        }
    }
    
    /**
     * 删除图书信息的方法
     * 
     * @param book
     */
    public static void deleteBook(Book book) {
        Connection conn = null;
        try {
            String bno=book.getBno();
            conn = DAO.getConn();
            PreparedStatement ps = conn.prepareStatement("delete from binfo where bno=?");
            ps.setString(1, bno.trim()); 
            int flag = ps.executeUpdate(); 
            if (flag > 0) {
                JOptionPane.showMessageDialog(null, "删除成功。");
            } else {
                JOptionPane.showMessageDialog(null, "删除失败。");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "删除失败！");
            return;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                
            }
        }
    }
}


