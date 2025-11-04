package org.example;

import java.util.List;

public class UserResponse {
    private boolean success;
    private String message;
    private List<StockModel> data;

    public UserResponse(boolean success, String message, List<StockModel> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<StockModel> getData() { return data; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
    public void setData(List<StockModel> data) { this.data = data; }
}
