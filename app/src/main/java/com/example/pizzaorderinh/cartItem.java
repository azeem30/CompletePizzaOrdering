package com.example.pizzaorderinh;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class cartItem {
    cartItem(){}
    String quantity,cpImage,cpName,orderTotal;

    public cartItem(String quantity, String cpImage, String cpName, String orderTotal) {
        this.quantity = quantity;
        this.cpImage = cpImage;
        this.cpName = cpName;
        this.orderTotal = orderTotal;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCpImage() {
        return cpImage;
    }

    public void setCpImage(String cpImage) {
        this.cpImage = cpImage;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }
}
