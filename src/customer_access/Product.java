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
public class Product {
    private String prodID;
    private String prodName;
    private String prodLine;
    private int qtyInStock;
    private Float price;
    private byte[] image;

    public Product() {
    }

    public Product(String prodID, String prodName, String prodLine, int qtyInStock,Float price, byte[] image) {
        this.prodID = prodID;
        this.prodName = prodName;
        this.prodLine = prodLine;
        this.price = price;
        this.image = image;
        this.qtyInStock = qtyInStock;
    }

    public void setQtyInStock(int qtyInStock) {
        this.qtyInStock = qtyInStock;
    }
        
    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdLine() {
        return prodLine;
    }

    public void setProdLine(String prodLine) {
        this.prodLine = prodLine;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public int getQtyInStock() {
        return qtyInStock;
    }
    
   
}
