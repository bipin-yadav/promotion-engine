package com.demo.promotion.engine.service;

import com.demo.promotion.engine.controller.dto.RequestDto;
import com.demo.promotion.engine.controller.dto.ResponseDto;
import com.demo.promotion.engine.service.domain.Price;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final NItemTypePromotionRule nItemTypePromotionRule;
    private final BundledItemTypePromotionRule bundledItemTypePromotionRule;

    public PromotionServiceImpl(NItemTypePromotionRule nItemTypePromotionRule, BundledItemTypePromotionRule bundledItemTypePromotionRule) {
        this.nItemTypePromotionRule = nItemTypePromotionRule;
        this.bundledItemTypePromotionRule = bundledItemTypePromotionRule;
    }

    @Override
    public ResponseDto applyPromotion(RequestDto requestDto) {
        return new ResponseDto(execute(requestDto.getDetails()));
    }

    private double execute(List<RequestDto.SkuDetail> skuDetails) {
        nItemTypePromotionRule.setNextRule(bundledItemTypePromotionRule);
        Price price = new Price();
        nItemTypePromotionRule.apply(skuDetails, price);
        return price.getTotal();
    }

}
