package projects.practice.jewelleryappintegrated.util;

public class OrderProduct {
    int jewellery_id , qty;
    double order_total;

    public OrderProduct(int jewellery_id, int qty, double order_total) {
        this.jewellery_id = jewellery_id;
        this.qty = qty;
        this.order_total = order_total;
    }

    public OrderProduct() {
    }

    public int getJewellery_id() {
        return jewellery_id;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "jewellery_id=" + jewellery_id +
                ", qty=" + qty +
                ", order_total=" + order_total +
                '}';
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

    public double getOrder_total() {
        return order_total;
    }

    public void setOrder_total(double order_total) {
        this.order_total = order_total;
    }
}
