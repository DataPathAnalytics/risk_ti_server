package com.datapath.server.riskindicators.containers;

import java.util.List;

public class RiskResponse {

    private List<Risk> risks;

    public List<Risk> getRisks() {
        return risks;
    }

    public void setRisks(List<Risk> risks) {
        this.risks = risks;
    }
}
