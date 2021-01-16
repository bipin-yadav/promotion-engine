package com.demo.promotion.engine.controller;

import com.demo.promotion.engine.Application;
import com.demo.promotion.engine.repository.PromotionRepository;
import com.demo.promotion.engine.repository.SkuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class PromotionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @BeforeEach
    public void beforeEach() {
        promotionRepository.deleteAll();
        skuRepository.deleteAll();
    }

    @Test
    public void shouldTestScenario1() throws Exception {
        createSkuIds();
        createPromotions();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/promotions/actions/eval")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"details\": [\n" +
                        "        {\n" +
                        "            \"skuId\": \"A\",\n" +
                        "            \"quantity\": \"1\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"skuId\": \"B\",\n" +
                        "            \"quantity\": \"1\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"skuId\": \"C\",\n" +
                        "            \"quantity\": \"1\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertEquals("{\"total\":100.0}", contentAsString);
    }

    private void createSkuIds() throws Exception {
        adddSkuId("    \"skuId\": \"A\",\n", "    \"skuPrice\": \"50\"\n");
        adddSkuId("    \"skuId\": \"B\",\n", "    \"skuPrice\": \"30\"\n");
        adddSkuId("    \"skuId\": \"C\",\n", "    \"skuPrice\": \"20\"\n");
        adddSkuId("    \"skuId\": \"D\",\n", "    \"skuPrice\": \"15\"\n");
    }

    @Test
    public void shouldTestScenario2() throws Exception {
        createSkuIds();
        createPromotions();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/promotions/actions/eval")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"details\": [\n" +
                        "        {\n" +
                        "            \"skuId\": \"A\",\n" +
                        "            \"quantity\": \"5\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"skuId\": \"B\",\n" +
                        "            \"quantity\": \"5\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"skuId\": \"C\",\n" +
                        "            \"quantity\": \"1\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertEquals("{\"total\":370.0}", contentAsString);
    }

    @Test
    public void shouldTestScenario3() throws Exception {
        createSkuIds();
        createPromotions();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/promotions/actions/eval")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"details\": [\n" +
                        "        {\n" +
                        "            \"skuId\": \"A\",\n" +
                        "            \"quantity\": \"3\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"skuId\": \"B\",\n" +
                        "            \"quantity\": \"5\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"skuId\": \"C\",\n" +
                        "            \"quantity\": \"1\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"skuId\": \"D\",\n" +
                        "            \"quantity\": \"1\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertEquals("{\"total\":280.0}", contentAsString);
    }

    private void createPromotions() throws Exception {
        addPromotion("/promotions", "    \"skuId\": \"A\",\n", "    \"rule\": \"3A=130\"\n");
        addPromotion("/promotions", "    \"skuId\": \"B\",\n", "    \"rule\": \"2B=45\"\n");
        addPromotion("/promotions", "    \"skuId\": \"C\",\n", "    \"rule\": \"C+D=30\"\n");
        addPromotion("/promotions", "    \"skuId\": \"D\",\n", "    \"rule\": \"C+D=30\"\n");
    }

    private void addPromotion(String s, String s2, String s3) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(s)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        s2 +
                        s3 +
                        "}")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    private void adddSkuId(String s, String s2) throws Exception {
        addPromotion("/skus", s, s2);
    }

}