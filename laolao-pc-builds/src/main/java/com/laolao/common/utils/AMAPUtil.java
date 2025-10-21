package com.laolao.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.laolao.common.properties.AMAPProperties;
import com.laolao.pojo.shop.vo.DistrictVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class AMAPUtil {

    @Resource
    private AMAPProperties amapProperties;
    @Resource
    private WebClient.Builder webClientBuilder;

    private String getKey() {
        return amapProperties.getKey();
    }

    public List<DistrictVO> getDistrictByAdcade(Integer adcode) {
        String uri = UriComponentsBuilder.fromUriString("https://restapi.amap.com/v3/config/district")
                .queryParam("key", getKey())
                .queryParam("keywords", adcode)
                .queryParam("subdistrict", 1)
                .queryParam("extensions", "base")
                .toUriString();

        return getDistrictVO(uri);
    }

    public List<DistrictVO> getDistrictByName(String name) {
        String uri = UriComponentsBuilder.fromUriString("https://restapi.amap.com/v3/config/district")
                .queryParam("key", getKey())
                .queryParam("keywords", name)
                .queryParam("subdistrict", 1)
                .queryParam("extensions", "base")
                .build(false)
                .toUriString();

        return getDistrictVO(uri);
    }

    private List<DistrictVO> getDistrictVO(String uri) {
        return webClientBuilder.build().get()
                .uri(uri)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> {
                    JsonNode districtNode = json
                            .path("districts")
                            .path(0)
                            .path("districts");
                    List<DistrictVO> districtList = new ArrayList<>();
                    for (JsonNode district : districtNode) {
                        DistrictVO cityVO = new DistrictVO();
                        cityVO.setLevel(district.path("level").asText());
                        cityVO.setAdcode(district.path("adcode").asInt());
                        cityVO.setName(district.path("name").asText());
                        districtList.add(cityVO);
                    }
                    return districtList;
                })
                .block();
    }
}
