package com.bb.reference;

import com.bb.domain.CustomerProduct;
import com.bb.domain.CustomerProfit;

import java.util.Calendar;
import java.util.Date;

public class WeekStatus {
    private Date startDate;
    private Date endDate;
    private int daysCompleted;
    private int daysLeft;
    private int daysToComplete;
    private CustomerProduct customerProduct;
    private CustomerProfit customerProfit;


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDaysCompleted() {
        return daysCompleted;
    }

    public void setDaysCompleted(int daysCompleted) {
        this.daysCompleted = daysCompleted;
    }

    public int getDaysToComplete() {
        return daysToComplete;
    }

    public void setDaysToComplete(int daysToComplete) {
        this.daysToComplete = daysToComplete;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public CustomerProduct getCustomerProduct() {
        return customerProduct;
    }

    public void setCustomerProduct(CustomerProduct customerProduct) {
        this.customerProduct = customerProduct;
    }

    public CustomerProfit getCustomerProfit() {
        return customerProfit;
    }

    public void setCustomerProfit(CustomerProfit customerProfit) {
        this.customerProfit = customerProfit;
    }
}
