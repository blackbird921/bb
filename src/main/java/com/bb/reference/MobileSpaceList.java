package com.bb.reference;

public class MobileSpaceList {
    //Boolean type
    private Object isOnVacation;
    private String avatarUrl;
    //com.bb.domain.CustomerProduct object
    private Object currentCustomerProduct;
    //com.bb.domain.CustomerProduct object
    private Object futureCustomerProduct;
    //com.bb.reference.WeekStatus object
    private Object weekStatus;

    public Object getOnVacation() {
        return isOnVacation;
    }

    public void setOnVacation(Object onVacation) {
        isOnVacation = onVacation;
    }

    public Object getCurrentCustomerProduct() {
        return currentCustomerProduct;
    }

    public void setCurrentCustomerProduct(Object currentCustomerProduct) {
        this.currentCustomerProduct = currentCustomerProduct;
    }

    public Object getWeekStatus() {
        return weekStatus;
    }

    public void setWeekStatus(Object weekStatus) {
        this.weekStatus = weekStatus;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Object getFutureCustomerProduct() {
        return futureCustomerProduct;
    }

    public void setFutureCustomerProduct(Object futureCustomerProduct) {
        this.futureCustomerProduct = futureCustomerProduct;
    }
}
