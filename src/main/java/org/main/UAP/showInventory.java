package org.main.UAP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class showInventory {

    private long lastModifiedTime = 0;
    private Timer timer;

    public Component showInventoryPanel(JFrame frame) {
        String filePath = "dataWarehouse.csv";
        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("Item Name");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price");
        tableModel.addColumn("Image");
        tableModel.addColumn("Latest Update");
        tableModel.addColumn("Actions");

        JPanel panel = getjPanel(tableModel);

        tableModel.setRowCount(0);

        readDataFromCSV(filePath, tableModel);

        startFileWatcher(filePath, tableModel);

        return panel;
    }

    private static JPanel getjPanel(DefaultTableModel tableModel) {
        JTable table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 3) {
                    return ImageIcon.class;
                } else if (column == 4) {
                    return JButton.class;
                }
                return super.getColumnClass(column);
            }

            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        JTableHeader header = table.getTableHeader();

        table.setRowHeight(70);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        header.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        header.setBackground(new Color(63, 81, 181));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 32));

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);


        table.getColumn("Actions").setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            JButton updateButton = new JButton("Update");
            JButton removeButton = new JButton("Remove");

            updateButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Update button clicked for row " + row));
            removeButton.addActionListener(e -> ((DefaultTableModel) table1.getModel()).removeRow(row));

            buttonPanel.add(updateButton);
            buttonPanel.add(removeButton);

            if (isSelected) {
                buttonPanel.setBackground(table1.getSelectionBackground());
            } else {
                buttonPanel.setBackground(table1.getBackground());
            }

            return buttonPanel;
        });

        table.getColumn("Actions").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
                JButton updateButton = new JButton("Update");
                JButton removeButton = new JButton("Remove");

                String price = table.getValueAt(row,2).toString();
                String quantity = table.getValueAt(row, 1).toString();

                RemoveItem removeItem = new RemoveItem();

                updateButton.addActionListener(e -> UpdateItem.showUpdateDialog("dataWarehouse.csv",table.getValueAt(row,0).toString(),price,quantity));
                removeButton.addActionListener(e ->removeItem.removeRowFromCSV("dataWarehouse.csv",table.getValueAt(row,0).toString()));

                buttonPanel.add(updateButton);
                buttonPanel.add(removeButton);
                return buttonPanel;
            }
        });

        return panel;
    }

    private void readDataFromCSV(String filePath, DefaultTableModel tableModel) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String itemName = data[0];
                    String quantity = data[1];
                    String price = data[2];
                    String imagePath = data[3].trim();
                    String latest = data[4];

                    ImageIcon imageIcon = null;
                    if (!imagePath.isEmpty()) {
                        File imageFile = new File(imagePath);
                        if (imageFile.exists()) {
                            imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(45, 45, Image.SCALE_AREA_AVERAGING));
                        }
                    }

                    tableModel.addRow(new Object[]{itemName, quantity, price, imageIcon, latest,null});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load inventory data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startFileWatcher(String filePath, DefaultTableModel tableModel) {
        File file = new File(filePath);
        if (timer == null) {
            timer = new Timer();
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long currentModifiedTime = file.lastModified();
                if (currentModifiedTime > lastModifiedTime) {
                    lastModifiedTime = currentModifiedTime;
                    SwingUtilities.invokeLater(() -> {
                        tableModel.setRowCount(0);
                        readDataFromCSV(filePath, tableModel);
                    });
                }
            }
        }, 0, 100);
    }
}
