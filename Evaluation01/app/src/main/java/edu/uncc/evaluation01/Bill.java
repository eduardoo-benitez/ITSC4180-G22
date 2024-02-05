package edu.uncc.evaluation01;

import java.io.Serializable;

public class Bill implements Serializable {
    double billAmount;
    int tipPercent;
    double tipAmount;
    double total;

    public Bill (double billAmount, int tipPercent) {
        this.billAmount = billAmount;
        this.tipPercent = tipPercent;

        this.tipAmount = billAmount * ((double)this.tipPercent / 100);
        this.total = billAmount - this.tipAmount;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public int getTipPercent() {
        return tipPercent;
    }

    public double getTipAmount() {
        return tipAmount;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billAmount=" + billAmount +
                ", tipPercent=" + tipPercent +
                ", tipAmount=" + tipAmount +
                ", total=" + total +
                '}';
    }
}
