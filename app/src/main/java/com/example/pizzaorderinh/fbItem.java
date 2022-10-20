package com.example.pizzaorderinh;

public class fbItem {
    fbItem()
    {

    }
    String pImage,pName,pPrice;
    public fbItem(String pImage, String pName,String pPrice){
        this.pImage=pImage;
        this.pName=pName;
        this.pPrice=pPrice;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }
}
