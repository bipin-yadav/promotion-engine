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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class BundledItemTypePromotionRule extends AbstractPromotionRule {

    PromotionRepository promotionRepository;
    SkuRepository skuRepository;

    public BundledItemTypePromotionRule(PromotionRepository promotionRepository, SkuRepository skuRepository) {
        this.promotionRepository = promotionRepository;
        this.skuRepository = skuRepository;
    }

    @Override
    public void apply(List<RequestDto.SkuDetail> skuDetails, Price price) {
        if (!CollectionUtils.isEmpty(skuDetails)) {
            List<RequestDto.SkuDetail> combinedTypeList = getSkuDetails(skuDetails);
            combinedTypeEval(combinedTypeList, price);
        }
        applyNextRuleIfExist(skuDetails, price);
    }

    private List<RequestDto.SkuDetail> getSkuDetails(List<RequestDto.SkuDetail> skuDetails) {
        List<RequestDto.SkuDetail> combinedTypeList = new ArrayList<>();
        skuDetails.forEach(skuDetail -> {
            String skuId = skuDetail.getSkuId();
            Promotion promotion = promotionRepository.findBySkuId(skuId);
            if (promotion.getRule().contains("+")) {
                combinedTypeList.add(skuDetail);
            }
        });
        return combinedTypeList;
    }

    private void combinedTypeEval(List<RequestDto.SkuDetail> skuDetails, Price price) {
        double skuTotal = 0;
        Set<String> skipSkuIds = new HashSet<>();
        for(RequestDto.SkuDetail skuDetail : skuDetails) {
            Sku sku = skuRepository.findBySkuId(skuDetail.getSkuId());
            Promotion promotion = promotionRepository.findBySkuId(skuDetail.getSkuId());
            String[] split = promotion.getRule().split("=");
            double skuPrice  = Double.parseDouble(split[1]);
            String[] skuIds = split[0].split("\\+");
            String skuId1 = skuIds[0];
            String skuId2 = skuIds[1];
            if ((skipSkuIds.contains(skuId1) || skipSkuIds.contains(skuId2))) {
                continue;
            }
            if (combinedSkuPresent(skuId1, skuDetails) && combinedSkuPresent(skuId2, skuDetails)) {
                skuTotal = skuTotal + skuPrice;
                skipSkuIds.add(skuId1);
                skipSkuIds.add(skuId2);
            } else {
                skuTotal = skuTotal + sku.getSkuPrice();
            }
        }
        double priceTotal = price.getTotal();
        price.setTotal(priceTotal + skuTotal);
    }

    private boolean combinedSkuPresent(String skuId, List<RequestDto.SkuDetail> combinedType) {
        return combinedType.stream()
                .anyMatch(skuDetail -> skuDetail.getSkuId().equalsIgnoreCase(skuId));
    }
}
