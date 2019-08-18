/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kitchen_access;

import java.util.ArrayList;

/**
 *
 * @author danpiamonte
 */
public class Product {
    private String prodName;
    private int quantity;

    public Product() {
    }

    public Product(String prodName, int quantity) {
        this.prodName = prodName;
        this.quantity = quantity;
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
    
   
}
