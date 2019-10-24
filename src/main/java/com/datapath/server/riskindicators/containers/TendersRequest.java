package com.datapath.server.riskindicators.containers;

import java.util.List;

public class TendersRequest {

    private Integer page;
    private List<Integer> regions;
    private List<Integer> buyers;
    private List<Integer> risksSum;
    private String date;
    private Long minAmount;
    private Long maxAmount;
    private List<String> procurentMethodTypes;
    private List<String> cpv;
    private List<String> indicatorId;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getRegions() {
        return regions;
    }

    public void setRegions(List<Integer> regions) {
        this.regions = regions;
    }

    public List<Integer> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<Integer> buyers) {
        this.buyers = buyers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Long minAmount) {
        this.minAmount = minAmount;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<String> getProcurentMethodTypes() {
        return procurentMethodTypes;
    }

    public void setProcurentMethodTypes(List<String> procurentMethodTypes) {
        this.procurentMethodTypes = procurentMethodTypes;
    }

    public List<String> getCpv() {
        return cpv;
    }

    public void setCpv(List<String> cpv) {
        this.cpv = cpv;
    }

    public List<String> getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(List<String> indicatorId) {
        this.indicatorId = indicatorId;
    }

    public List<Integer> getRisksSum() {
        return risksSum;
    }

    public void setRisksSum(List<Integer> risksSum) {
        this.risksSum = risksSum;
    }
}
