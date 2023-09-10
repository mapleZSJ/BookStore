package com.bean;

public class Book {
	private String bno = null;
	private String bname = null;
    private String author = null;
    private String introduction = null;
    private double price = 0.00;
    private int stock = 0;
    private boolean bstate = false;
    
    private String kno = null;
    private String kind = null;
    
    
    public String getBno() {
        return bno;
    }
    
    public void setBno(String bno) {
        this.bno = bno;
    }
    
    public String getBname() {
        return bname;
    }
    
    public void setBname(String bname) {
        this.bname = bname;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getIntro() {
        return introduction;
    }
    
    public void setIntro(String introduction) {
        this.introduction = introduction;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getStock() {
        return stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public boolean getBstate() {
        return bstate;
    }
    
    public void setBstate(boolean bstate) {
        this.bstate = bstate;
    }
    
    public String getKno() {
        return kno;
    }
    
    public void setKno(String kno) {
        this.kno = kno;
    }
    
    public String getKind() {
        return kind;
    }
    
    public void setKind(String kind) {
        this.kind = kind;
    }
    
    
}
