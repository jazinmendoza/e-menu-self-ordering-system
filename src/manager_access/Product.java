/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager_access;

import java.sql.Blob;

/**
 *
 * @author Jazin Mendoza
 */
public class Product {
    private String productID;
    private String productName;
    private String productLine;
    private int qtyInStock;
    private Double price;
    private Blob image;

    public Product() {
    }

    public Product(String productID, String productName, String productLine, int qtyInStock, Double price, Blob image) {
        this.productID = productID;
        this.productName = productName;
        this.productLine = productLine;
        this.qtyInStock = qtyInStock;
        this.price = price;
        this.image = image;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public int getQtyInStock() {
        return qtyInStock;
    }

    public void setQtyInStock(int qtyInStock) {
        this.qtyInStock = qtyInStock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
    
}
