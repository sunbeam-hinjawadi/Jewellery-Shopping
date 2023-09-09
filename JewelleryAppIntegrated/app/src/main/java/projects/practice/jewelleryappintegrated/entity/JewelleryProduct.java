package projects.practice.jewelleryappintegrated.entity;

import java.io.Serializable;

public class JewelleryProduct implements Serializable {
    private int jewellery_id;
    private int category_id;
    private String jewellery_name;
    private String jewellery_description;
    private String jewellery_image;
    private double jewellery_price;
    private int isAvailable;
    private int is_offer_available;
    private int offer_id;
    private int stock_qty;

    private int qty;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public JewelleryProduct() {
    }

    public JewelleryProduct(int jewellery_id, int category_id, String jewellery_name, String jewellery_description, String jewellery_image, double jewellery_price, int isAvailable, int is_offer_available, int offer_id, int stock_qty) {
        this.jewellery_id = jewellery_id;
        this.category_id = category_id;
        this.jewellery_name = jewellery_name;
        this.jewellery_description = jewellery_description;
        this.jewellery_image = jewellery_image;
        this.jewellery_price = jewellery_price;
        this.isAvailable = isAvailable;
        this.is_offer_available = is_offer_available;
        this.offer_id = offer_id;
        this.stock_qty = stock_qty;
    }

    public int getJewellery_id() {
        return jewellery_id;
    }

    public void setJewellery_id(int jewellery_id) {

        this.jewellery_id = jewellery_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getJewellery_name() {
        return jewellery_name;
    }

    public void setJewellery_name(String jewellery_name) {
        this.jewellery_name = jewellery_name;
    }

    public String getJewellery_description() {
        return jewellery_description;
    }

    public void setJewellery_description(String jewellery_description) {
        this.jewellery_description = jewellery_description;
    }

    public String getJewellery_image() {
        return jewellery_image;
    }

    public void setJewellery_image(String jewellery_image) {
        this.jewellery_image = jewellery_image;
    }

    public double getJewellery_price() {
        return jewellery_price;
    }

    public void setJewellery_price(double jewellery_price) {
        this.jewellery_price = jewellery_price;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getIs_offer_available() {
        return is_offer_available;
    }

    public void setIs_offer_available(int is_offer_available) {
        this.is_offer_available = is_offer_available;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }

    public int getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(int stock_qty) {
        this.stock_qty = stock_qty;
    }

    @Override
    public String toString() {
        return "JewelleryProduct{" +
                "jewellery_id=" + jewellery_id +
                ", category_id=" + category_id +
                ", jewellery_name='" + jewellery_name + '\'' +
                ", jewellery_description='" + jewellery_description + '\'' +
                ", jewellery_image='" + jewellery_image + '\'' +
                ", jewellery_price=" + jewellery_price +
                ", isAvailable=" + isAvailable +
                ", is_offer_available=" + is_offer_available +
                ", offer_id=" + offer_id +
                ", stock_qty=" + stock_qty +
                '}';
    }
}
