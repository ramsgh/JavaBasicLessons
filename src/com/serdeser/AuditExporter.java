package com.serdeser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

// Object representing the live database record to be exported
class BankTransaction {
    private String txnId;
    private String timestamp;
    private String sourceAcc;
    private String destAcc;
    private double amount;
    private String status;

    public BankTransaction(String txnId, String timestamp, String sourceAcc, String destAcc, double amount, String status) {
        this.txnId = txnId;
        this.timestamp = timestamp;
        this.sourceAcc = sourceAcc;
        this.destAcc = destAcc;
        this.amount = amount;
        this.status = status;
    }

    // Converts object fields directly into a clean CSV row fragment
    public String toCsvRow() {
        return String.format("%s,%s,%s,%s,%.2f,%s",
                txnId, timestamp, sourceAcc, destAcc, amount, status);
    }
}

public class AuditExporter {
    public static void main(String[] args) {
        // Define the output export path using NIO Paths
        Path exportPath = Paths.get("daily_audit_export.csv");

        // Mock data representing database records fetched at EOD (End of Day)
        List<BankTransaction> databaseRecords = Arrays.asList(
                new BankTransaction("TXN-1001", "2026-08-31T14:22:10", "ACC-7741", "ACC-9902", 12500.00, "APPROVED"),
                new BankTransaction("TXN-1002", "2026-08-31T14:25:45", "ACC-3312", "ACC-1145", 850.50, "PENDING"),
                new BankTransaction("TXN-1003", "2026-08-31T14:31:02", "ACC-8854", "ACC-4412", 95000.00, "FLAGGED"),
                new BankTransaction("TXN-1004", "2026-08-31T14:35:19", "ACC-1299", "ACC-6631", 45.00, "REJECTED")
        );

        // Open buffered writer using NIO for optimal memory management during large file writes
        try (BufferedWriter writer = Files.newBufferedWriter(exportPath)) {

            // 1. Write CSV Column Headers required by auditing software
            writer.write("TransactionId,Timestamp,SourceAccount,DestinationAccount,Amount,Status");
            writer.newLine();

            // 2. Loop through records and write text rows sequentially
            for (BankTransaction txn : databaseRecords) {
                writer.write(txn.toCsvRow());
                writer.newLine(); // Move pointer to next flat line
            }

            System.out.println("Audit file successfully exported to: " + exportPath.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Critical System Alert: Export failed due to I/O error: " + e.getMessage());
        }
    }
}
