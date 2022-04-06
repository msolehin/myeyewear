package com.example.myeyewear;

public class Orders {

    private String oid,name,phone,address,date,time,payment,status,totalAmount;

    public Orders() {
    }

    public Orders(String oid,String name, String phone, String address, String date, String time, String payment, String status, String totalAmount) {
        this.name=oid;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.time = time;
        this.payment = payment;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
