package org.main.UAP;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RemoveItem {
    public void removeRowFromCSV(String filePath, String searchTerm) {
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(searchTerm)) {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println(STR."Error reading the file: \{e.getMessage()}");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println(STR."Error writing to the file: \{e.getMessage()}");
        }
    }
}
