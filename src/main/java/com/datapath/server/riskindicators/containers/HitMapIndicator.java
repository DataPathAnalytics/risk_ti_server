package com.datapath.server.riskindicators.containers;

public class HitMapIndicator {

    private String indicator;
    private String indicatorCode;
    private String buyerName;
    private Integer tendersCount;

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Integer getTendersCount() {
        return tendersCount;
    }

    public void setTendersCount(Integer tendersCount) {
        this.tendersCount = tendersCount;
    }

    public String getIndicatorCode() {
        return indicatorCode;
    }

    public void setIndicatorCode(String indicatorCode) {
        this.indicatorCode = indicatorCode;
    }
}
