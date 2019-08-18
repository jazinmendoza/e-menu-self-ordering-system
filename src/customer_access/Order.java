/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer_access;

/**
 *
 * @author student
 */
public class Order {
    private String prodID;
    private String prodName;
    private int quantity;
    private int qtyInStock;
    private float price;

    public Order() {
    }

    
    public Order(String prodID, String prodName ,int quantity, int qtyInStock ,float price) {
        this.prodID = prodID;
        this.quantity = quantity;
        this.prodName = prodName;
        this.price = price;
        this.qtyInStock = qtyInStock;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    
    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
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

    public int getQtyInStock() {
        return qtyInStock;
    }

    public void setQtyInStock(int qtyInStock) {
        this.qtyInStock = qtyInStock;
    }
    
    
    
}
