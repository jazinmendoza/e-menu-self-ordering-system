/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager_access;

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
public class Query {

    Connection con = null;

    public Connection getConnection() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/e-menu", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public ArrayList<Product> getAllProducts(String productLine) {
        ArrayList<Product> list = new ArrayList<Product>();
        Connection con = getConnection();
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT ");
            sb.append(" * ");
            sb.append("FROM ");
            sb.append(" item ");
            sb.append(" WHERE ");
            sb.append(" productLine = ? ");
            sb.append(";");
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setString(1, productLine);
            rs = myExecuteQuery(pstmt);

            while (rs.next()) {
                Product product = new Product(rs.getString("productId"), rs.getString("productName"),
                        rs.getString("productLine"), rs.getInt("qtyInStock"), rs.getDouble("price"),
                        rs.getBlob("image"));
                list.add(product);
            }
            pstmt.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public void updateProduct(Product product, InputStream image) {

        Connection con = getConnection();
        PreparedStatement pstmt;

        try {
            StringBuffer sb = new StringBuffer();
            sb.append("UPDATE");
            sb.append(" item ");
            sb.append("SET ");
            sb.append(" productName = ?,");
            sb.append(" qtyInStock = ?,");
            sb.append(" price = ?,");
            sb.append(" image = ?");
            sb.append(" WHERE");
            sb.append(" productID = ?");
            sb.append(";");
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setString(1, product.getProductName());
            pstmt.setInt(2, product.getQtyInStock());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setBlob(4, image);
            pstmt.setString(5, product.getProductID());
            if (myExecuteUpdate(pstmt) == 1) {
                System.out.println("Updated");
            };

            pstmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addNewProduct(Product product, InputStream image) {

        Connection con = getConnection();
        PreparedStatement pstmt;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("INSERT");
            sb.append(" INTO ");
            sb.append(" item ");
            sb.append(" (productId , productName, productLine, qtyInStock, price, image)");
            sb.append(" VALUES ");
            sb.append(" (?, ?, ? , ?, ?, ?) ");
            sb.append(";");
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setString(1, product.getProductID());
            pstmt.setString(2, product.getProductName());
            pstmt.setString(3, product.getProductLine());
            pstmt.setInt(4, product.getQtyInStock());
            pstmt.setDouble(5, product.getPrice());
            pstmt.setBlob(6, image);
            if (myExecuteUpdate(pstmt) == 1) {
                System.out.println("Inserted");
            };

            pstmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<ProductLine> getProductLines() {
        ArrayList<ProductLine> list = new ArrayList<ProductLine>();
        Connection con = getConnection();
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT ");
            sb.append(" * ");
            sb.append("FROM ");
            sb.append(" productlines");
            sb.append(";");
            pstmt = con.prepareStatement(sb.toString());

            rs = myExecuteQuery(pstmt);

            while (rs.next()) {
                ProductLine productline = new ProductLine(rs.getString("productLine"));
                list.add(productline);
            }
            pstmt.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public void deleteProduct(Product product) {

        Connection con = getConnection();
        PreparedStatement pstmt;

        try {
            StringBuffer sb = new StringBuffer();
            sb.append("DELETE");
            sb.append(" FROM ");
            sb.append(" item ");
            sb.append(" WHERE");
            sb.append(" productID = ?");
            sb.append(";");
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setString(1, product.getProductID());
            if (myExecuteUpdate(pstmt) == 1) {
                System.out.println("Deleted");
            };

            pstmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public int myExecuteUpdate(PreparedStatement pstmt) {
        int rs = 0;

        try {
            rs = pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

}
