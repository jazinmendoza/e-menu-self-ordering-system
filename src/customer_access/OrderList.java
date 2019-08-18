/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer_access;

import java.util.ArrayList;

/**
 *
 * @author student
 */
public class OrderList {
    private ArrayList<Order> orderList = new ArrayList();
    
    
    public OrderList() {
    }    

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }
    
    public void addOrder(ArrayList<Order> orders){
        setOrderList(orders);
    }
    public void removeOrder(String prodID, OrderList order){
        for(int i = 0; i < order.getOrderList().size() ; i++){
            if((order.getOrderList().get(i).getProdID()).equals(prodID)){
                order.getOrderList().remove(i);
                break;
            }
        }
    }
    public void updateOrder(Order order){
        
    }
     
}
