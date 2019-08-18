/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager_access;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jazin Mendoza
 */
public class EditPanel extends JDialog {

    private ArrayList<Product> products = new ArrayList<Product>();
    private String selectedID;
    private JPanel textPane = new JPanel();
    private JPanel imagePane = new JPanel();
    private Product product = null;
    private JPanel buttonPane = new JPanel();
    private JFileChooser fc = new JFileChooser();
    private JLabel imageLabel;
    private JTextField productIDText;
    private JTextField nameText;
    private JTextField lineText;
    private JTextField qtyText;
    private JTextField priceText;
    private FileInputStream img;
    private boolean changed = false;
    private JButton changeImage = new JButton("Change Image");
    private JTable table;
    private int row;

    public EditPanel(String selectedID, ArrayList<Product> products, JTable table, int row) {
        this.products = products;
        this.selectedID = selectedID;
        this.table = table;
        this.row = row;
        showPanel();
    }

    public void showPanel() {
        JDialog editPane = new JDialog(this, "", Dialog.ModalityType.DOCUMENT_MODAL);
        editPane.setBounds(450, 232, 500, 300);
        editPane.setAlwaysOnTop(true);
        showProductInfo();
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBackground(Color.red);
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.X_AXIS));
        imagePane.setLayout(new BorderLayout());
        imagePane.add(showImage(), BorderLayout.CENTER);
        imagePane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
        imagePane.add(changeImage, BorderLayout.SOUTH);
        changeImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int ret = fc.showSaveDialog(imagePane);

                changed = true;
                File file = fc.getSelectedFile();
                try {
                    changeImage(file);
                } catch (IOException ex) {
                    Logger.getLogger(EditPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        textPane.setLayout(new BoxLayout(textPane, BoxLayout.Y_AXIS));
        textPane.setMaximumSize(new Dimension(350, 500));
        textPane.setPreferredSize(new Dimension(350, 500));

        buttonPane.setPreferredSize(new Dimension(1000, 700));
        buttonPane.setMinimumSize(new Dimension(1000, 700));
        showButtons();
        mainPane.add(imagePane);
        mainPane.add(textPane);
        contentPane.add(mainPane);
        contentPane.add(buttonPane);
        editPane.getContentPane().add(contentPane);
        editPane.setVisible(true);
    }

    public Component showImage() {
        imageLabel = new JLabel();
        imageLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        if (product != null && product.getImage() != null) {
            ImageIcon image = null;
            try {
                image = new ImageIcon(product.getImage().getBytes(1L, ((int) product.getImage()
                        .length())));
            } catch (SQLException ex) {
                Logger.getLogger(EditPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            imageLabel.setIcon(image);
        } else {
            imageLabel.setText("No image");
            changeImage.setText("Add Image");
        }

        return imageLabel;
    }

    public void changeImage(File image) throws FileNotFoundException, IOException {
        imageLabel.revalidate();
        imageLabel.repaint();
        imageLabel.setText("");
        if (image != null) {
            img = new FileInputStream(image);
            imageLabel.setIcon(new ImageIcon(ImageIO.read(image)));
        } else {
            image = null;
        }

    }

    public void showProductInfo() {
        product = getProduct();
        if (product != null) {
            JPanel productIDPane = new JPanel();
            productIDPane.setLayout(new BoxLayout(productIDPane, BoxLayout.X_AXIS));
            productIDPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel productID = new JLabel("Product ID:            ");
            productID.setHorizontalAlignment(SwingConstants.LEADING);
            productIDText = new JTextField();
            productIDText.setText(selectedID);
            productIDText.setEnabled(false);
            productIDText.setForeground(Color.black);
            productIDPane.add(productID);
            productIDPane.add(productIDText);
            textPane.add(productIDPane);

            JPanel namePane = new JPanel();
            namePane.setLayout(new BoxLayout(namePane, BoxLayout.X_AXIS));
            namePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel name = new JLabel("Product Name:     ");
            nameText = new JTextField(product.getProductName());
            nameText.setForeground(Color.black);
            namePane.add(name);
            namePane.add(nameText);
            textPane.add(namePane);

            JPanel linePane = new JPanel();
            linePane.setLayout(new BoxLayout(linePane, BoxLayout.X_AXIS));
            linePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel line = new JLabel("Product Line:        ");
            lineText = new JTextField(product.getProductLine());
            nameText.setForeground(Color.black);
            linePane.add(line);
            linePane.add(lineText);
            textPane.add(linePane);

            JPanel qtyPane = new JPanel();
            qtyPane.setLayout(new BoxLayout(qtyPane, BoxLayout.X_AXIS));
            qtyPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel qty = new JLabel("Quantity In Stock: ");
            qtyText = new JTextField(Integer.toString(product.getQtyInStock()));
            qtyText.setForeground(Color.black);
            qtyPane.add(qty);
            qtyPane.add(qtyText);
            textPane.add(qtyPane);

            JPanel pricePane = new JPanel();
            pricePane.setLayout(new BoxLayout(pricePane, BoxLayout.X_AXIS));
            pricePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel price = new JLabel("Price:                      ");
            priceText = new JTextField(Double.toString(product.getPrice()));
            qtyText.setForeground(Color.black);
            pricePane.add(price);
            pricePane.add(priceText);
            textPane.add(pricePane);
        } else {
            System.out.println("NO PRODUCT");
            JPanel productIDPane = new JPanel();
            productIDPane.setLayout(new BoxLayout(productIDPane, BoxLayout.X_AXIS));
            productIDPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel productID = new JLabel("Product ID:            ");
            productID.setHorizontalAlignment(SwingConstants.LEADING);
            productIDText = new JTextField(selectedID);
            productIDText.setEnabled(false);
            productIDText.setForeground(Color.black);
            productIDPane.add(productID);
            productIDPane.add(productIDText);
            textPane.add(productIDPane);

            JPanel namePane = new JPanel();
            namePane.setLayout(new BoxLayout(namePane, BoxLayout.X_AXIS));
            namePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel name = new JLabel("Product Name:     ");
            nameText = new JTextField();
            nameText.setForeground(Color.black);
            namePane.add(name);
            namePane.add(nameText);
            textPane.add(namePane);

            JPanel linePane = new JPanel();
            linePane.setLayout(new BoxLayout(linePane, BoxLayout.X_AXIS));
            linePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel line = new JLabel("Product Line:        ");
            lineText = new JTextField();
            nameText.setForeground(Color.black);
            linePane.add(line);
            linePane.add(lineText);
            textPane.add(linePane);

            JPanel qtyPane = new JPanel();
            qtyPane.setLayout(new BoxLayout(qtyPane, BoxLayout.X_AXIS));
            qtyPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel qty = new JLabel("Quantity In Stock: ");
            qtyText = new JTextField();
            qtyText.setForeground(Color.black);
            qtyPane.add(qty);
            qtyPane.add(qtyText);
            textPane.add(qtyPane);

            JPanel pricePane = new JPanel();
            pricePane.setLayout(new BoxLayout(pricePane, BoxLayout.X_AXIS));
            pricePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel price = new JLabel("Price:                      ");
            priceText = new JTextField();
            qtyText.setForeground(Color.black);
            pricePane.add(price);
            pricePane.add(priceText);
            textPane.add(pricePane);
        }

    }

    public Product getProduct() {
        for (Product p : products) {
            if (p.getProductID().equals(selectedID)) {
                return p;
            }
        }
        return null;
    }

    public void showButtons() {
        JButton update = new JButton("Update");
        JButton delete = new JButton("Delete");
        JButton insert = new JButton("Insert");
        JPanel filler = new JPanel();
        filler.setPreferredSize(new Dimension(50, 10));
        filler.setMaximumSize(new Dimension(50, 10));
//        buttonPane.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 30));
        update.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product.setProductName(nameText.getText());
                product.setQtyInStock(Integer.parseInt(qtyText.getText()));
                product.setPrice(Double.parseDouble(priceText.getText()));
                Query query = new Query();
                query.updateProduct(product, img);
                ((DefaultTableModel) table.getModel()).setValueAt(nameText.getText(), row, 1);
                ((DefaultTableModel) table.getModel()).setValueAt(qtyText.getText(), row, 2);
                ((DefaultTableModel) table.getModel()).setValueAt(priceText.getText(), row, 3);
                dispose();
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Query query = new Query();
                query.deleteProduct(product);
                ((DefaultTableModel) table.getModel()).removeRow(row);
                dispose();
            }
        });

        insert.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                product = new Product();
                ((DefaultTableModel) table.getModel()).setValueAt(productIDText.getText(), row, 0);
                ((DefaultTableModel) table.getModel()).setValueAt(nameText.getText(), row, 1);
                ((DefaultTableModel) table.getModel()).setValueAt(qtyText.getText(), row, 2);
                ((DefaultTableModel) table.getModel()).setValueAt(priceText.getText(), row, 3);

                product.setProductID(selectedID);
                product.setProductLine(lineText.getText());
                product.setProductName(nameText.getText());
                product.setQtyInStock(Integer.parseInt(qtyText.getText()));
                product.setPrice(Double.parseDouble(priceText.getText()));
                Query query = new Query();
                query.addNewProduct(product, img);
                dispose();
            }
        });

        if (product == null) {
            buttonPane.add(insert);
        } else {
            buttonPane.add(update);
            buttonPane.add(filler);
            buttonPane.add(delete);
        }

    }

    public void addImage() {

    }

}
