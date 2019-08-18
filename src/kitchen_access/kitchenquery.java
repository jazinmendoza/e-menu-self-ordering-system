/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kitchen_access;

import java.sql.Connection;
//import customer_access.Order;
//import customer_access.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author user
 */
public class kitchenquery {

    ArrayList<Order> takeOrderList = new ArrayList<>(); //arraylist of orders
    Product y = new Product(); 
    Statement st;

    /*SELECT tableinfo.tableNum, tableinfo.pax, orderdetails.orderNum, orderdetails.quantity, orderdetails.status, item.productName 
        FROM tableinfo JOIN orderdetails on tableinfo.tableNum = orderdetails.tableNum 
        JOIN item on orderdetails.productId = item.productId
     */
    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/e-menu", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(kitchenquery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(kitchenquery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public ArrayList addOrderList() {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT tableinfo.tableNum, orderdetails.orderNum");
        sb.append("orderdetails.quantity,orderdetails.status,item.productName ");
        sb.append("FROM tableinfo ");
        sb.append("JOIN orderdetails on tableinfo.tableNum = orderdetails.tableNum ");
        sb.append("JOIN  item on orderdetails.productId = item.productId ");
//        System.out.println(sb.toString());
        PreparedStatement pstmt;

        try {
            Connection con = getConnection();
            ResultSet rs = null;
            ArrayList<Product> tempOrder = new ArrayList(); //temporary Order with list of products
            pstmt = con.prepareStatement(sb.toString());
            rs = myExecuteQuery(pstmt);
            boolean check = false;
            int rscount = 0;
            Order k;
            while (rs.next()) {
                do {
                    if (takeOrderList.isEmpty()) {
//                        tempOrder = new ArrayList();
                        Product temp = new Product(); 
                        temp.setProdName(rs.getString("productName"));
                        temp.setQuantity(rs.getInt("quantity"));

                        tempOrder.add(temp);
                        k = new Order(
                                tempOrder,
                                rs.getInt("tableNum"),
                                rs.getInt("orderNum"),
                                rs.getInt("status")
                        );
                        takeOrderList.add(k);
                      
                        break;
                    } else {
                        System.out.println("sud");
                        for (int i = 0; i < takeOrderList.size() && takeOrderList.get(rscount).getTableNum() == rs.getInt("tableNum"); i++);
                        if (takeOrderList.get(rscount).getTableNum() == rs.getInt("tableNum")) {
                            Product temp = new Product();//if same table num && list of order exist, it will only only add the products
                            temp.setProdName(rs.getString("productName"));
                            temp.setQuantity(rs.getInt("quantity"));
                            tempOrder.add(temp);
//                            k = new Kitchen(
//                                    rs.getInt("tableNum"),
//                                    rs.getInt("orderNum"),
//                                    rs.getInt("status"),
//                                    tempOrder
//                            );
//                            takeOrderList.add(k);
                            break;
                        } else {
                            Product temp = new Product(); //orders
                            temp.setProdName(rs.getString("productName"));
                            temp.setQuantity(rs.getInt("quantity"));

                            tempOrder.add(temp);
                            k = new Order(
                                    tempOrder,
                                    rs.getInt("tableNum"),
                                    rs.getInt("orderNum"),
                                    rs.getInt("status")
                            );
                            takeOrderList.add(k);
                            break;
                        }
                    }

                } while (rscount != takeOrderList.size());
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return takeOrderList;
    }

    public ArrayList<Order> getOrders() throws SQLException {
        ArrayList<Order> orderList = new ArrayList<Order>();
        ArrayList<Product> products = new ArrayList<Product>();
        int orderNum = 0;
        int status = 0;
        int tableNum = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("*");
        sb.append(" FROM ");
        sb.append("orders ");
        sb.append("WHERE ");
        sb.append("orderStatus = 0");
        sb.append(";");

        PreparedStatement pstmt;

        Connection con = getConnection();
        ResultSet rs = null;
        pstmt = con.prepareStatement(sb.toString());
        rs = myExecuteQuery(pstmt);
        while (rs.next()) {
            orderNum = rs.getInt("orderNum");
            status = rs.getInt("orderStatus");
            tableNum = rs.getInt("tableNum");
            products = getOrderdetails(orderNum);
            orderList.add(new Order(products, tableNum, orderNum, status));
        }

        rs.close();

        return orderList;
    }

    public void updateStatus(int orderNum) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append("orders ");
        sb.append("SET ");
        sb.append("orderStatus = 1 ");
        sb.append("WHERE ");
        sb.append("orderNum = ?");
        sb.append(";");

        PreparedStatement pstmt;

        Connection con = getConnection();

        ResultSet rs = null;
        pstmt = con.prepareStatement(sb.toString());
        pstmt.setString(1, Integer.toString(orderNum));
        
        if(myExecuteUpdate(pstmt)==1){
            System.out.println("UPDATED");
        }
    }
    
    public ArrayList<Product> getOrderdetails(int orderNum) throws SQLException {
        ArrayList<Product> products = new ArrayList<Product>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("*");
        sb.append(" FROM ");
        sb.append("orderdetails ");
        sb.append("JOIN item ON item.productId = orderdetails.productId ");
        sb.append("WHERE ");
        sb.append("orderNum = ?");
        sb.append(";");

        PreparedStatement pstmt;

        Connection con = getConnection();
        ResultSet rs = null;
        pstmt = con.prepareStatement(sb.toString());
        pstmt.setString(1, Integer.toString(orderNum));
        rs = myExecuteQuery(pstmt);
        while (rs.next()) {
            products.add(new Product(rs.getString("productName"), rs.getInt("quantity")));
        }

        rs.close();
        return products;
    }

    public ResultSet myExecuteQuery(PreparedStatement pstmt) {
        ResultSet rs = null;

        try {
            rs = pstmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(kitchenquery.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }
    
    public int myExecuteUpdate (PreparedStatement pstmt){
        int rs = 0;
        try {
            rs = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(kitchenquery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

}
