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
public class Order {
    private ArrayList<Product> orderList = new ArrayList();
    private int tableNum;
    private int status;
    
    public Order(){
        
    }
    
    public Order(ArrayList<Product> orders,int tableNum, int status) {
        this.orderList = orders;
        this.tableNum = tableNum;
        this.status = status;
    }    

    public Order(int tableNum, int status) {
        this.tableNum = tableNum;
        this.status = status;
    }
    

    public ArrayList<Product> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Product> orderList) {
        this.orderList = orderList;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }  
           
}
