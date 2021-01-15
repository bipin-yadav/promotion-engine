package com.demo.promotion.engine.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "sku_id")
    private String skuId;

    @Column(name = "sku_price")
    private double skuPrice;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public double getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(double skuPrice) {
        this.skuPrice = skuPrice;
    }
}
