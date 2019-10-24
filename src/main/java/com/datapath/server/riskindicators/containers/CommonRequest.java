package com.datapath.server.riskindicators.containers;

import java.util.List;

public class CommonRequest {

    private List<String> indicatorId;
    private List<String> procurentMethodTypes;
    private List<String> buyers;
    private List<String> cpv;
    private List<String> regions;
    private List<Integer> risksSum;
    private String yearMonth;
    private String buyerNameSubstring;
    private Long minAmount;
    private Long maxAmount;

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

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public String getBuyerNameSubstring() {
        return buyerNameSubstring;
    }

    public void setBuyerNameSubstring(String buyerNameSubstring) {
        this.buyerNameSubstring = buyerNameSubstring;
    }

    public List<String> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<String> buyers) {
        this.buyers = buyers;
    }

    public List<String> getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(List<String> indicatorId) {
        this.indicatorId = indicatorId;
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

    public List<Integer> getRisksSum() {
        return risksSum;
    }

    public void setRisksSum(List<Integer> risksSum) {
        this.risksSum = risksSum;
    }
}
