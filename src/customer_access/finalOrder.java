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
public class finalOrder {
    private String prodID;
    private int quantity;

    public finalOrder() {
    }
    
    public finalOrder(String prodID, int quantity) {
        this.prodID = prodID;
        this.quantity = quantity;
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
}
