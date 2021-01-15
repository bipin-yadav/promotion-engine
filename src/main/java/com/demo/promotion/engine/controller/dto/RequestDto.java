package com.demo.promotion.engine.controller.dto;

import java.util.List;

public class RequestDto {

    List<SkuDetail> details;

    public static class SkuDetail {
        private String skuId;
        private int quantity;

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public List<SkuDetail> getDetails() {
        return details;
    }

    public void setDetails(List<SkuDetail> details) {
        this.details = details;
    }
}
