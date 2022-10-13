package com.example.pizzaorderinh;

public class Item {

     String pizza;
     int img;

    public Item(String pizza, int img) {
        this.pizza = pizza;
        this.img = img;
    }

    public  String getPizza() {
        return pizza;
    }

    public void setPizza(String pizza) {
        this.pizza = pizza;
    }

    public  int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
