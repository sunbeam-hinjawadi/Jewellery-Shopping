package projects.practice.jewelleryappintegrated.entity;

import java.io.Serializable;

public class CartProduct implements Serializable {
    private int customer_id ,jewellery_id , qty;
    private double price ;
    private String jewellery_name ;
    public CartProduct(){

    }

    public CartProduct(int customer_id, int jewellery_id, int qty, double price, String jewellery_name) {
        this.customer_id = customer_id;
        this.jewellery_id = jewellery_id;
        this.qty = qty;
        this.price = price;
        this.jewellery_name = jewellery_name;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getJewellery_id() {
        return jewellery_id;
    }

    public void setJewellery_id(int jewellery_id) {
        this.jewellery_id = jewellery_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getJewellery_name() {
        return jewellery_name;
    }

    public void setJewellery_name(String jewellery_name) {
        this.jewellery_name = jewellery_name;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "customer_id=" + customer_id +
                ", jewellery_id=" + jewellery_id +
                ", qty=" + qty +
                ", price=" + price +
                ", jewellery_name='" + jewellery_name + '\'' +
                '}';
    }
}
