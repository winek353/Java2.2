package uj.jwzp.w2.entity;

import uj.jwzp.w2.entity.Item;

import java.math.BigDecimal;
import java.util.List;

public class Transaction {
    int id;
    String timestamp;
    int customer_id;
    List<Item> items;
    BigDecimal sum;

    public Transaction(int id, String timestamp, int customer_id, List<Item> items) {
        this.id = id;
        this.timestamp = timestamp;
        this.customer_id = customer_id;
        this.items = items;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
