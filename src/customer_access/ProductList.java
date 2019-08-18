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
public class ProductList {
    private ArrayList<Product> dessertList = new ArrayList();
    private ArrayList<Product> friedDimsumList = new ArrayList();
    private ArrayList<Product> ricePotsList = new ArrayList();
    private ArrayList<Product> saladList = new ArrayList();
    private ArrayList<Product> shaopaoList = new ArrayList();
    private ArrayList<Product> soupsList = new ArrayList();
    private ArrayList<Product> steamedDimsumList = new ArrayList();
    private ArrayList<Product> drinksList = new ArrayList();

    public ProductList(){
       Query con = new Query();
       
       this.dessertList = con.addProductList("Desserts");
       this.friedDimsumList = con.addProductList("Fried Dimsum");
       this.ricePotsList = con.addProductList("Rice Pots");
       this.saladList = con.addProductList("Salads and Vegetables");
       this.shaopaoList = con.addProductList("Shaopao");
       this.soupsList = con.addProductList("Soups and Noodles");
       this.steamedDimsumList = con.addProductList("Steamed Dimsum");
       this.drinksList = con.addProductList("Drinks");
    }
    
    public ArrayList<Product> getDessertList() {
        return dessertList;
    }

    public void setDessertList(ArrayList<Product> dessertList) {
        this.dessertList = dessertList;
    }

    public ArrayList<Product> getFriedDimsumList() {
        return friedDimsumList;
    }

    public void setFriedDimsumList(ArrayList<Product> friedDimsumList) {
        this.friedDimsumList = friedDimsumList;
    }

    public ArrayList<Product> getRicePotsList() {
        return ricePotsList;
    }

    public void setRicePotsList(ArrayList<Product> ricePotsList) {
        this.ricePotsList = ricePotsList;
    }

    public ArrayList<Product> getSaladList() {
        return saladList;
    }

    public void setSaladList(ArrayList<Product> saladList) {
        this.saladList = saladList;
    }

    public ArrayList<Product> getShaopaoList() {
        return shaopaoList;
    }

    public void setShaopaoList(ArrayList<Product> shaopaoList) {
        this.shaopaoList = shaopaoList;
    }

    public ArrayList<Product> getSoupsList() {
        return soupsList;
    }

    public void setSoupsList(ArrayList<Product> soupsList) {
        this.soupsList = soupsList;
    }

    public ArrayList<Product> getSteamedDimsumList() {
        return steamedDimsumList;
    }

    public void setSteamedDimsumList(ArrayList<Product> steamedDimsumList) {
        this.steamedDimsumList = steamedDimsumList;
    }

    public ArrayList<Product> getDrinksList() {
        return drinksList;
    }

    public void setDrinksList(ArrayList<Product> drinksList) {
        this.drinksList = drinksList;
    }
}
