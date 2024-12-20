package org.main.UAP;

import javax.swing.*;
import java.awt.*;

public class WarehouseSystem {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Warehouse Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JButton btnUpdate= new JButton("Update Item");
        JButton btnAddItem = new JButton("Add Item");
        JButton btnRemoveItem = new JButton("Remove Item");
        JButton btnReports = new JButton("Generate Reports");

        topPanel.add(btnAddItem);
        topPanel.add(btnUpdate);
        topPanel.add(btnRemoveItem);
        topPanel.add(btnReports);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        showAddItem showAddItem = new showAddItem();
        showInventory showInventory = new showInventory();

        JPanel inventoryPanel = (JPanel) showInventory.showInventoryPanel(frame);
        mainPanel.add(inventoryPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();


        btnAddItem.addActionListener(e -> {
            showAddItem.showAddItemDialog(frame);
        });

        // Action listener for removing an item (you need to implement the action)
        btnRemoveItem.addActionListener(e -> {
            // Show dialog for removing an item from the warehouse (to be implemented)
            JOptionPane.showMessageDialog(frame, "Remove Item functionality is not implemented yet.");
        });

        // Action listener for generating reports (you need to implement the action)
        btnReports.addActionListener(e -> {
            // Show dialog for generating reports (to be implemented)
            JOptionPane.showMessageDialog(frame, "Generate Reports functionality is not implemented yet.");
        });

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
