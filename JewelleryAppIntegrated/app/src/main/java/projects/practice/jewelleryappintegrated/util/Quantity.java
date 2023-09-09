package projects.practice.jewelleryappintegrated.util;

import java.io.Serializable;

public class Quantity implements Serializable {
    int quantity;
    int customer_id;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    double price;


    public Quantity() {
    }


    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }


    public Quantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Quantity{" +
                "quantity=" + quantity +
                '}';
    }
}
