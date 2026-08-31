import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LegacyLogProcessor {
    public static void main(String[] args) {
        File inputFile = new File("transactions.txt");
        File outputFile = new File("failed_orders.txt");

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(inputFile));
            writer = new BufferedWriter(new FileWriter(outputFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("FAILED")) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            System.out.println("Failed orders processed using IO.");

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            // Must manually close streams to avoid memory/file locks
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.out.println("Failed to close files.");
            }
        }
    }
}
