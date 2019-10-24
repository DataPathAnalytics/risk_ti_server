package com.datapath.server.riskindicators.containers;

import java.util.List;

public class IndicatorsTendersBuyersRequest {

    private List<Integer> buyersId;
    private List<String> procurentMethodTypes;
    private List<String> cpv;
    private List<String> indicatorId;
    private Integer minAmount;
    private Integer maxAmount;
    private String date;

    public List<Integer> getBuyersId() {
        return buyersId;
    }

    public void setBuyersId(List<Integer> buyersId) {
        this.buyersId = buyersId;
    }

    public List<String> getProcurentMethodTypes() {
        return procurentMethodTypes;
    }

    public void setProcurentMethodTypes(List<String> procurentMethodTypes) {
        this.procurentMethodTypes = procurentMethodTypes;
    }

    public Integer getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<String> getCpv() {
        return cpv;
    }

    public void setCpv(List<String> cpv) {
        this.cpv = cpv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(List<String> indicatorId) {
        this.indicatorId = indicatorId;
    }
}
