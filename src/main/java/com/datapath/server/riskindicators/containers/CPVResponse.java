package com.datapath.server.riskindicators.containers;

import java.util.List;

public class CPVResponse {

    private List<CPV> cpvs;

    public List<CPV> getCpvs() {
        return cpvs;
    }

    public void setCpvs(List<CPV> cpvs) {
        this.cpvs = cpvs;
    }
}
