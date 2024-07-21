package com.example.receipt.service;

import com.example.receipt.model.Receipt;
import com.example.receipt.model.*;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
public class ReceiptService {
    private HashMap<String, Points> receipts = new HashMap<>();

    private UUID nextId = UUID.randomUUID();


    public Optional<Points> getReceiptById(String id) {
        return Optional.ofNullable(receipts.get(id));
    }

    public Id saveReceipt(Receipt receipt) {
        receipts.put(nextId.toString(), calculate(receipt));
        String t=nextId.toString();
        nextId=UUID.randomUUID();
        return new Id(t);
    }

    public Points calculate(Receipt receipt) {
        int totalPoints = 0;

        // 1. One point for every alphanumeric character in the retailer name
        totalPoints += countAlphanumericCharacters(receipt.getRetailer());

        // 2. 50 points if the total is a round dollar amount with no cents
        BigDecimal totalAmount = new BigDecimal(receipt.getTotal());
        if (totalAmount.stripTrailingZeros().scale() <= 0) {
            totalPoints += 50;
        }

        // 3. 25 points if the total is a multiple of 0.25
        if (totalAmount.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0) {
            totalPoints += 25;
        }

        // 4. 5 points for every two items on the receipt
        int numberOfItems = receipt.getItems().size();
        totalPoints += (numberOfItems / 2) * 5;

        // 5. Points for item description length and price
        for (Item item : receipt.getItems()) {
            String trimmedDescription = item.getShortDescription().trim();
            if (trimmedDescription.length() % 3 == 0) {
                BigDecimal price = new BigDecimal(item.getPrice());
                BigDecimal points = price.multiply(new BigDecimal("0.2")).setScale(0, RoundingMode.UP);
                totalPoints += points.intValue();
            }
        }

        // 6. 6 points if the day in the purchase date is odd
        String[] dateParts = receipt.getPurchaseDate().split("-");
        int day = Integer.parseInt(dateParts[2]);
        if (day % 2 != 0) {
            totalPoints += 6;
        }

        // 7. 10 points if the time of purchase is after 2:00pm and before 4:00pm
        String[] timeParts = receipt.getPurchaseTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        if (hour >= 14 && hour < 16) {
            totalPoints += 10;
        }

        return new Points(totalPoints);
    }

    private static int countAlphanumericCharacters(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                count++;
            }
        }
        return count;
    }

}
