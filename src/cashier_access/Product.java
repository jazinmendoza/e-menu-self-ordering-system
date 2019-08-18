/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashier_access;

import java.util.ArrayList;

/**
 *
 * @author danpiamonte
 */
public class Product {
    private String prodName;
    private int quantity;
    private float price;
    private float subtotal;

    public Product() {
    }

    public Product(String prodName, int quantity, float price, float subtotal) {
        this.prodName = prodName;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    
   
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }
    
    
    
   
}
