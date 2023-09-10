package com.tool;

import java.sql.*;

import com.dao.DAO;

public class SaveUserStateTool {
	private static String username = null;
    private static String password = null;
    private static boolean state = false;
    
    public static void setUsername(String username) {
        SaveUserStateTool.username = username;
    }
    
    public static String getUsername() {
        return username;
    }
    
    public static void setPassword(String password) {
        SaveUserStateTool.password = password;
    }
    
    public static String getPassword() {
        return password;
    }
    
    
    public static void setState(boolean state) {
        SaveUserStateTool.state = state;
    }
    
    public static boolean getState() {
    	return state;
    }
}
