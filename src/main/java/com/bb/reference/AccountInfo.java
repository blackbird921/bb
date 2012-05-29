package com.bb.reference;

import com.bb.domain.CustomerProduct;
import com.bb.domain.CustomerProfit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountInfo {
    private float balance;
    private List<AccountInfoItem> items = new ArrayList<AccountInfoItem>();

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public List<AccountInfoItem> getItems() {
        return items;
    }

    public void setItems(List<AccountInfoItem> items) {
        this.items = items;
    }
}
