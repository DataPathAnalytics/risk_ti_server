package com.datapath.server.riskindicators.containers;

import java.util.List;

public class TendersCPVRequest {

    private String date;
    private List<Integer> buyersId;
    private List<String> cpv;
    private List<String> indicatorId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Integer> getBuyersId() {
        return buyersId;
    }

    public void setBuyersId(List<Integer> buyersId) {
        this.buyersId = buyersId;
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
}
