package com.demo.promotion.engine.service;

import com.demo.promotion.engine.controller.dto.RequestDto;
import com.demo.promotion.engine.service.domain.Price;

import java.util.List;

public abstract class AbstractPromotionRule implements PromotionRule {
    protected PromotionRule nextRule;

    public void setNextRule(PromotionRule nextRule) {
        this.nextRule = nextRule;
    }

    public void applyNextRuleIfExist(List<RequestDto.SkuDetail> skuDetails, Price total) {
        if (this.nextRule != null) {
            this.nextRule.apply(skuDetails, total);
        }
    }
}
