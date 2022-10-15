package com.example.pizzaorderinh;

public class fbItem {
    fbItem()
    {

    }
    String pImage,pName;
    public fbItem(String pImage, String pName){
        this.pImage=pImage;
        this.pName=pName;
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
}
