package com.example.zahidali.forecast_final.PojoClasses;

/**
 * Created by Itpvt on 23-Jan-18.
 */

public class Order_product_pojo {
    private String name;
    private String sku;

    public Order_product_pojo(String name, String sku) {
        this.name = name;
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
