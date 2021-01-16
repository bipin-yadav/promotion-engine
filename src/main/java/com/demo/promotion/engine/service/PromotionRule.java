package com.demo.promotion.engine.service;

import com.demo.promotion.engine.controller.dto.RequestDto;
import com.demo.promotion.engine.service.domain.Price;

import java.util.List;

public interface PromotionRule {
    void setNextRule(PromotionRule nextRule);
    void apply(List<RequestDto.SkuDetail> skuDetails, Price price);
}
