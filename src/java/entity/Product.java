/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Thanh Dang
 */
public class Product {
    private int id;
    private String name;
    private String image;
    private double price;

    public Product() {
    }
    
    public Product(int id, String name, String image, double price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getFormattedPrice() {
        Locale lc = new Locale("vn", "VN");
        
        NumberFormat formatter = NumberFormat.getInstance(lc);
        String result = formatter.format(price) + " VNĐ";
        return result.replaceAll(",", ".");   
    }
    
    
}
