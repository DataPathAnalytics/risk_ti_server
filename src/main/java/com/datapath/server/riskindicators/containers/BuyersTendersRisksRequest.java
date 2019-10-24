package com.datapath.server.riskindicators.containers;

import java.util.Collection;
import java.util.List;

public class BuyersTendersRisksRequest {

    private List<String> procurentMethodTypes;
    private List<String> indicatorId;

    public List<String> getProcurentMethodTypes() {
        return procurentMethodTypes;
    }

    public void setProcurentMethodTypes(List<String> procurentMethodTypes) {
        this.procurentMethodTypes = procurentMethodTypes;
    }

    public List<String> getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(List<String> indicatorId) {
        this.indicatorId = indicatorId;
    }
}
