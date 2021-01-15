package com.demo.promotion.engine.service;

import com.demo.promotion.engine.controller.dto.RequestDto;
import com.demo.promotion.engine.controller.dto.ResponseDto;
import com.demo.promotion.engine.entities.Promotion;
import com.demo.promotion.engine.entities.Sku;
import com.demo.promotion.engine.repository.PromotionRepository;
import com.demo.promotion.engine.repository.SkuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PromotionServiceImpl implements PromotionService {

    PromotionRepository promotionRepository;
    SkuRepository skuRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository, SkuRepository skuRepository) {
        this.promotionRepository = promotionRepository;
        this.skuRepository = skuRepository;
    }

    @Override
    public ResponseDto applyPromotion(RequestDto requestDto) {
        return new ResponseDto(execute(requestDto));
    }

    private double execute(RequestDto requestDto) {
        // put all logic here..
        List<RequestDto.SkuDetail> singleTypeList = new ArrayList<>();
        List<RequestDto.SkuDetail> combinedTypeList = new ArrayList<>();
        requestDto.getDetails().forEach(skuDetail -> {
            String skuId = skuDetail.getSkuId();
            Promotion promotion = promotionRepository.findBySkuId(skuId);
            if (!promotion.getRule().contains("+")) {
                singleTypeList.add(skuDetail);
            } else {
                combinedTypeList.add(skuDetail);
            }
        });
        // add pattern here
        double singleTypeEval = singleTypeEval(singleTypeList);
        double combinedTypeEval = combinedTypeEval(combinedTypeList);
        return singleTypeEval + combinedTypeEval;
    }

    private double combinedTypeEval(List<RequestDto.SkuDetail> combinedType) {
        double total = 0;
        Set<String> skipSkuIds = new HashSet<>();
        for(RequestDto.SkuDetail skuDetail : combinedType) {
            Sku sku = skuRepository.findBySkuId(skuDetail.getSkuId());
            Promotion promotion = promotionRepository.findBySkuId(skuDetail.getSkuId());
            String[] split = promotion.getRule().split("=");
            double price  = Double.parseDouble(split[1]);
            String[] skuIds = split[0].split("\\+");
            String skuId1 = skuIds[0];
            String skuId2 = skuIds[1];
            if ((skipSkuIds.contains(skuId1) || skipSkuIds.contains(skuId2))) {
                continue;
            }
            if (combinedSkuPresent(skuId1, combinedType) && combinedSkuPresent(skuId2, combinedType)) {
                total = total + price;
                skipSkuIds.add(skuId1);
                skipSkuIds.add(skuId2);
            } else {
                total = total + sku.getSkuPrice();
            }
        }
        return  total;
    }

    private boolean combinedSkuPresent(String skuId, List<RequestDto.SkuDetail> combinedType) {
        return combinedType.stream()
                .anyMatch(skuDetail -> skuDetail.getSkuId().equalsIgnoreCase(skuId));
    }

    private double singleTypeEval(List<RequestDto.SkuDetail> singleType) {
        double total = 0;
        for(RequestDto.SkuDetail skuDetail : singleType) {
            Sku sku = skuRepository.findBySkuId(skuDetail.getSkuId());
            Promotion promotion = promotionRepository.findBySkuId(skuDetail.getSkuId());
            String[] split = promotion.getRule().split("=");
            double price  = Double.parseDouble(split[1]);
            String[] skuSplit = split[0].split(skuDetail.getSkuId());
            int quantity = Integer.parseInt(skuSplit[0]);
            int nonPromotionalQuantity = skuDetail.getQuantity() % quantity;
            int promotionalUnit = skuDetail.getQuantity() / quantity;
            total = total + (promotionalUnit * price + nonPromotionalQuantity * sku.getSkuPrice());
        }
        return  total;
    }
}
