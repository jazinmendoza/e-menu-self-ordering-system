/*
// * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kitchen_access;
import java.util.ArrayList;

/**
 *
 * @author danpiamonte
 */
public class Order {
    private ArrayList<Product> orderList = new ArrayList();
    private int tableNum;
    private int orderNum;
    private int status;
    public Order(){
        
    }
    
    public Order(ArrayList<Product> orders,int tableNum, int orderNum, int status) {
        this.orderList = orders;
        this.tableNum = tableNum;
        this.status = status;
        this.orderNum=orderNum;
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
    
    
    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
