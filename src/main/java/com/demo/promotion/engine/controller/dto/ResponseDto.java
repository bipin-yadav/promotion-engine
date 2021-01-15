package com.demo.promotion.engine.controller.dto;

public class ResponseDto {
    double total;

    public ResponseDto(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
