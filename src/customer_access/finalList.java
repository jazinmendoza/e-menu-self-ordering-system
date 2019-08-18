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
public class finalList {
    private ArrayList<finalOrder> finalList = new ArrayList();
    
    
    public finalList() {
    }    

    public ArrayList<finalOrder> getFinalList() {
        return finalList;
    }

    public void setFinalList(ArrayList<finalOrder> finalList) {
        this.finalList = finalList;
    }
    
    public void addOrder(ArrayList<finalOrder> finalList){
        setFinalList(finalList);
    }
     
}
