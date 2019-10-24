package com.datapath.server.riskindicators.containers;

import java.util.List;

public class TendersCPVResponse {

    private List<TendersCPV> cpvs;

    public List<TendersCPV> getCpvs() {
        return cpvs;
    }

    public void setCpvs(List<TendersCPV> cpvs) {
        this.cpvs = cpvs;
    }
}
