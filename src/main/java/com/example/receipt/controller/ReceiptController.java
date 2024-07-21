package com.example.receipt.controller;

import com.example.receipt.model.*;
import com.example.receipt.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@RequestMapping()
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    @GetMapping("/api/receipts/{id}")
    public ResponseEntity<Points> getReceiptById(@PathVariable String id) {
        Optional<Points> receipt = receiptService.getReceiptById(id);
        return receipt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/receipts/process")
    public Id createReceipt(@RequestBody Receipt receipt) {
        return receiptService.saveReceipt(receipt);
    }
}
