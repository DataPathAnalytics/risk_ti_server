package com.datapath.server.riskindicators.containers;

public class IndicatorTendersRisks {

    private Integer id;
    private String code;
    private String description;
    private Integer tendersCount;
    private Integer riskSum;

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

    public Integer getRiskSum() {
        return riskSum;
    }

    public void setRiskSum(Integer riskSum) {
        this.riskSum = riskSum;
    }

}