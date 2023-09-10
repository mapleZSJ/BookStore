package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.JOptionPane;

import com.bean.Book;
import com.bean.Order;

public class OrderDao {
	/**
     * 添加购物车的方法
     * 
     * @param book
     */
    public static void insertCart(Book book) {
        Connection conn = null;
        try {
            String bno=book.getBno();
            double price=book.getPrice();
            String kno=book.getKno();
            String kind=book.getKind();
           
            conn = DAO.getConn();
            PreparedStatement ps=null;
            ps = conn.prepareStatement("select uno from userinfo where uname=? and pwd=?");
            ps.setString(1, kno.trim());
            ps.setString(2, kind.trim());
            ResultSet rs = ps.executeQuery();
            String no=null;
            while (rs.next()) { // 循环遍历查询结果集
            	no=rs.getString(1);
            }
            ps = conn.prepareStatement("insert into shoppingcart values(?,1,?,?)");
            ps.setDouble(1, price);
            ps.setString(2, no);
            ps.setString(3, bno.trim());
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
    
    public static void insertOrder(Order order,int state) {
        Connection conn = null;
        try {
            String bno=order.getBno();
            int amount=order.getAmount();
            double all=order.getBmoney();
            double expense=order.getExpenses();
            double price=order.getPrice();
            int uno=order.getUno();
            int ano=order.getAno();
            conn = DAO.getConn();
            PreparedStatement ps=null;
            ps = conn.prepareStatement("insert into orderinfo(amount,bmoney,texpenses,paymentstate,uno,ano) values(?,?,?,?,?,?)");
            ps.setInt(1, amount);
            ps.setDouble(2, all);
            ps.setDouble(3, expense);
            ps.setInt(4, state);
            ps.setInt(5, uno);
            ps.setInt(6, ano);
            int flag1 = ps.executeUpdate(); 
            if (flag1 > 0) {
            	int ono=Integer.parseInt(DAO.get_result("select top 1 ono from orderinfo where uno='"+uno+"' and ano='"+ano+"' order by ono desc"));
            	ps = conn.prepareStatement("insert into orderdetail(bamount,bprice,ono,bno) values(?,?,?,?)");
            	ps.setInt(1, amount);
                ps.setDouble(2, price);
                ps.setInt(3, ono);
                ps.setString(4, bno);
                int flag = ps.executeUpdate(); 
                if (flag > 0) {
                    if(state==1) {
                        JOptionPane.showMessageDialog(null, "购买成功。");
                    	ps = conn.prepareStatement("update binfo set stock=stock-? where bno=?");
                    	ps.setInt(1, amount);
                    	ps.setString(2, bno);
                    	ps.executeUpdate(); 
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "明细添加失败。");
                }
            } else {
                JOptionPane.showMessageDialog(null, "订单添加失败。");
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
}
