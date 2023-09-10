package com.bean;

import java.sql.Date;

public class Order {
    private int ono = 0;
    private int amount = 0;
    private double bmoney = 0.00;
    private double expenses = 0.00;
    private boolean state = false;
    private String time = null;
    
    private String uname = null;
    private String addr = null;
    private String bno = null;
    private double price = 0.00;
    private int uno = 0;
    private int ano = 0;
    
    public int getOno() {
        return ono;
    }
    
    public void setOno(int ono) {
        this.ono = ono;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public double getBmoney() {
        return bmoney;
    }
    
    public void setBmoney(double bmoney) {
        this.bmoney = bmoney;
    }
    
    public double getExpenses() {
        return expenses;
    }
    
    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }
    
    public boolean getState() {
        return state;
    }
    
    public void setState(boolean state) {
        this.state = state;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    
    public String getUname() {
        return uname;
    }
    
    public void setUname(String uname) {
        this.uname = uname;
    }
    
    public String getAddr() {
        return addr;
    }
    
    public void setAddr(String addr) {
        this.addr = addr;
    }
    
    public String getBno() {
        return bno;
    }
    
    public void setBno(String bno) {
        this.bno = bno;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getUno() {
        return uno;
    }
    
    public void setUno(int uno) {
        this.uno = uno;
    }
    
    public int getAno() {
        return ano;
    }
    
    public void setAno(int ano) {
        this.ano = ano;
    }
}
