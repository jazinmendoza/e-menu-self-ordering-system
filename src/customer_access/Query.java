/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer_access;

/**
 *
 * @author Jazin Mendoza
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 1bestcsharp.blogspot.com
 */
public class Query {

    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/e-menu", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public ArrayList<Product> addProductList(String productLine) {

        ArrayList<Product> list = new ArrayList<Product>();
        Connection con = getConnection();
        ResultSet rs;
        Product p;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append("FROM ");
        sb.append(" item ");
        sb.append(" WHERE ");
        sb.append(" productLine = ?");
        sb.append(" AND qtyInStock > 0");
        sb.append(";");

        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setString(1, productLine);
            rs = myExecuteQuery(pstmt);
            
            while (rs.next()) {
                p = new Product(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getString("productLine"),
                        rs.getInt("qtyInStock"),
                        rs.getFloat("price"),
                        rs.getBytes("image")
                );
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ResultSet myExecuteQuery(PreparedStatement pstmt) {
        ResultSet rs = null;

        try {
            rs = pstmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

    public void insertOrder(int orderNum, int tableNum) {
        ResultSet rs = null;
        Connection con = getConnection();
        System.out.println("in");
        try {
            String insertSQL = "INSERT INTO orders VALUES ("+orderNum+", "+0+")";
            PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void insertOrderdetails(finalList order, int orderNum, int tableNum) {
        ResultSet rs = null;
        Connection con = getConnection();

        try {
            for(int i = 0; i < order.getFinalList().size(); i++){
                int qty = order.getFinalList().get(i).getQuantity();
                String insertSQL = "INSERT INTO orderdetails VALUES ("+orderNum+", ?,"+qty+", "+tableNum+", "+0+")";
                
                PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
                preparedStatement.setString(1, order.getFinalList().get(i).getProdID());
                System.out.println(insertSQL);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void updateBillout(int tableNum, int orderNum) {
        Connection con = getConnection();

        try {
                String insertSQL = "UPDATE tabledetails SET status="+2+" WHERE tableNum="+tableNum;
                
                PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
                preparedStatement.executeUpdate();
                
                String updateStat = "UPDATE `orders` SET orderStatus=2 WHERE orderNum=?";
                
                PreparedStatement pstmt = con.prepareStatement(updateStat);
                pstmt.setString(1, Integer.toString(orderNum));
                pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void occupyTable(int tableNum){
        ResultSet rs = null;
        Connection con = getConnection();
        try {
            String insertSQL = "UPDATE tabledetails SET status = 1 WHERE tableNum = "+tableNum;
            PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void updateQuantity(int qty, String prodID) {
        ResultSet rs = null;
        Connection con = getConnection();
        System.out.println("in");
        try {
            String insertSQL = "UPDATE `item` SET `qtyInStock`= "+qty+" WHERE `productId` = '"+prodID+"'";
            PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public int getQuantity(String prodID) {
        int stock = 0;
        ResultSet rs = null;
        Connection con = getConnection();
        System.out.println("in");
        try {
            String insertSQL = "SELECT `qtyInStock` FROM `item` WHERE `productId` = '"+prodID+"'";
            System.out.println(insertSQL);
            PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
            rs = myExecuteQuery(preparedStatement);
            if(!rs.next()){
                stock = 0;
            }else{
                stock = rs.getInt("qtyInStock");
            }
            System.out.println("stock"+stock);
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stock;
    }
     
     public int getOrderStatus(int orderNum){
          int status = 0;
        ResultSet rs = null;
        Connection con = getConnection();
        System.out.println("in");
        try {
            String insertSQL = "SELECT orderStatus FROM `orders` WHERE orderNum ="+orderNum;
            System.out.println(insertSQL);
            PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
            rs = myExecuteQuery(preparedStatement);
            if(!rs.next()){
                status = 0;
            }else{
                status = rs.getInt("orderStatus");
            }
            System.out.println("status: "+status);
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return status;
     }
     
     public ResultSet getMyOrders(int orderNum){
        ResultSet rs = null;
        Connection con = getConnection();
        System.out.println("in");
        try {
            String insertSQL = "SELECT * FROM `orderdetails` JOIN item ON orderdetails.productId = item.productId WHERE orderdetails.orderNum ="+orderNum;
            System.out.println(insertSQL);
            PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
            rs = myExecuteQuery(preparedStatement);
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
     }
}
