package com.datapath.server.riskindicators.containers;

public class TendersCPV {

    private String cpv;
    private Integer tendersCount;
    private Integer risksCount;

    public String getCpv() {
        return cpv;
    }

    public void setCpv(String cpv) {
        this.cpv = cpv;
    }

    public Integer getTendersCount() {
        return tendersCount;
    }

    public void setTendersCount(Integer tendersCount) {
        this.tendersCount = tendersCount;
    }

    public void setRisksCount(Integer risksCount) {
        this.risksCount = risksCount;
    }

    public Integer getRisksCount() {
        return risksCount;
    }
}
