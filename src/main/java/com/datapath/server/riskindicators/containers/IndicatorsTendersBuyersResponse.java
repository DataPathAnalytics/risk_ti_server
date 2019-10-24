package com.datapath.server.riskindicators.containers;

import java.util.List;

public class IndicatorsTendersBuyersResponse {

    private List<IndicatorTendersBuyers> indicators;

    public List<IndicatorTendersBuyers> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<IndicatorTendersBuyers> indicators) {
        this.indicators = indicators;
    }
}
