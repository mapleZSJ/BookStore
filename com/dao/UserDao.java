package com.dao;

import java.sql.*;
import javax.swing.*;

import com.bean.User;
import com.tool.SaveUserStateTool;

public class UserDao {
	
	public static boolean okUser(User user) {
        Connection conn = null;
        try {
            String uname = user.getName();
            String pwd = user.getPwd();
            conn = DAO.getConn(); 
            PreparedStatement ps = conn.prepareStatement("select pwd,authority from userinfo where uname=?");
            ps.setString(1, uname); // 为参数赋值
            ResultSet rs = ps.executeQuery(); // 获得查询结果集
            if (rs.next() && rs.getRow() > 0) { // 查询到用户信息
                String password = rs.getString(1).trim(); 
                boolean state = rs.getBoolean(2); 
                if (password.equals(pwd)) {
                    SaveUserStateTool.setUsername(uname);
                    SaveUserStateTool.setPassword(pwd);
                    SaveUserStateTool.setState(state);;
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "密码不正确。");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "用户名不存在。");
                return false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "数据库异常！\n" + ex.getMessage());
            return false;
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
    
    /**
     * 添加用户信息的方法
     * 
     * @param user
     */
    public static void insertUser(User user) {
        Connection conn = null;
        try {
            String username = user.getName();
            String pwd = user.getPwd();
            String okPwd = user.getOkPwd();
            String sex=user.getSex();
            String card= user.getCard();
            String pay=user.getPay();
            String birth=user.getBirth();
            if (username == null || username.trim().equals("") || pwd == null || pwd.trim().equals("") || okPwd == null || okPwd.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "用户名或密码不能为空。");
                return;
            }
            if (!pwd.trim().equals(okPwd.trim())) {
                JOptionPane.showMessageDialog(null, "两次输入的密码不一致。");
                return;
            }
            if (card == null || card.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "银行卡号不能为空。");
                return;
            }
            if (pay == null || pay.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "支付密码不能为空。");
                return;
            }
            if (birth == null || birth.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "出生日期不能为空。");
                return;
            }
            conn = DAO.getConn();
            PreparedStatement ps = conn.prepareStatement("insert into userinfo(uname,pwd,sex,cardnum,cpwd,birthday)  values(?,?,?,?,?,?)");
            ps.setString(1, username.trim()); 
            ps.setString(2, pwd.trim());
            ps.setString(3, sex);
            ps.setString(4, card.trim());
            ps.setString(5, pay.trim());
            ps.setString(6, birth.trim());
            int flag = ps.executeUpdate(); 
            if (flag > 0) {
                JOptionPane.showMessageDialog(null, "添加成功。");
            } else {
                JOptionPane.showMessageDialog(null, "添加失败。");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "用户名重复，请换个名称！");
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
     * 修改用户密码的方法
     * 
     * @param oldPwd  
     * @param newPwd  
     * @param okPwd   
     */
    public static void updateUser(String oldPwd, String newPwd, String okPwd,int i) {
        try {
        	int uno=Integer.parseInt(DAO.get_result("select uno from userinfo where uname='"+SaveUserStateTool.getUsername()+"' and pwd='"+SaveUserStateTool.getPassword()+"'"));
        	int cpwd=Integer.parseInt(DAO.get_result("select cpwd from userinfo where uno='"+uno+"' "));
            if (!newPwd.trim().equals(okPwd.trim())) {
                JOptionPane.showMessageDialog(null, "两次输入的密码不一致。");
                return;
            }
            if(i==0) {
            	if (!oldPwd.trim().equals(SaveUserStateTool.getPassword())) {
                    JOptionPane.showMessageDialog(null, "原密码不正确。");
                    return;
                }
            }else {
            	if (!oldPwd.trim().equals(cpwd)) {
                    JOptionPane.showMessageDialog(null, "原密码不正确。");
                    return;
                }
            }
            Connection conn = DAO.getConn();
            PreparedStatement ps=null;
            if(i==0) {
            	ps = conn.prepareStatement("update userinfo set pwd = ? where uname = ?");
                ps.setString(1, newPwd.trim());
                ps.setString(2, SaveUserStateTool.getUsername());
            }else {
            	ps = conn.prepareStatement("update userinfo set cpwd = ? where uno = ?");
                ps.setString(1, newPwd.trim());
                ps.setInt(2, uno);
            }
            int flag = ps.executeUpdate();
            if (flag > 0) {
                JOptionPane.showMessageDialog(null, "修改成功。");
            } else {
                JOptionPane.showMessageDialog(null, "修改失败。");
            }
            ps.close();
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "数据库异常！" + ex.getMessage());
            return;
        }
    }
}
