package org.main.UAP;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class showAddItem {
    public void showAddItemDialog(JFrame frame) {
        JDialog dialog = new JDialog(frame, "Add Item", true);
        dialog.setSize(400, 300);
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

        JLabel lblImage = new JLabel("Image:");
        JTextField txtImage = new JTextField(15);
        txtImage.setEditable(false);
        JButton btnChooseImage = getjButton(dialog, txtImage);

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

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(lblImage, gbc);

        gbc.gridx = 1;
        inputPanel.add(txtImage, gbc);

        gbc.gridx = 2;
        inputPanel.add(btnChooseImage, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        btnSave.addActionListener(e -> {
            String itemName = txtItemName.getText();
            String quantityText = txtQuantity.getText();
            String priceText = txtPrice.getText();
            String imagePath = txtImage.getText();

            if (itemName.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);
                double price = Double.parseDouble(priceText);

                addItem addItem = new addItem(itemName, quantity, price, imagePath);
                addItem.saveToCSV("dataWarehouse.csv");

                System.out.println("Item saved: " + itemName + ", Quantity: " + quantity + ", Price: " + price + ", Image: " + imagePath);
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

    private static JButton getjButton(JDialog dialog, JTextField txtImage) {
        JButton btnChooseImage = new JButton("Choose Image");

        btnChooseImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(dialog);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                txtImage.setText(selectedFile.getAbsolutePath());
            }
        });
        return btnChooseImage;
    }
}
