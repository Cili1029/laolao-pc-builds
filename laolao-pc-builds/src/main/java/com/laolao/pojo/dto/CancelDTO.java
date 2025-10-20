package com.laolao.pojo.dto;

public class CancelDTO {
    /**
     * 订单号
     */
    private String number;

    /**
     * 取消原因
     */
    private String cancelReason;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public CancelDTO() {
    }

    public CancelDTO(String number, String cancelReason) {
        this.number = number;
        this.cancelReason = cancelReason;
    }

    @Override
    public String toString() {
        return "CancelDTO{" +
                "number='" + number + '\'' +
                ", cancelReason='" + cancelReason + '\'' +
                '}';
    }
}

