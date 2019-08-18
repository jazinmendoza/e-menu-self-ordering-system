package manager_access;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MenuDisplay extends JFrame {

    static int numOfProductLines = 7;
    GridLayout mainGrid = new GridLayout(0, 1);
    GridLayout bodyGridLayout = new GridLayout(3, 2, 10, 10);
    GridLayout categoryGridLayout = new GridLayout(1, numOfProductLines);
    private Toolkit tk = Toolkit.getDefaultToolkit();
    ArrayList<Product> products = new ArrayList<Product>();
    ArrayList<Product> allProducts = new ArrayList<Product>();
    ArrayList<ProductLine> productLines = new ArrayList<ProductLine>();
    Query query = new Query();
    JPanel body = new JPanel();
    JTabbedPane divisions = new JTabbedPane();

    public MenuDisplay() {
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setVisible(true);
        this.setPreferredSize(new Dimension((int) tk.getScreenSize().getWidth(),
                (int) tk.getScreenSize().getHeight()));
        JPanel header = new JPanel();
        header.setBackground(new Color(158, 209, 46));
        this.add(header, BorderLayout.NORTH);
        JLabel icon = new JLabel();
        icon.setIcon(new ImageIcon(getClass().getResource("/manager_access/logo@2x.png"))); // NOI18N
        icon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        header.add(icon, BorderLayout.CENTER);
    }

    public void addComponentsToPane(final Container pane) {

        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(Color.black);

        divisions.setFont(new java.awt.Font("Tahoma", 0, 20));
        productLines = query.getProductLines();
        for (int i = 0; productLines.size() > i; i++) {
            JPanel categories = new JPanel();
            categories.setLayout(new GridLayout(1, 1));
            divisions.addTab(productLines.get(i).getProductLine(), categories);
            JScrollPane scrollPane = new JScrollPane(showProducts(productLines.get(i).getProductLine()));
            categories.add(scrollPane);
            body.add(divisions);
        }
//        divisions.addTab("Add Productline", new JPanel());
        pane.add(body);

    }

    public JTable showProducts(String productLine) {
        products = query.getAllProducts(productLine);
        allProducts.addAll(products);
//        String[] columnNames = {"Product ID", "Product Name", "Qty In Stock", "Price"};
//        Object[][] data = new Object[products.size()][5];
//        JTable table = new JTable(data, columnNames) {
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };

////            table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
////            table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField()));
//
//        table.setFillsViewportHeight(true);
//        for (int j = 0; j < products.size(); j++) {
//            data[j][0] = products.get(j).getProductID();
//            data[j][1] = products.get(j).getProductName();
//            data[j][2] = products.get(j).getQtyInStock();
//            data[j][3] = products.get(j).getPrice();
//        }
//        try {
//            for (int row = 0; row < table.getRowCount(); row++) {
//                int rowHeight = table.getRowHeight();
//
//                for (int column = 0; column < table.getColumnCount(); column++) {
//                    Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
//                    rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
//                }
//
//                table.setRowHeight(row, 60);
//            }
//
//        } catch (ClassCastException e) {
//        }
        JTable table = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setRowHeight(50);
        table.setFont(new Font("Tahoma", 0, 18));
        Object[] columns = {"Product ID", "Product Name", "Qty In Stock", "Price"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        Object[] row = new Object[columns.length];
        for (int j = 0; j < products.size(); j++) {
            row[0] = products.get(j).getProductID();
            row[1] = products.get(j).getProductName();
            row[2] = products.get(j).getQtyInStock();
            row[3] = products.get(j).getPrice();
            model.addRow(row);
        }

        row[0] = "Add Product";
        row[1] = " ";
        row[2] = " ";
        row[3] = " ";
        model.addRow(row);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int index = table.getSelectedRow();
                if (model.getValueAt(index, 0).equals("Add Product")) {
                    StringBuffer id = new StringBuffer();
                    if (productLine.equals("Desserts")) {
                        if (model.getRowCount() < 10) {
                            id.append("DS-00");
                        }else{
                            id.append("DS-0");
                        }
                    } else if (productLine.equals("Fried Dimsum")) {
                         if (model.getRowCount() < 10) {
                            id.append("FD-00");
                        }else{
                            id.append("FD-0");
                        }
                    } else if (productLine.equals("Rice Pots")) {
                         if (model.getRowCount() < 10) {
                            id.append("RP-00");
                        }else{
                            id.append("RP-0");
                        }
                    } else if (productLine.equals("Salads and Vegetables")) {
                         if (model.getRowCount() < 10) {
                            id.append("SV-00");
                        }else{
                            id.append("SV-0");
                        }
                        
                    } else if (productLine.equals("Shaopao")) {
                         if (model.getRowCount() < 10) {
                            id.append("SH-00");
                        }else{
                            id.append("SH-0");
                        }
                    } else if (productLine.equals("Soups and Noodles")) {
                         if (model.getRowCount() < 10) {
                            id.append("SN-00");
                        }else{
                           id.append("SN-0");
                        }
                    } else if (productLine.equals("Steamed Dimsum")) {
                         if (model.getRowCount() < 10) {
                            id.append("SD-00");
                        }else{
                            id.append("SD-00");
                        } 
                    }else if(productLine.equals("Drinks")){
                        if (model.getRowCount() < 10) {
                            id.append("DR-00");
                        }else{
                            id.append("DR-0");
                        } 
                    }
                    id.append(model.getRowCount());
                    EditPanel popUp = new EditPanel(id.toString(), allProducts, table, index);
                } else {
                    EditPanel popUp = new EditPanel(model.getValueAt(index, 0).toString(), allProducts, table, index);
                }
                //System.out.println(products);
            }
        });
        return table;
    }

    /**
     * Create the GUI and show it. For thread safety, this method is invoked
     * from the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        MenuDisplay frame = new MenuDisplay();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();

            }
        });
    }
}
