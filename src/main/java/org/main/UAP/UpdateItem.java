package org.main.UAP;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UpdateItem {

    public static void updateRowInCSV(String filePath, String searchTerm, String[] newRowValues) {
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length > 0 && fields[0].equals(searchTerm)) {
                    updatedLines.add(String.join(",", newRowValues));
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading the file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to the file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void showUpdateDialog(String filePath, String searchTerm, String price, String quantity) {
        JFrame frame = new JFrame();
        JDialog dialog = new JDialog(frame, "Update Item", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblItemName = new JLabel(STR."Item Name: \{searchTerm}");

        JLabel lblQuantity = new JLabel("Quantity:");
        JTextField txtQuantity = new JTextField(15);

        JLabel lblPrice = new JLabel("Price:");
        JTextField txtPrice = new JTextField(15);

        JLabel lblImage = new JLabel("Image:");
        JTextField txtImage = new JTextField(15);
        txtImage.setEditable(false);
        JButton btnChooseImage = getChooseImageButton(dialog, txtImage);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(lblItemName, gbc);

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
            try {
                String quantityText = txtQuantity.getText().trim();
                String priceText = txtPrice.getText().trim();
                String imagePath = txtImage.getText().trim();
                int inputQuantity = quantityText.isEmpty() ? Integer.parseInt(quantity) : Integer.parseInt(quantityText);
                double inputPrice = priceText.isEmpty() ? Double.parseDouble(price) : Double.parseDouble(priceText);
                String latestUpdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                if (imagePath.isEmpty()) {
                    imagePath = " ";
                }

                String[] newRowValues = {
                        searchTerm,
                        String.valueOf(inputQuantity),
                        String.valueOf(inputPrice),
                        imagePath,
                        latestUpdate
                };

                updateRowInCSV(filePath, searchTerm, newRowValues);
                JOptionPane.showMessageDialog(dialog, "Item updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Quantity and Price must be numeric!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "An unexpected error occurred!", "Error", JOptionPane.ERROR_MESSAGE);
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

    private static JButton getChooseImageButton(JDialog dialog, JTextField txtImage) {
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
