package com.datapath.server.riskindicators.containers;

import java.util.ArrayList;
import java.util.List;

public class IndicatorsTendersRisksRequest {

    private List<String> procurentMethodTypes;
    private List<Integer> buyersId;

    public IndicatorsTendersRisksRequest() {
        procurentMethodTypes = new ArrayList<>();
    }

    public List<String> getProcurentMethodTypes() {
        return procurentMethodTypes;
    }

    public void setProcurentMethodTypes(List<String> procurentMethodTypes) {
        this.procurentMethodTypes = procurentMethodTypes;
    }

    public List<Integer> getBuyersId() {
        return buyersId;
    }

    public void setBuyersId(List<Integer> buyersId) {
        this.buyersId = buyersId;
    }
}
