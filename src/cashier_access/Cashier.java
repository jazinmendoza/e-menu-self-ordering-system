package cashier_access;

import customer_access.*;
import java.awt.Color;
import static java.awt.Color.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gail semilla ducao
 */
public class Cashier extends javax.swing.JFrame {

    /**
     * Creates new form Cashier
     *
     */
    float total = 0;
    private ArrayList<Order> orderList = new ArrayList();
    private Component[] tables;
    private JPanel rDetails = new JPanel();
    private int tableNum;
    private int finalNdx;

    public Cashier() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.tables = tableContainer.getComponents();
    }

    public void getArray() throws SQLException {
       CashierQuery query = new CashierQuery();
       orderList = query.getOrders();
       occupyButtons();
    }

    public void refreshArray(int table) {
        for (int j = 0; j < orderList.size(); j++) {
            if (orderList.get(j).getTableNum() == table) {
                orderList.remove(j);
                break;
            }
        }
        for (int i = 0; i < tables.length; i++) {
            if (tables[i] instanceof JButton) {
                JButton button = (JButton) tables[i];
                int num = Integer.parseInt(button.getText());
                if(num == table){
                    button.setBackground(white);
                    break;
                }
            }
        }
        
    }

    public void getData(int tableNum, String color) {
        System.out.println(tableNum);
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getTableNum() == tableNum) {
                break;
            }
        }
        if (color.equalsIgnoreCase("java.awt.Color[r=255,g=255,b=255]")) {
            dstatus.setText("Table " + tableNum + " is available\n");
            avail.setVisible(true);
        } else if (color.equalsIgnoreCase("java.awt.Color[r=255,g=200,b=0]")) {
            showReceipt(tableNum);
            print.setVisible(false);
        } else {
            showReceipt(tableNum);
            print.setVisible(true);

        }
    }

    public void occupyButtons() {
        for (int i = 0; i < tables.length; i++) {
            if (tables[i] instanceof JButton) {
                JButton button = (JButton) tables[i];
                int num = Integer.parseInt(button.getText());
                for (int j = 0; j < orderList.size(); j++) {
                    if (orderList.get(j).getTableNum() == num) {
                        if (orderList.get(j).getStatus() == 1) {
                            System.out.println("nay sud" + num);
                            button.setBackground(orange);
                        } else if (orderList.get(j).getStatus() == 2) {
                            System.out.println("billout" + num);
                            button.setBackground(red);
                        } else {
                            System.out.println("table: " + num);
                            button.setBackground(white);
                        }
                    }
                }
            }
        }
    }

    public void showReceipt(int tableNum) {
        Receipt.setVisible(true);
        System.out.println("tableNum: " + tableNum);
        receiptShown(tableNum);
    }

    public void receiptShown(int tableNum) {
        receiptContent.revalidate();
        receiptContent.repaint();
        receiptContent.removeAll();
        receiptContent.add(addScrollPane());
        addTotal();
    }

    public Component addScrollPane() {
        JScrollPane scrollPane = new JScrollPane(rDetails,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        scrollPane.setMinimumSize(new Dimension(500, 400));
        scrollPane.setMaximumSize(new Dimension(500, 400));
        for (int j = 0; j < orderList.size(); j++) {
            if (orderList.get(j).getTableNum() == tableNum) {
                finalNdx = j;
                break;
            }
        }
        System.out.println("Number of orders: " + orderList.get(finalNdx).getOrderList().size());
        for (int i = 0; i < orderList.get(finalNdx).getOrderList().size(); i++) {
            rDetails.revalidate();
            rDetails.repaint();
            rDetails.add(panelOrder(i));
        }

        rDetails.setPreferredSize(new Dimension(500, 400));
        rDetails.setMinimumSize(new Dimension(500, 400));
        rDetails.setMaximumSize(new Dimension(500, 400));
        rDetails.setBounds(0, 0, 500, 400);

        return scrollPane;
    }

    public Component panelOrder(int ndx) {
        JPanel order = new JPanel();
        order.setLayout(new GridLayout(0, 4));
//         System.out.println(ndx);
//         System.out.println(orders.getItemList().get(ndx).getProdName());
        JLabel prodName = new JLabel();
        JLabel prodQty = new JLabel();
        JLabel prodPrice = new JLabel();
        JLabel prodPartial = new JLabel();

        prodName.setText(orderList.get(finalNdx).getOrderList().get(ndx).getProdName());
        prodQty.setText(Integer.toString(orderList.get(finalNdx).getOrderList().get(ndx).getQuantity()));
        prodPrice.setText(Float.toString(orderList.get(finalNdx).getOrderList().get(ndx).getPrice()));
        prodPartial.setText(Float.toString(orderList.get(finalNdx).getOrderList().get(ndx).getSubtotal()));

        prodName.setMaximumSize(new Dimension(121, 20));
        prodName.setMinimumSize(new Dimension(121, 20));
        prodName.setPreferredSize(new Dimension(121, 20));
        prodQty.setMaximumSize(new Dimension(121, 20));
        prodQty.setMinimumSize(new Dimension(121, 20));
        prodQty.setPreferredSize(new Dimension(121, 20));
        prodPrice.setMaximumSize(new Dimension(121, 20));
        prodPrice.setMinimumSize(new Dimension(121, 20));
        prodPrice.setPreferredSize(new Dimension(121, 20));

        prodPrice.setHorizontalAlignment(JLabel.CENTER);
        prodName.setHorizontalAlignment(JLabel.CENTER);
        prodQty.setHorizontalAlignment(JLabel.CENTER);
        prodPartial.setHorizontalAlignment(JLabel.CENTER);
        
        order.add(prodName);
        order.add(prodQty);
        order.add(prodPrice);
        order.add(prodPartial);
//
//        System.out.println(order.toString());
//        System.out.println("PRODName: " + prodName.getText());
        return order;
    }

    public void changeStatus(int table) throws SQLException {
        CashierQuery con = new CashierQuery();
        System.out.println("Table Billout"+table);
        con.updateTableStatus(table);
        con.insertPayment(total, table);
        Receipt.setVisible(false);
        refreshArray(table);
    }

    public void addTotal(){
        for(int i = 0; i < orderList.get(finalNdx).getOrderList().size(); i++){
         total += orderList.get(finalNdx).getOrderList().get(i).getSubtotal();
        }
        
        Total.setText(String.format("PHP %,.2f", total));
    }
    
    /**
     *
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        avail = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        dstatus = new javax.swing.JLabel();
        Receipt = new javax.swing.JFrame();
        details = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        print = new javax.swing.JButton();
        Total = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        receiptContent = new javax.swing.JPanel();
        cashierAreaLAbel = new javax.swing.JLabel();
        tableContainer = new javax.swing.JPanel();
        table1 = new javax.swing.JButton();
        table3 = new javax.swing.JButton();
        table5 = new javax.swing.JButton();
        table7 = new javax.swing.JButton();
        table9 = new javax.swing.JButton();
        table10 = new javax.swing.JButton();
        table8 = new javax.swing.JButton();
        table6 = new javax.swing.JButton();
        table4 = new javax.swing.JButton();
        table2 = new javax.swing.JButton();
        table11 = new javax.swing.JButton();
        table12 = new javax.swing.JButton();
        table18 = new javax.swing.JButton();
        table14 = new javax.swing.JButton();
        table17 = new javax.swing.JButton();
        table15 = new javax.swing.JButton();
        table13 = new javax.swing.JButton();
        table16 = new javax.swing.JButton();
        table19 = new javax.swing.JButton();
        table21 = new javax.swing.JButton();
        table20 = new javax.swing.JButton();
        table22 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        occupied = new javax.swing.JLabel();
        billout = new javax.swing.JLabel();
        available = new javax.swing.JLabel();

        avail.setAlwaysOnTop(true);
        avail.setMaximumSize(new java.awt.Dimension(537, 316));
        avail.setMinimumSize(new java.awt.Dimension(537, 316));
        avail.setResizable(false);

        jPanel4.setBackground(new java.awt.Color(153, 204, 0));
        jPanel4.setPreferredSize(new java.awt.Dimension(537, 316));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cashier_access/logo@2x.png"))); // NOI18N

        dstatus.setFont(new java.awt.Font("Tekton Pro", 0, 18)); // NOI18N
        dstatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dstatus.setText("This Table is Available for dining :)");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(dstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(dstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout availLayout = new javax.swing.GroupLayout(avail.getContentPane());
        avail.getContentPane().setLayout(availLayout);
        availLayout.setHorizontalGroup(
            availLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        availLayout.setVerticalGroup(
            availLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        avail.getAccessibleContext().setAccessibleParent(this);

        Receipt.setAlwaysOnTop(true);
        Receipt.setMinimumSize(new java.awt.Dimension(560, 680));
        Receipt.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                ReceiptComponentShown(evt);
            }
        });

        details.setBackground(new java.awt.Color(153, 204, 0));
        details.setMaximumSize(new java.awt.Dimension(560, 680));
        details.setMinimumSize(new java.awt.Dimension(560, 680));
        details.setPreferredSize(new java.awt.Dimension(560, 680));
        details.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cashier_access/logo@2x.png"))); // NOI18N
        details.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        print.setText("PRINT RECEIPT");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        details.add(print, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 600, 140, 35));

        Total.setFont(new java.awt.Font("Source Sans Pro Light", 1, 14)); // NOI18N
        Total.setText("PHP ");
        details.add(Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 550, 160, 50));

        jLabel9.setFont(new java.awt.Font("Source Sans Pro Light", 1, 18)); // NOI18N
        jLabel9.setText("TOTAL:");
        details.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 550, -1, 50));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Food Name");
        details.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 90, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Quantity");
        details.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 80, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Price");
        details.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, 60, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Sum");
        details.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 60, -1));

        receiptContent.setBackground(new java.awt.Color(153, 204, 0));
        receiptContent.setMaximumSize(new java.awt.Dimension(500, 400));
        receiptContent.setMinimumSize(new java.awt.Dimension(500, 400));
        receiptContent.setPreferredSize(new java.awt.Dimension(500, 400));
        receiptContent.setVerifyInputWhenFocusTarget(false);
        receiptContent.setLayout(new java.awt.BorderLayout());
        details.add(receiptContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 500, 400));

        javax.swing.GroupLayout ReceiptLayout = new javax.swing.GroupLayout(Receipt.getContentPane());
        Receipt.getContentPane().setLayout(ReceiptLayout);
        ReceiptLayout.setHorizontalGroup(
            ReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(details, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ReceiptLayout.setVerticalGroup(
            ReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(details, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(158, 209, 46));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cashierAreaLAbel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        cashierAreaLAbel.setText("C A S H I E R     A R E A");
        getContentPane().add(cashierAreaLAbel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        tableContainer.setBackground(new java.awt.Color(158, 209, 46));
        tableContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table1.setBackground(new java.awt.Color(255, 255, 255));
        table1.setText("1");
        table1.setMaximumSize(new java.awt.Dimension(70, 60));
        table1.setMinimumSize(new java.awt.Dimension(70, 60));
        table1.setPreferredSize(new java.awt.Dimension(70, 60));
        table1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table1ActionPerformed(evt);
            }
        });
        tableContainer.add(table1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 70, 60));

        table3.setBackground(new java.awt.Color(255, 255, 255));
        table3.setText("3");
        table3.setMaximumSize(new java.awt.Dimension(70, 60));
        table3.setMinimumSize(new java.awt.Dimension(70, 60));
        table3.setPreferredSize(new java.awt.Dimension(70, 60));
        table3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table3ActionPerformed(evt);
            }
        });
        tableContainer.add(table3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 70, 60));

        table5.setBackground(new java.awt.Color(255, 255, 255));
        table5.setText("5");
        table5.setMaximumSize(new java.awt.Dimension(70, 60));
        table5.setMinimumSize(new java.awt.Dimension(70, 60));
        table5.setPreferredSize(new java.awt.Dimension(70, 60));
        table5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table5ActionPerformed(evt);
            }
        });
        tableContainer.add(table5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 70, 60));

        table7.setBackground(new java.awt.Color(255, 255, 255));
        table7.setText("7");
        table7.setMaximumSize(new java.awt.Dimension(70, 60));
        table7.setMinimumSize(new java.awt.Dimension(70, 60));
        table7.setPreferredSize(new java.awt.Dimension(70, 60));
        table7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table7ActionPerformed(evt);
            }
        });
        tableContainer.add(table7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, -1, -1));

        table9.setBackground(new java.awt.Color(255, 255, 255));
        table9.setText("9");
        table9.setMaximumSize(new java.awt.Dimension(70, 60));
        table9.setMinimumSize(new java.awt.Dimension(70, 60));
        table9.setPreferredSize(new java.awt.Dimension(70, 60));
        table9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table9ActionPerformed(evt);
            }
        });
        tableContainer.add(table9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 490, -1, -1));

        table10.setBackground(new java.awt.Color(255, 255, 255));
        table10.setText("10");
        table10.setMaximumSize(new java.awt.Dimension(70, 60));
        table10.setMinimumSize(new java.awt.Dimension(70, 60));
        table10.setPreferredSize(new java.awt.Dimension(70, 60));
        table10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table10ActionPerformed(evt);
            }
        });
        tableContainer.add(table10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 490, -1, -1));

        table8.setBackground(new java.awt.Color(255, 255, 255));
        table8.setText("8");
        table8.setMaximumSize(new java.awt.Dimension(70, 60));
        table8.setMinimumSize(new java.awt.Dimension(70, 60));
        table8.setPreferredSize(new java.awt.Dimension(70, 60));
        table8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table8ActionPerformed(evt);
            }
        });
        tableContainer.add(table8, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, -1, -1));

        table6.setBackground(new java.awt.Color(255, 255, 255));
        table6.setText("6");
        table6.setMaximumSize(new java.awt.Dimension(70, 60));
        table6.setMinimumSize(new java.awt.Dimension(70, 60));
        table6.setPreferredSize(new java.awt.Dimension(70, 60));
        table6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table6ActionPerformed(evt);
            }
        });
        tableContainer.add(table6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, -1, -1));

        table4.setBackground(new java.awt.Color(255, 255, 255));
        table4.setText("4");
        table4.setMaximumSize(new java.awt.Dimension(70, 60));
        table4.setMinimumSize(new java.awt.Dimension(70, 60));
        table4.setPreferredSize(new java.awt.Dimension(70, 60));
        table4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table4ActionPerformed(evt);
            }
        });
        tableContainer.add(table4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 70, 60));

        table2.setBackground(new java.awt.Color(255, 255, 255));
        table2.setText("2");
        table2.setMaximumSize(new java.awt.Dimension(70, 60));
        table2.setMinimumSize(new java.awt.Dimension(70, 60));
        table2.setPreferredSize(new java.awt.Dimension(70, 60));
        table2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table2ActionPerformed(evt);
            }
        });
        tableContainer.add(table2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 70, 60));

        table11.setBackground(new java.awt.Color(255, 255, 255));
        table11.setText("11");
        table11.setMaximumSize(new java.awt.Dimension(93, 98));
        table11.setMinimumSize(new java.awt.Dimension(93, 98));
        table11.setPreferredSize(new java.awt.Dimension(93, 98));
        table11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table11ActionPerformed(evt);
            }
        });
        tableContainer.add(table11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, -1, -1));

        table12.setBackground(new java.awt.Color(255, 255, 255));
        table12.setText("12");
        table12.setMaximumSize(new java.awt.Dimension(93, 98));
        table12.setMinimumSize(new java.awt.Dimension(93, 98));
        table12.setPreferredSize(new java.awt.Dimension(93, 98));
        table12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table12ActionPerformed(evt);
            }
        });
        tableContainer.add(table12, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, -1, -1));

        table18.setBackground(new java.awt.Color(255, 255, 255));
        table18.setText("18");
        table18.setMaximumSize(new java.awt.Dimension(93, 98));
        table18.setMinimumSize(new java.awt.Dimension(93, 98));
        table18.setPreferredSize(new java.awt.Dimension(93, 98));
        table18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table18ActionPerformed(evt);
            }
        });
        tableContainer.add(table18, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, -1, -1));

        table14.setBackground(new java.awt.Color(255, 255, 255));
        table14.setText("14");
        table14.setMaximumSize(new java.awt.Dimension(93, 98));
        table14.setMinimumSize(new java.awt.Dimension(93, 98));
        table14.setPreferredSize(new java.awt.Dimension(93, 98));
        table14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table14ActionPerformed(evt);
            }
        });
        tableContainer.add(table14, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 270, -1, -1));

        table17.setBackground(new java.awt.Color(255, 255, 255));
        table17.setText("17");
        table17.setMaximumSize(new java.awt.Dimension(93, 98));
        table17.setMinimumSize(new java.awt.Dimension(93, 98));
        table17.setPreferredSize(new java.awt.Dimension(93, 98));
        table17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table17ActionPerformed(evt);
            }
        });
        tableContainer.add(table17, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 420, -1, -1));

        table15.setBackground(new java.awt.Color(255, 255, 255));
        table15.setText("15");
        table15.setMaximumSize(new java.awt.Dimension(93, 98));
        table15.setMinimumSize(new java.awt.Dimension(93, 98));
        table15.setPreferredSize(new java.awt.Dimension(93, 98));
        table15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table15ActionPerformed(evt);
            }
        });
        tableContainer.add(table15, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 270, -1, -1));

        table13.setBackground(new java.awt.Color(255, 255, 255));
        table13.setText("13");
        table13.setMaximumSize(new java.awt.Dimension(93, 98));
        table13.setMinimumSize(new java.awt.Dimension(93, 98));
        table13.setPreferredSize(new java.awt.Dimension(93, 98));
        table13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table13ActionPerformed(evt);
            }
        });
        tableContainer.add(table13, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 110, -1, -1));

        table16.setBackground(new java.awt.Color(255, 255, 255));
        table16.setText("16");
        table16.setMaximumSize(new java.awt.Dimension(93, 98));
        table16.setMinimumSize(new java.awt.Dimension(93, 98));
        table16.setPreferredSize(new java.awt.Dimension(93, 98));
        table16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table16ActionPerformed(evt);
            }
        });
        tableContainer.add(table16, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 270, -1, -1));

        table19.setBackground(new java.awt.Color(255, 255, 255));
        table19.setText("19");
        table19.setMaximumSize(new java.awt.Dimension(93, 98));
        table19.setMinimumSize(new java.awt.Dimension(93, 98));
        table19.setPreferredSize(new java.awt.Dimension(93, 98));
        table19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table19ActionPerformed(evt);
            }
        });
        tableContainer.add(table19, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 420, -1, -1));

        table21.setBackground(new java.awt.Color(255, 255, 255));
        table21.setText("21");
        table21.setMaximumSize(new java.awt.Dimension(197, 123));
        table21.setMinimumSize(new java.awt.Dimension(197, 123));
        table21.setPreferredSize(new java.awt.Dimension(197, 123));
        table21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table21ActionPerformed(evt);
            }
        });
        tableContainer.add(table21, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 260, -1, -1));

        table20.setBackground(new java.awt.Color(255, 255, 255));
        table20.setText("20");
        table20.setMaximumSize(new java.awt.Dimension(197, 123));
        table20.setMinimumSize(new java.awt.Dimension(197, 123));
        table20.setPreferredSize(new java.awt.Dimension(197, 123));
        table20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table20ActionPerformed(evt);
            }
        });
        tableContainer.add(table20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 80, -1, -1));

        table22.setBackground(new java.awt.Color(255, 255, 255));
        table22.setText("22");
        table22.setMaximumSize(new java.awt.Dimension(197, 123));
        table22.setMinimumSize(new java.awt.Dimension(197, 123));
        table22.setPreferredSize(new java.awt.Dimension(197, 123));
        table22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                table22ActionPerformed(evt);
            }
        });
        tableContainer.add(table22, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 430, -1, -1));

        getContentPane().add(tableContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 600));

        jPanel1.setBackground(new java.awt.Color(158, 209, 46));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cashier_access/logo@2x.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jPanel3.setBackground(java.awt.Color.orange);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        occupied.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        occupied.setText("-  Occupied");

        billout.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        billout.setText("- Ready for Bill out");

        available.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        available.setText("- Available");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel1)
                .addGap(98, 98, 98)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(occupied, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(available, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(billout, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(622, 622, 622)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(billout))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(occupied))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(available))
                        .addGap(79, 79, 79))))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 790));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated


    private void ReceiptComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_ReceiptComponentShown
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ReceiptComponentShown

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        try {
            // TODO add your handling code here:
            changeStatus(tableNum);
        } catch (SQLException ex) {
            Logger.getLogger(Cashier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_printActionPerformed

    private void table22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table22ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table22.getText());
        String color = table22.getBackground().toString();
        getData(tableNum, color);

    }//GEN-LAST:event_table22ActionPerformed

    private void table20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table20ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table20.getText());
        String color = table20.getBackground().toString();
        getData(tableNum, color);

    }//GEN-LAST:event_table20ActionPerformed

    private void table21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table21ActionPerformed
        // TODO add your handling code here:

        tableNum = Integer.parseInt(table21.getText());
        String color = table21.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table21ActionPerformed

    private void table19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table19ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table19.getText());
        String color = table19.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table19ActionPerformed

    private void table16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table16ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table16.getText());
        String color = table16.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table16ActionPerformed

    private void table13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table13ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table13.getText());
        String color = table13.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table13ActionPerformed

    private void table15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table15ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table15.getText());
        String color = table15.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table15ActionPerformed

    private void table17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table17ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table17.getText());
        String color = table17.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table17ActionPerformed

    private void table14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table14ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table14.getText());
        String color = table14.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table14ActionPerformed

    private void table18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table18ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table18.getText());
        String color = table18.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table18ActionPerformed

    private void table12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table12ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table12.getText());
        String color = table12.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table12ActionPerformed

    private void table11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table11ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table11.getText());
        String color = table11.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table11ActionPerformed

    private void table2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table2ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table2.getText());
        String color = table2.getBackground().toString();
        getData(tableNum, color);

    }//GEN-LAST:event_table2ActionPerformed

    private void table4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table4ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table4.getText());
        String color = table4.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table4ActionPerformed

    private void table6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table6ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table6.getText());
        String color = table6.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table6ActionPerformed

    private void table8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table8ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table8.getText());
        String color = table8.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table8ActionPerformed

    private void table10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table10ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table10.getText());
        String color = table10.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table10ActionPerformed

    private void table9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table9ActionPerformed
        // TODO add your handling code here
//        int tableNum;
        tableNum = Integer.parseInt(table9.getText());
        String color = table9.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table9ActionPerformed

    private void table7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table7ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table7.getText());
        String color = table7.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table7ActionPerformed

    private void table5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table5ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table5.getText());
        String color = table5.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table5ActionPerformed

    private void table3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table3ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table3.getText());
        String color = table3.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table3ActionPerformed

    private void table1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_table1ActionPerformed
        // TODO add your handling code here:
//        int tableNum;
        tableNum = Integer.parseInt(table1.getText());
        String color = table1.getBackground().toString();
        getData(tableNum, color);
    }//GEN-LAST:event_table1ActionPerformed

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String args[]) throws InterruptedException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cashier.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() 
//                
//            }
//               
//        });
        Cashier n = new Cashier();
        n.setVisible(true);
        try {
            //        n.occupyButtons();
            n.getArray();
        } catch (SQLException ex) {
            Logger.getLogger(Cashier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame Receipt;
    private javax.swing.JLabel Total;
    private javax.swing.JFrame avail;
    private javax.swing.JLabel available;
    private javax.swing.JLabel billout;
    private javax.swing.JLabel cashierAreaLAbel;
    private javax.swing.JPanel details;
    private javax.swing.JLabel dstatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel occupied;
    private javax.swing.JButton print;
    private javax.swing.JPanel receiptContent;
    private javax.swing.JButton table1;
    private javax.swing.JButton table10;
    private javax.swing.JButton table11;
    private javax.swing.JButton table12;
    private javax.swing.JButton table13;
    private javax.swing.JButton table14;
    private javax.swing.JButton table15;
    private javax.swing.JButton table16;
    private javax.swing.JButton table17;
    private javax.swing.JButton table18;
    private javax.swing.JButton table19;
    private javax.swing.JButton table2;
    private javax.swing.JButton table20;
    private javax.swing.JButton table21;
    private javax.swing.JButton table22;
    private javax.swing.JButton table3;
    private javax.swing.JButton table4;
    private javax.swing.JButton table5;
    private javax.swing.JButton table6;
    private javax.swing.JButton table7;
    private javax.swing.JButton table8;
    private javax.swing.JButton table9;
    private javax.swing.JPanel tableContainer;
    // End of variables declaration//GEN-END:variables
}
