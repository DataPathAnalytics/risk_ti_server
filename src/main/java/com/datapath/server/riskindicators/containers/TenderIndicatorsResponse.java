package com.datapath.server.riskindicators.containers;

import java.util.List;

public class TenderIndicatorsResponse {

    private List<TenderIndicator> indicators;

    public List<TenderIndicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<TenderIndicator> indicators) {
        this.indicators = indicators;
    }
}
