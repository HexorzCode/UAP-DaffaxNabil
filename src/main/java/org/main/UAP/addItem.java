package org.main.UAP;

import java.io.FileWriter;
import java.io.IOException;

public class addItem {
    private String itemName;
    private int quantity;
    private double price;

    public addItem(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }


    @Override
    public String toString() {
        return STR."Item Name: \{itemName}, Quantity: \{quantity}, Price: \{price}";
    }

    // Method to save item data to CSV
    public void saveToCSV(String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) { // Append mode
            writer.append(itemName)
                    .append(',')
                    .append(String.valueOf(quantity))
                    .append(',')
                    .append(String.valueOf(price))
                    .append('\n');
            System.out.println(STR."Data successfully saved to \{filePath}");
        } catch (IOException e) {
            System.err.println(STR."Error while saving to CSV: \{e.getMessage()}");
        }
    }
}
