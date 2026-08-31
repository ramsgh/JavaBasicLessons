package com.serdeser;

import java.io.*;


public class SessionManager {
    public static void main(String[] args) {
        String filepath = "src/user_session.ser";
        ShoppingCart savedCart = new ShoppingCart("CUST_7702", 3, 149.99);

        // 1. SERIALIZATION: Saving the object to disk
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filepath))) {
            out.writeObject(savedCart);
            System.out.println("Session saved to disk: " + savedCart);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. DESERIALIZATION: Loading the object back into memory
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath))) {
            ShoppingCart loadedCart = (ShoppingCart) in.readObject();
            System.out.println("Session restored from disk: " + loadedCart);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

// The class MUST implement Serializable to be saved to disk
class ShoppingCart implements Serializable {
    // serialVersionUID ensures version compatibility during deserialization
    private static final long serialVersionUID = 1L;

    private String customerId;
    private int itemCount;
    private double totalAmount;

    public ShoppingCart(String customerId, int itemCount, double totalAmount) {
        this.customerId = customerId;
        this.itemCount = itemCount;
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Cart [Customer: " + customerId + ", Items: " + itemCount + ", Total: $" + totalAmount + "]";
    }
}