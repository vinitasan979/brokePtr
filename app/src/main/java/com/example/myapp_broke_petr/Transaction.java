package com.example.myapp_broke_petr;


import java.text.DecimalFormat;
import java.util.Date;

public class Transaction {
    public Date date;
    public String product;
    public String category;
    public float amount;

    private static DecimalFormat df = new DecimalFormat("0.00");

    public Transaction(Date date, String product, String category, float amount) {
        this.date = date;
        this.product = product;
        this.category = category;
        this.amount = amount;
    }
}
