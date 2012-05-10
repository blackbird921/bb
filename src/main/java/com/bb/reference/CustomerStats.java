package com.bb.reference;

public class CustomerStats {
    Long customerId;
    int bonusTotal;
    int bonusRank;
    int checkinPercentage;
    int checkinTotal;
    int checkinRank;
    int commitTotal;
    int totalActiveCustomer;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public int getBonusTotal() {
        return bonusTotal;
    }

    public void setBonusTotal(int bonusTotal) {
        this.bonusTotal = bonusTotal;
    }

    public int getBonusRank() {
        return bonusRank;
    }

    public void setBonusRank(int bonusRank) {
        this.bonusRank = bonusRank;
    }

    public int getCheckinPercentage() {
        return checkinPercentage;
    }

    public void setCheckinPercentage(int checkinPercentage) {
        this.checkinPercentage = checkinPercentage;
    }

    public int getCheckinRank() {
        return checkinRank;
    }

    public void setCheckinRank(int checkinRank) {
        this.checkinRank = checkinRank;
    }

    public int getTotalActiveCustomer() {
        return totalActiveCustomer;
    }

    public void setTotalActiveCustomer(int totalActiveCustomer) {
        this.totalActiveCustomer = totalActiveCustomer;
    }

    public int getCheckinTotal() {
        return checkinTotal;
    }

    public void setCheckinTotal(int checkinTotal) {
        this.checkinTotal = checkinTotal;
    }

    public int getCommitTotal() {
        return commitTotal;
    }

    public void setCommitTotal(int commitTotal) {
        this.commitTotal = commitTotal;
    }
}
