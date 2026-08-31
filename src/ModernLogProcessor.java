import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModernLogProcessor {
    public static void main(String[] args) {
        Path inputPath = Paths.get("transactions.txt");
        Path outputPath = Paths.get("failed_orders.txt");

        // Try-with-resources closes files automatically
        try (BufferedReader reader = Files.newBufferedReader(inputPath);
             BufferedWriter writer = Files.newBufferedWriter(outputPath)) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("FAILED")) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            System.out.println("Failed orders processed successfully using NIO.");

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
