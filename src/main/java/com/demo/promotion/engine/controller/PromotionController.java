package com.demo.promotion.engine.controller;

import com.demo.promotion.engine.controller.dto.RequestDto;
import com.demo.promotion.engine.controller.dto.ResponseDto;
import com.demo.promotion.engine.service.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromotionController {

    PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @RequestMapping(value = "/promotions/actions/eval",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<ResponseDto> promotionsEval(@RequestBody RequestDto requestDto) {
        ResponseDto responseDto = promotionService.applyPromotion(requestDto);
        return ResponseEntity
                .ok(responseDto);
    }


}
