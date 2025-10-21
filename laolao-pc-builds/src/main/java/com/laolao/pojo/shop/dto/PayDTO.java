package com.laolao.pojo.shop.dto;

public class PayDTO {
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PayDTO() {
    }

    public PayDTO(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PayDTO{" +
                "number='" + number + '\'' +
                '}';
    }
}
