package com.resrfullapi.api.testing.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class StockModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String productName;
    private double productPrice;
    private String warehouseName;
    private int productQuantity;

    public StockModel() {}

    public StockModel(int id, String productName, double productPrice, String warehouseName, int productQuantity) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.warehouseName = warehouseName;
        this.productQuantity = productQuantity;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getProductPrice() { return productPrice; }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }

    public int getProductQuantity() { return productQuantity; }
    public void setProductQuantity(int productQuantity) { this.productQuantity = productQuantity; }
}
