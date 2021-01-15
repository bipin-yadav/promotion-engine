package com.demo.promotion.engine.service;

import com.demo.promotion.engine.controller.dto.RequestDto;
import com.demo.promotion.engine.controller.dto.ResponseDto;
import com.demo.promotion.engine.entities.Promotion;
import com.demo.promotion.engine.entities.Sku;
import com.demo.promotion.engine.repository.PromotionRepository;
import com.demo.promotion.engine.repository.SkuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

        return 0;
    }
}
