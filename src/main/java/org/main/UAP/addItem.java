package org.main.UAP;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class addItem {
    private String itemName;
    private int quantity;
    private double price;
    private String imagePath;
    private String latestUpdate;

    // Constructor
    public addItem(String itemName, int quantity, double price, String imagePath) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.imagePath = imagePath;
        updateLatestUpdate();
    }

    // Getters and setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
        updateLatestUpdate();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
        updateLatestUpdate();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
        updateLatestUpdate();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        updateLatestUpdate();
    }

    public String getLatestUpdate() {
        return latestUpdate;
    }

    private void updateLatestUpdate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.latestUpdate = LocalDateTime.now().format(formatter);
    }

    @Override
    public String toString() {
        return String.format("Item Name: %s, Quantity: %d, Price: %.2f, Image Path: %s, Latest Update: %s",
                itemName, quantity, price, imagePath, latestUpdate);
    }

    // Method to save item data to CSV
    public void saveToCSV(String filePath) {
        if (itemName == null || itemName.isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }

        try (FileWriter writer = new FileWriter(filePath, true)) { // Append mode
            writer.append(itemName)
                    .append(',')
                    .append(String.valueOf(quantity))
                    .append(',')
                    .append(String.valueOf(price))
                    .append(',')
                    .append(imagePath.trim())
                    .append(',')
                    .append(latestUpdate)
                    .append('\n');
            System.out.printf("Data successfully saved to %s%n", filePath);
        } catch (IOException e) {
            System.err.printf("Error while saving to CSV: %s%n", e.getMessage());
        }
    }
}
