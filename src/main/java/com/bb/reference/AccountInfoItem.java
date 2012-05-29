package com.bb.reference;

import java.util.Comparator;
import java.util.Date;

public class AccountInfoItem {
    private Date timestamp;
    private String name;
    private float bonus;
    private float inAmount;
    private float outAmount;
    private float balance;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getInAmount() {
        return inAmount;
    }

    public void setInAmount(float inAmount) {
        this.inAmount = inAmount;
    }

    public float getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(float outAmount) {
        this.outAmount = outAmount;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }


    @Override
    public String toString() {
        return  timestamp+"";
    }

    public static class OldFirstComparator implements Comparator<AccountInfoItem>{
        @Override
        public int compare(AccountInfoItem o1, AccountInfoItem o2) {
            if (o1.getTimestamp().before(o2.getTimestamp())) {
                return -1;
            }else{
                return 1;
            }

        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }

    public static class NewFirstComparator implements Comparator<AccountInfoItem>{
        @Override
        public int compare(AccountInfoItem o1, AccountInfoItem o2) {
            if (o1.getTimestamp().before(o2.getTimestamp())) {
                return 1;
            }else{
                return -1;
            }

        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }

}
