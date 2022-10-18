package com.example.pizzaorderinh;

public class menuItem {
    String pgImage,pgName,pgPrice;
    public menuItem()
    {

    }

    public menuItem(String pgImage, String pgName, String pgPrice) {
        this.pgImage = pgImage;
        this.pgName = pgName;
        this.pgPrice = pgPrice;
    }

    public String getPgImage() {
        return pgImage;
    }

    public void setPgImage(String pgImage) {
        this.pgImage = pgImage;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getPgPrice() {
        return pgPrice;
    }

    public void setPgPrice(String pgPrice) {
        this.pgPrice = pgPrice;
    }
}
