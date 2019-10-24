package com.datapath.server.riskindicators.containers;

import java.util.List;

public class IndicatorsTendersRisksResponse {

    private List<IndicatorTendersRisks> indicators;

    public List<IndicatorTendersRisks> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<IndicatorTendersRisks> indicators) {
        this.indicators = indicators;
    }

}
