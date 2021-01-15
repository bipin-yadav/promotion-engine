package com.demo.promotion.engine.controller.dto;

import java.util.List;

public class RequestDto {

    List<SkuDetails> details;

    public static class SkuDetails {
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

    public List<SkuDetails> getDetails() {
        return details;
    }

    public void setDetails(List<SkuDetails> details) {
        this.details = details;
    }
}
