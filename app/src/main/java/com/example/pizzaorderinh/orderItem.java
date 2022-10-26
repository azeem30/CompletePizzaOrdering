package com.example.pizzaorderinh;

public class orderItem {
    public orderItem(){}
    String orderI, orderN, orderP, orderQ, orderTotalCost;

    public orderItem(String orderI, String orderN, String orderP, String orderQ, String orderTotalCost) {
        this.orderI = orderI;
        this.orderN = orderN;
        this.orderP = orderP;
        this.orderQ = orderQ;
        this.orderTotalCost = orderTotalCost;
    }

    public String getOrderI() {
        return orderI;
    }

    public void setOrderI(String orderI) {
        this.orderI = orderI;
    }

    public String getOrderN() {
        return orderN;
    }

    public void setOrderN(String orderN) {
        this.orderN = orderN;
    }

    public String getOrderP() {
        return orderP;
    }

    public void setOrderP(String orderP) {
        this.orderP = orderP;
    }

    public String getOrderQ() {
        return orderQ;
    }

    public void setOrderQ(String orderQ) {
        this.orderQ = orderQ;
    }

    public String getOrderTotalCost() {
        return orderTotalCost;
    }

    public void setOrderTotalCost(String orderTotalCost) {
        this.orderTotalCost = orderTotalCost;
    }
}
