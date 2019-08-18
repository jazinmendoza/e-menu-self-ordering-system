/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashier_access;

import java.io.InputStream;
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
 * @author Jazin Mendoza
 */
public class CashierQuery {

    Connection con = null;

    public Connection getConnection() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/e-menu", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(CashierQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }


    public ResultSet myExecuteQuery(PreparedStatement pstmt) {
        ResultSet rs = null;

        try {
            rs = pstmt.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(CashierQuery.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

    public int myExecuteUpdate(PreparedStatement pstmt) {
        int rs = 0;

        try {
            rs = pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CashierQuery.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }
    
    public void updateTableStatus(int tableNum) throws SQLException{
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append("tabledetails ");
        sb.append("SET ");
        sb.append("status = 1 ");
        sb.append("WHERE ");
        sb.append("tableNum = ?");
        sb.append(";");

        PreparedStatement pstmt;

        Connection con = getConnection();

        ResultSet rs = null;
        pstmt = con.prepareStatement(sb.toString());
        pstmt.setString(1, Integer.toString(tableNum));
        
        if(myExecuteUpdate(pstmt)==1){
            System.out.println("UPDATED");
        }
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
        sb.append("tabledetails ");
        sb.append(";");

        PreparedStatement pstmt;

        Connection con = getConnection();
        ResultSet rs = null;
        pstmt = con.prepareStatement(sb.toString());
        rs = myExecuteQuery(pstmt);
        while (rs.next()) {
            tableNum = rs.getInt("tableNum");
            status = rs.getInt("status");
            products = getOrderdetails(tableNum);
            orderList.add(new Order(products, tableNum, status));
        }

        rs.close();

        return orderList;
    }
    
    public void insertPayment(float total, int table) throws SQLException{
        StringBuilder sb = new StringBuilder();
        StringBuilder on = new StringBuilder();
        StringBuilder st = new StringBuilder();
        
              
        sb.append("INSERT INTO `payment`(`orderNum`, `amount`) VALUES (?,?)");
        on.append("SELECT orderNum FROM orders WHERE tableNum = ? AND orderStatus = 2");
        st.append("UPDATE orders SET orderStatus = 3 WHERE orderNum = ?");
        
        PreparedStatement pstmt;
        PreparedStatement pstmtordernum;
        PreparedStatement pstmtstat;

        Connection con = getConnection();
        int ordernum = 0;
        ResultSet rs = null;        
        
        pstmtordernum = con.prepareStatement(on.toString());
        pstmtordernum.setInt(1, table);
        rs = myExecuteQuery(pstmtordernum);
        System.out.println(pstmtordernum.toString());
        System.out.println(rs.toString());
        while(rs.next()){
            ordernum = rs.getInt("orderNum");
            System.out.println("ORDERNUM: "+ ordernum);
        }
             
        pstmt = con.prepareStatement(sb.toString());
        pstmt.setInt(1, ordernum);
        pstmt.setFloat(2, total);        
        
        System.out.println(pstmt.toString());
        if(myExecuteUpdate(pstmt)==1){
            System.out.println("UPDATED2");
        }
        pstmtstat = con.prepareStatement(st.toString());
        pstmtstat.setInt(1, ordernum);
        myExecuteUpdate(pstmtstat);
    }
    
    public ArrayList<Product> getOrderdetails(int tableNum) throws SQLException {
        ArrayList<Product> products = new ArrayList<Product>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM orders JOIN tabledetails ON tabledetails.tableNum= orders.tableNum ");
        sb.append("JOIN orderdetails ON orderdetails.orderNum = orders.orderNum JOIN item ON ");
        sb.append("item.productId = orderdetails.productId WHERE tabledetails.tableNum = ?");
        sb.append(";");

        PreparedStatement pstmt;

        Connection con = getConnection();
        ResultSet rs = null;
        pstmt = con.prepareStatement(sb.toString());
        pstmt.setString(1, Integer.toString(tableNum));
        rs = myExecuteQuery(pstmt);
        while (rs.next()) {
            float subtotal = rs.getInt("quantity") * rs.getFloat("price");
            products.add(new Product(rs.getString("productName"), rs.getInt("quantity"), rs.getFloat("price"), subtotal));
        }

        rs.close();
        return products;
    }

}
