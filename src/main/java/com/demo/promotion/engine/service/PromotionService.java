package com.demo.promotion.engine.service;

import com.demo.promotion.engine.controller.dto.RequestDto;
import com.demo.promotion.engine.controller.dto.ResponseDto;

public interface PromotionService {
    ResponseDto applyPromotion(RequestDto requestDto);
}
