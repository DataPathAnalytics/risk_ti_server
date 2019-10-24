package com.datapath.server.riskindicators.containers;

import java.util.List;

public class IndicatorResponse {

    private List<Indicator> indicators;

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }
}
