/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer_access;

import java.awt.Color;
import static java.awt.Color.green;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author danpiamonte
 */
public class Step1 extends javax.swing.JFrame {
    public static int tableNumber = 2;
    /**
     * Creates new form Step1
     */
    
    private ArrayList<Product> tempList1;
    private ArrayList<Product> tempList2;
    
    private static OrderList orderList = new OrderList();
    ArrayList<Order> temp = new ArrayList();
    JButton Tray;
//    JFrame wframe, dframe, tframe;

    JLabel prodName;
    
    public ArrayList<Product> getTempList1() {
        return tempList1;
    }

    public void setTempList1(ArrayList<Product> tempList1) {
        this.tempList1 = tempList1;
    }

    public ArrayList<Product> getTempList2() {
        return tempList2;
    }

    public void setTempList2(ArrayList<Product> tempList2) {
        this.tempList2 = tempList2;
    }
    
    public Step1() {
        initComponents();
        this.setLocationRelativeTo(null);
        productContainer.revalidate();
        productContainer.repaint();
        productContainer.removeAll();
        createAndShowGUIPanel(new ProductList().getSteamedDimsumList(),orderList);
    }
    public Step1(OrderList order) {
        initComponents();
        this.setLocationRelativeTo(null);
        productContainer.revalidate();
        productContainer.repaint();
        productContainer.removeAll();
        createAndShowGUIPanel(new ProductList().getSteamedDimsumList(),orderList);
        this.temp = order.getOrderList();
    }
    
