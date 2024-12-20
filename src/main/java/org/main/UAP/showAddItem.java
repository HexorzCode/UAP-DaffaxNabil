package org.main.UAP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class showAddItem {
    public void showAddItemDialog(JFrame frame) {
        JDialog dialog = new JDialog(frame, "Add Item", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblItemName = new JLabel("Item Name:");
        JTextField txtItemName = new JTextField(15);

        JLabel lblQuantity = new JLabel("Quantity:");
        JTextField txtQuantity = new JTextField(15);

        JLabel lblPrice = new JLabel("Price:");
        JTextField txtPrice = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(lblItemName, gbc);

        gbc.gridx = 1;
        inputPanel.add(txtItemName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lblQuantity, gbc);

        gbc.gridx = 1;
        inputPanel.add(txtQuantity, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lblPrice, gbc);

        gbc.gridx = 1;
        inputPanel.add(txtPrice, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        btnSave.addActionListener(e -> {
            String itemName = txtItemName.getText();
            String quantityText = txtQuantity.getText();
            String priceText = txtPrice.getText();


            if (itemName.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);
                double price = Double.parseDouble(priceText);
                addItem addItem = new addItem(itemName,quantity,price);
                addItem.saveToCSV("dataWarehouse.csv");

                System.out.println(STR."Item saved: \{itemName}, Quantity: \{quantity}, Price: \{price}");
                dialog.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Quantity and Price must be numeric!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}
