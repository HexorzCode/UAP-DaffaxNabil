package org.main.UAP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.Timer;
import java.util.TimerTask;

public class showInventory {

    private long lastModifiedTime = 0; // Store the last modified time of the CSV file
    private Timer timer;

    public Component showInventoryPanel(JFrame frame) {
        String filePath = "dataWarehouse.csv"; // Path to your inventory CSV file
        DefaultTableModel tableModel = new DefaultTableModel();

        // Add columns to the table model
        tableModel.addColumn("Item Name");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");

        JTable table = new JTable(tableModel);  // Initialize the JTable
        JScrollPane scrollPane = new JScrollPane(table);  // Wrap JTable in JScrollPane

        // Create a panel to hold the table and scrollpane
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Clear any existing rows (in case the panel is reused)
        tableModel.setRowCount(0);

        // Read and load data from the CSV file
        readDataFromCSV(filePath, tableModel);

        // Set up a timer to check for file updates every 5 seconds
        startFileWatcher(filePath, tableModel, frame);

        return panel;
    }

    private void readDataFromCSV(String filePath, DefaultTableModel tableModel) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load inventory data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startFileWatcher(String filePath, DefaultTableModel tableModel, JFrame frame) {
        Path path = Paths.get(filePath);
        File file = new File(filePath);

        // Start a timer to check the file modification timestamp every 5 seconds
        if (timer == null) {
            timer = new Timer();
        }

        // Timer task to check for file updates
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long currentModifiedTime = file.lastModified();
                if (currentModifiedTime > lastModifiedTime) {
                    // File has been updated
                    lastModifiedTime = currentModifiedTime;

                    // Clear the previous table data and reload the CSV
                    SwingUtilities.invokeLater(() -> {
                        tableModel.setRowCount(0); // Clear the existing rows
                        readDataFromCSV(filePath, tableModel); // Reload data
                        frame.revalidate();
                        frame.repaint();
                    });
                }
            }
        }, 0, 100); // Check every 5000 milliseconds (5 seconds)
    }
}
