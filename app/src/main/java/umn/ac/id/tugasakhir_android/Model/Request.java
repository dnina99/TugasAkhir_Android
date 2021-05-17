package umn.ac.id.tugasakhir_android.Model;

import java.util.List;

public class Request {
    private String mobileNumber;
    private String name;
    private String address;
    private String total;

    private List<Order> foods;

    public Request() {
    }

    public Request(String mobileNumber, String name, String address, String total, List<Order> foods) {
        this.mobileNumber = mobileNumber;
        this.name = name;
        this.address = address;
        this.total = total;
        this.foods = foods;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
