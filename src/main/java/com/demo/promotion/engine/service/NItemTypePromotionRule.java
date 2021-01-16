package com.demo.promotion.engine.service;

import com.demo.promotion.engine.controller.dto.RequestDto;
import com.demo.promotion.engine.entities.Promotion;
import com.demo.promotion.engine.entities.Sku;
import com.demo.promotion.engine.repository.PromotionRepository;
import com.demo.promotion.engine.repository.SkuRepository;
import com.demo.promotion.engine.service.domain.Price;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class NItemTypePromotionRule extends AbstractPromotionRule {

    PromotionRepository promotionRepository;
    SkuRepository skuRepository;

    public NItemTypePromotionRule(PromotionRepository promotionRepository, SkuRepository skuRepository) {
        this.promotionRepository = promotionRepository;
        this.skuRepository = skuRepository;
    }

    @Override
    public void apply(List<RequestDto.SkuDetail> skuDetails, Price price) {
        if (!CollectionUtils.isEmpty(skuDetails)) {
            List<RequestDto.SkuDetail> singleTypeList = getSkuDetails(skuDetails);
            singleTypeEval(singleTypeList, price);
        }
        applyNextRuleIfExist(skuDetails, price);
    }

    private List<RequestDto.SkuDetail> getSkuDetails(List<RequestDto.SkuDetail> skuDetails) {
        List<RequestDto.SkuDetail> singleTypeList = new ArrayList<>();
        skuDetails.forEach(skuDetail -> {
            String skuId = skuDetail.getSkuId();
            Promotion promotion = promotionRepository.findBySkuId(skuId);
            if (!promotion.getRule().contains("+")) {
                singleTypeList.add(skuDetail);
            }
        });
        return singleTypeList;
    }

    private void singleTypeEval(List<RequestDto.SkuDetail> skuDetails, Price price) {
        double skuTotal = 0;
        for (RequestDto.SkuDetail detail : skuDetails) {
            Sku sku = skuRepository.findBySkuId(detail.getSkuId());
            Promotion promotion = promotionRepository.findBySkuId(detail.getSkuId());
            String[] split = promotion.getRule().split("=");
            double skuPrice = Double.parseDouble(split[1]);
            String[] skuSplit = split[0].split(detail.getSkuId());
            int quantity = Integer.parseInt(skuSplit[0]);
            int nonPromotionalQuantity = detail.getQuantity() % quantity;
            int promotionalUnit = detail.getQuantity() / quantity;
            skuTotal = skuTotal + (promotionalUnit * skuPrice + nonPromotionalQuantity * sku.getSkuPrice());
        }
        double priceTotal = price.getTotal();
        price.setTotal(priceTotal + skuTotal);
    }
}
