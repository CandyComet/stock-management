package org.example;

public class StockModel {
    private int id;
    private String productName;
    private double productPrice;
    private String warehouseName;
    private int productQuantity;

    public StockModel(int id, String productName, double productPrice, String warehouseName, int productQuantity) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.warehouseName = warehouseName;
        this.productQuantity = productQuantity;
    }

    public int getId() { return id; }
    public String getProductName() { return productName; }
    public double getProductPrice() { return productPrice; }
    public String getWarehouseName() { return warehouseName; }
    public int getProductQuantity() { return productQuantity; }
}