    public void createAndShowGUIPanel(ArrayList<Product> productList, OrderList orderList) {
        tempList1 = new ArrayList();
        tempList2 = new ArrayList();
        if (productList.size() < 8) {
            for (int i = 0; i < productList.size(); i++) {
                tempList1.add(productList.get(i));
            }
        }else{
            for (int i = 0; i < 8; i++) {
                tempList1.add(productList.get(i));
            }
            for (int i = 8; i < productList.size(); i++) {
                tempList2.add(productList.get(i));
            }  
        }   
        productContainer.revalidate();
        productContainer.repaint();
        productContainer.removeAll();
        productContainer.add(addComponentsToPane(getTempList1()));
        page.revalidate();
        page.repaint();
        page.removeAll();
        page.setBackground(new Color(166, 206, 57)); 
        page.add(addPagesToPane((productList.size()/8)+1, orderList));
        this.setVisible(true);
    }
    public Component addComponentsToPane(ArrayList<Product> products) {
        JPanel prods = new JPanel();
        prods.setLayout(new GridLayout(0,4));
//        System.out.println("products:");
        
        if (products.size() < 8) {
            for (int i = 0; i < products.size();    i++) {
//                System.out.println(products.get(i).getProdName());
                prods.add(addPanel(products, i));
            }
            int fill = 8 - products.size();
            for(int j = 0; j < fill; j++){
                prods.add(addPanelFiller());
            }
        } else {
             for (int i = 0; i < 8; i++) {
//                System.out.println(products.get(i).getProdName());
                prods.add(addPanel(products, i));
            }
                           
        }
        
        //Add buttons to experiment with Grid Layout
        
        return prods;
    }
    public Component addPanel(ArrayList<Product> prods, int ndx) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(166, 206, 57));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = .25;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        prodName = new JLabel(prods.get(ndx).getProdName(), SwingConstants.CENTER);
        panel.add(prodName, c);

        c.weightx = 1;
        c.weighty = .25;
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 2;
        c.gridwidth = 3;
        JLabel prodImage = new JLabel("", SwingConstants.CENTER);
        byte[] image = prods.get(ndx).getImage();
        System.out.println("image: ");
        System.out.println(image);
        prodImage.setIcon(new ImageIcon(image));
        prodImage.setBackground(green);
        prodImage.setPreferredSize(new Dimension(60, 60));
        prodImage.setMinimumSize(new Dimension(60, 60));
        prodImage.setMaximumSize(new Dimension(60, 60));
        panel.add(prodImage, c);

        c.weightx = 1;
        c.weighty = .25;
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 3;
        StringBuilder price = new StringBuilder();
        price.append(String.format("Php %,.2f", prods.get(ndx).getPrice()));
        JLabel prodPrice = new JLabel(price.toString(), SwingConstants.CENTER);
        prodImage.setPreferredSize(new Dimension(60, 5));
        prodImage.setMinimumSize(new Dimension(60, 5));
        prodImage.setMaximumSize(new Dimension(60, 5));
        panel.add(prodPrice, c);
        c.weightx = 1;
        c.weighty = .25;
        c.gridx = 0;
        c.gridy = 5;
        c.gridheight = 2;
        c.gridwidth = 3;
        Tray = new JButton("Add to Tray");
        panel.add(Tray, c);
        Tray.setPreferredSize(new Dimension(60, 5));
        Tray.setMinimumSize(new Dimension(60, 5));
        Tray.setMaximumSize(new Dimension(60, 5));

        
        Tray.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boolean check = false;
              for(int i = 0; i < temp.size(); i++){
                  if(!orderList.getOrderList().get(i).getProdID().equalsIgnoreCase(prods.get(ndx).getProdID())){
                      check = false;
                  }else{
                      check= true;
                      break;
                  }
              }
              if(check == false){
                temp.add(new Order(prods.get(ndx).getProdID(), prods.get(ndx).getProdName(), 0, prods.get(ndx).getQtyInStock(),prods.get(ndx).getPrice()));
                
                orderList.addOrder(temp);
//                System.out.println(orderList.getOrderList().size());
                new Success().setVisible(true);
                for(int i = 0; i < (orderList.getOrderList().size()); i++){
//                    System.out.println(orderList.getOrderList().get(i).getProdID());
                }
//                System.out.println("added!");
              }else{
                  new Failed().setVisible(true);
              }
            }
        });

        return panel;
    }
    public Component addPanelFiller() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(166, 206, 57));
        panel.setLayout(new GridBagLayout());
        return panel;
    }
    
     public Component addPagesToPane(int pages, OrderList orderList) {
        JPanel page = new JPanel();
        page.setLayout(new GridLayout(0,pages));

        //Set up components preferred size
//         System.out.println(pages);
        for(int i = 0; i < pages; i++){
            page.add(addPage(i, orderList));
        }
       
       return page;
    }
    
    public Component addPage(int ndx, OrderList orderList) {
        JPanel page = new JPanel();
        JLabel pages = new JLabel("", SwingConstants.CENTER);
        pages.setBackground(new Color(166, 206, 57));   
        page.setBackground(new Color(166, 206, 57)); 
        ndx++;
            pages.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer_access/images/"+ndx+".png")));
       
        page.add(pages);
        int pageNum = ndx;

        pages.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ArrayList<Product> tempList = new ArrayList();
//                System.out.println("hi");
                if (pageNum == 1){
                   tempList = getTempList1();
                } else if( pageNum == 2 ){
                   tempList = getTempList2();
                }
                productContainer.revalidate();
                productContainer.repaint();
                productContainer.removeAll();
                if (tempList.size() < 8) {
                    productContainer.add(addComponentsToPane(tempList));
                } else {
                    productContainer.add(addComponentsToPane(tempList));

                }
            }
        });
        
        return page;
        
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        page = new javax.swing.JPanel();
        productContainer = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        drinksButton = new javax.swing.JButton();
        steamedDimsumButton = new javax.swing.JButton();
        friedDimsumButton = new javax.swing.JButton();
        ricePotsButton = new javax.swing.JButton();
        shaopaoButton = new javax.swing.JButton();
        saladsButton = new javax.swing.JButton();
        soupsButton = new javax.swing.JButton();
        dessertsButton = new javax.swing.JButton();
        NextStep = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(920, 600));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(920, 600));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(166, 206, 57));
        jPanel1.setMaximumSize(new java.awt.Dimension(920, 600));
        jPanel1.setMinimumSize(new java.awt.Dimension(920, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(920, 600));
        jPanel1.setVerifyInputWhenFocusTarget(false);
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentShown(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        page.setBackground(new java.awt.Color(166, 206, 57));
        page.setMaximumSize(new java.awt.Dimension(160, 30));
        page.setMinimumSize(new java.awt.Dimension(160, 30));
        page.setPreferredSize(new java.awt.Dimension(160, 30));
        page.setRequestFocusEnabled(false);
        page.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pageMouseClicked(evt);
            }
        });
        page.setLayout(new java.awt.GridLayout(1, 0));
        jPanel1.add(page, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 550, -1, -1));

        productContainer.setBackground(new java.awt.Color(166, 206, 57));
        productContainer.setMaximumSize(new java.awt.Dimension(610, 390));
        productContainer.setMinimumSize(new java.awt.Dimension(610, 390));
        productContainer.setRequestFocusEnabled(false);
        productContainer.setLayout(new java.awt.CardLayout());
        jPanel1.add(productContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 610, 390));

        jLabel17.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel17.setText("STEP 4");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 80, -1, -1));

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("BILL OUT");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, -1, -1));

        jLabel15.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel15.setText("STEP 3");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, -1, -1));

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("ORDER PROCESSING");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, -1, -1));

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("SUMMARY OF ORDERS");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, -1, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel14.setText("STEP 2");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("CHOOSE ORDER");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer_access/images/logo@2x.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 0, -1, 64));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("STEP 1");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer_access/images/step1.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        drinksButton.setText("DRINKS");
        drinksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drinksButtonActionPerformed(evt);
            }
        });
        jPanel1.add(drinksButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, 180, 30));

        steamedDimsumButton.setText("STEAMED DIMSUM");
        steamedDimsumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                steamedDimsumButtonActionPerformed(evt);
            }
        });
        jPanel1.add(steamedDimsumButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 180, 30));

        friedDimsumButton.setText("FRIED DIMSUM");
        friedDimsumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                friedDimsumButtonActionPerformed(evt);
            }
        });
        jPanel1.add(friedDimsumButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 180, 30));

        ricePotsButton.setText("RICE POTS");
        ricePotsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ricePotsButtonActionPerformed(evt);
            }
        });
        jPanel1.add(ricePotsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 180, 30));

        shaopaoButton.setText("SHAOPAO");
        shaopaoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shaopaoButtonActionPerformed(evt);
            }
        });
        jPanel1.add(shaopaoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 180, 30));

        saladsButton.setText("SALAD AND VEGETABLES");
        saladsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saladsButtonActionPerformed(evt);
            }
        });
        jPanel1.add(saladsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 180, 30));

        soupsButton.setText("SOUPS AND NOODLES");
        soupsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soupsButtonActionPerformed(evt);
            }
        });
        jPanel1.add(soupsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 180, 30));

        dessertsButton.setText("DESSERTS");
        dessertsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dessertsButtonActionPerformed(evt);
            }
        });
        jPanel1.add(dessertsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 180, 30));

        NextStep.setFont(new java.awt.Font("Source Sans Pro", 0, 18)); // NOI18N
        NextStep.setText(">");
        NextStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextStepActionPerformed(evt);
            }
        });
        jPanel1.add(NextStep, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 520, 40, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void jPanel1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentShown
        // TODO add your handling code here:
        getContentPane().setBackground(new Color(166,206,57));
    }//GEN-LAST:event_jPanel1ComponentShown

    private void NextStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextStepActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        
        Query q = new Query();
        q.occupyTable(tableNumber);
        new Step2(this.orderList, tableNumber).setVisible(true);
    }//GEN-LAST:event_NextStepActionPerformed

    private void dessertsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dessertsButtonActionPerformed
        // TODO add your handling code here:
       createAndShowGUIPanel(new ProductList().getDessertList(),orderList);
    }//GEN-LAST:event_dessertsButtonActionPerformed

    private void soupsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soupsButtonActionPerformed
        // TODO add your handling code here:
       createAndShowGUIPanel(new ProductList().getSoupsList(),orderList);
    }//GEN-LAST:event_soupsButtonActionPerformed

    private void saladsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saladsButtonActionPerformed
        // TODO add your handling code here:
        createAndShowGUIPanel(new ProductList().getSaladList(),orderList);
    }//GEN-LAST:event_saladsButtonActionPerformed

    private void shaopaoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shaopaoButtonActionPerformed
        // TODO add your handling code here:
        createAndShowGUIPanel(new ProductList().getShaopaoList(),orderList);
    }//GEN-LAST:event_shaopaoButtonActionPerformed

    private void ricePotsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ricePotsButtonActionPerformed
        // TODO add your handling code here:
        createAndShowGUIPanel(new ProductList().getRicePotsList(),orderList);
    }//GEN-LAST:event_ricePotsButtonActionPerformed

    private void friedDimsumButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friedDimsumButtonActionPerformed
        // TODO add your handling code here:
        createAndShowGUIPanel(new ProductList().getFriedDimsumList(), orderList);
    }//GEN-LAST:event_friedDimsumButtonActionPerformed

    private void steamedDimsumButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_steamedDimsumButtonActionPerformed
        // TODO add your handling code here
        createAndShowGUIPanel(new ProductList().getSteamedDimsumList(),orderList);
    }//GEN-LAST:event_steamedDimsumButtonActionPerformed

    private void drinksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drinksButtonActionPerformed
        // TODO add your handling code here:
       createAndShowGUIPanel(new ProductList().getDrinksList(),orderList);
    }//GEN-LAST:event_drinksButtonActionPerformed

    private void pageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pageMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_pageMouseClicked
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Step1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Step1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Step1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Step1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Step1().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton NextStep;
    private javax.swing.JButton dessertsButton;
    private javax.swing.JButton drinksButton;
    private javax.swing.JButton friedDimsumButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel page;
    private javax.swing.JPanel productContainer;
    private javax.swing.JButton ricePotsButton;
    private javax.swing.JButton saladsButton;
    private javax.swing.JButton shaopaoButton;
    private javax.swing.JButton soupsButton;
    private javax.swing.JButton steamedDimsumButton;
    // End of variables declaration//GEN-END:variables
}
