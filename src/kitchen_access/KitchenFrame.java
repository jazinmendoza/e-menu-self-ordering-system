/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package kitchen_access;

/*
 * KitchenFrame.java
 *
 */
import java.awt.*;
import static java.awt.Color.black;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class KitchenFrame extends JFrame {

    private ArrayList<Order> KitchenList = new ArrayList();

    public ArrayList<Order> getKitchenList() {
        return KitchenList;
    }

    public void setKitchenList(ArrayList<Order> productList) {
        this.KitchenList = KitchenList;
    }

    static final String gapList[] = {"0", "10", "15", "20"};
    final static int maxGap = 10;
    JComboBox horGapComboBox;
    JComboBox verGapComboBox;
    JButton applyButton = new JButton("Apply gaps");
    GridLayout experimentLayout = new GridLayout(0, 4);

    private Toolkit tk = Toolkit.getDefaultToolkit();

    public KitchenFrame() {
        this.setUndecorated(true);
//        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setVisible(true);
        this.setPreferredSize(new Dimension((int) tk.getScreenSize().getWidth(),
                (int) tk.getScreenSize().getHeight()));
        JPanel header = new JPanel();
        header.setBackground(new Color(166, 206, 57));
        this.add(header, BorderLayout.NORTH);
        JLabel icon = new JLabel();
        icon.setIcon(new ImageIcon(getClass().getResource("/manager_access/logo@2x.png"))); // NOI18N
        icon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        header.add(icon, BorderLayout.CENTER);
    }

    public void initGaps() {
        horGapComboBox = new JComboBox(gapList);
        verGapComboBox = new JComboBox(gapList);
    }

    public Component addComponentstoPane(ArrayList<Order> kitcheneeds) {
        System.out.println(kitcheneeds.size());
        JPanel show = new JPanel();

        show.revalidate();
        show.repaint();
        show.removeAll();

        show.setLayout(experimentLayout);
        //ProductList productList = new ProductList();

        //Add buttons to experiment with Grid Layout
        if (kitcheneeds.size() < 4) {
            for (int i = 0; i < kitcheneeds.size(); i++) {
                System.out.println(kitcheneeds.get(i).getTableNum());
                show.add(addPanel(kitcheneeds.get(i)));
            }
            int fill = 4 - kitcheneeds.size();
            for (int j = 0; j < fill; j++) {
                show.add(addPanelFiller());
            }
        } else {
            for (int i = 0; i < 4; i++) {
                System.out.println(kitcheneeds.get(i).getTableNum());
                show.add(addPanel(kitcheneeds.get(i)));
            }

        }
//        pane.add(prods, BorderLayout.CENTER);
        return show;
    }

    public Component addPanelFiller() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(166, 206, 57));
        panel.setLayout(new GridBagLayout());
        return panel;
    }

    JLabel tb;
    JLabel settablenumber;
    JLabel setordernumber;
    JLabel orderswrd;
    JPanel listorders;
    JLabel ss;
    JLabel setstatus;
    JButton serve;

    public Component addPanel(Order kitch) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(166, 206, 57));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel table = new JPanel();
        table.setLayout(new BoxLayout(table, BoxLayout.X_AXIS));
        table.setBackground(new Color(166, 206, 57));

        JPanel order = new JPanel();
        order.setLayout(new BoxLayout(order, BoxLayout.X_AXIS));
        order.setBackground(new Color(166, 206, 57));

        JLabel tableLabel;
        JLabel tableNum;

        JLabel orderLabel;
        JLabel orderNum;

        JPanel orders = new JPanel();

        JButton serve;
        JTable allOrders;

//        tableNum = new JLabel("Table No: " + prods.get(ndx).getProdName(), SwingConstants.LEADING);
        tableLabel = new JLabel("Table No:        ", SwingConstants.LEADING);
        tableLabel.setFont(new Font("Tahoma", 0, 16));
        table.add(tableLabel);

        tableNum = new JLabel(Integer.toString(kitch.getTableNum(), SwingConstants.RIGHT));
        tableNum.setFont(new Font("Tahoma", 0, 20));

        StringBuilder sb = new StringBuilder();
        sb.append(tableLabel);
        sb.append(tableNum);

        table.add(tableNum);
//        orderNum = new JLabel("Order No: " + prods.get(ndx).getProdName(), SwingConstants.LEADING);
        panel.add(table);

        orderLabel = new JLabel("Order No:        ", SwingConstants.LEADING);
        orderLabel.setFont(new Font("Tahoma", 0, 16));
        order.add(orderLabel);

        orderNum = new JLabel(Integer.toString(kitch.getOrderNum()), SwingConstants.RIGHT);
        orderNum.setFont(new Font("Tahoma", 0, 20));

        StringBuilder on = new StringBuilder();
        on.append(orderLabel);

        order.add(orderNum);
        System.out.println(orderNum.getText());

//        orderNum = new JLabel("Order No: " + prods.get(ndx).getProdName(), SwingConstants.LEADING);
        panel.add(order);

        allOrders = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        allOrders.setFont(new Font("Tahoma", 0, 14));
        Object[] columns = {"Product Name", "Quantity"};

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        allOrders.setModel(model);
        allOrders.setMinimumSize(new Dimension(300, 400));
        allOrders.setPreferredSize(new Dimension(300, 400));
        allOrders.getColumn(columns[0]).setPreferredWidth(200);
        allOrders.getColumn(columns[1]).setPreferredWidth(20);

        JScrollPane tableOrders = new JScrollPane(allOrders);
        tableOrders.setPreferredSize(new Dimension(300, 400));
        tableOrders.getViewport().add(allOrders);
        Object[] row = new Object[columns.length];

        for (int i = 0; i < kitch.getOrderList().size(); i++) {
            row[0] = kitch.getOrderList().get(i).getProdName();
            row[1] = kitch.getOrderList().get(i).getQuantity();
            model.addRow(row);
            allOrders.setRowHeight(i, 30);
        }

        orders.add(tableOrders);
        orders.setBackground(new Color(166, 206, 57));
        panel.add(orders);

        serve = new JButton("SERVE");
        serve.setPreferredSize(new Dimension(200, 80));
        serve.setMinimumSize(new Dimension(200, 80));
        serve.setMaximumSize(new Dimension(200, 80));
        panel.add(serve);

        serve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // mu tangtang sha sa arraylist
                kitchenquery query = new kitchenquery();
                try {
                    query.updateStatus(kitch.getOrderNum());
                    refreshContents(kitch.getOrderNum());
                } catch (SQLException ex) {
                    Logger.getLogger(KitchenFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("ORDERNUM: " + kitch.getOrderNum());
            }
        });
        return panel;
    }

    public void refreshContents(int orderNum) {
        kitchenquery query = new kitchenquery();
        ArrayList<Order> orders = new ArrayList<Order>();
        System.out.println("refresh");
        try {
            orders = query.getOrders();
            createAndShowGUI();
            this.setVisible(false);
        } catch (Exception e) {
            Logger.getLogger(KitchenFrame.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Create the GUI and show it. For thread safety, this method is invoked
     * from the event dispatch thread.
     */
    private static void createAndShowGUI() throws SQLException {
        //Create and set up the window.
        KitchenFrame frame = new KitchenFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        kitchenquery query = new kitchenquery();
        ArrayList<Order> allOrders = query.getOrders();
        frame.getContentPane().add(frame.addComponentstoPane(allOrders));
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldmetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                KitchenFrame frame = new KitchenFrame();
                try {
                    createAndShowGUI();
                } catch (SQLException ex) {
                    Logger.getLogger(KitchenFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
