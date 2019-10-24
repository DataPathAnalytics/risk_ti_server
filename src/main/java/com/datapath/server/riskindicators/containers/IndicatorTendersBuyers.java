package com.datapath.server.riskindicators.containers;

public class IndicatorTendersBuyers {

    private Integer id;
    private String code;
    private String description;
    private Integer tendersCount;
    private Integer buyersCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTendersCount() {
        return tendersCount;
    }

    public void setTendersCount(Integer tendersCount) {
        this.tendersCount = tendersCount;
    }

    public Integer getBuyersCount() {
        return buyersCount;
    }

    public void setBuyersCount(Integer buyersCount) {
        this.buyersCount = buyersCount;
    }
}
