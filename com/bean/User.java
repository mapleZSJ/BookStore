package com.bean;

public class User {
	private int uno = 0;
	private String name = null;
    private String pwd = null;
    private String okPwd = null;
    private String sex = null;
    private String card = null;
    private String pay = null;
    private boolean authority = false;
    private String birth = null;
    
    private int ano = 0;
    private String receiver = null;
    private String address = null;
    private String postcode = null;
    private String phone = null;
    private boolean state = false;
    
    
    public int getUno() {
        return uno;
    }
    
    public void setUno(int uno) {
        this.uno = uno;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public void setOkPwd(String okPwd) {
        this.okPwd = okPwd;
    }
    
    public String getOkPwd() {
        return okPwd;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setCard(String card) {
        this.card = card;
    }
    
    public String getCard() {
        return card;
    }
    
    public void setPay(String pay) {
        this.pay = pay;
    }
    
    public String getPay() {
        return pay;
    }
    
    public void setAuthority(boolean authority) {
        this.authority = authority;
    }
    
    public boolean getAuthority() {
        return authority;
    }   
    
    public void setBirth(String birth) {
        this.birth = birth;
    }
    
    public String getBirth() {
        return birth;
    }
    
    
    //��ַ
    public void setAno(int ano) {
        this.ano = ano;
    }
    
    public int getAno() {
        return ano;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    
    public String getReceiver() {
        return receiver;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    
    public String getPostcode() {
        return postcode;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setState(boolean state) {
        this.state = state;
    }
    
    public boolean getState() {
        return state;
    }
}
