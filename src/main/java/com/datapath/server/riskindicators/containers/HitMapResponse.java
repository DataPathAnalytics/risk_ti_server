package com.datapath.server.riskindicators.containers;

import java.util.List;

public class HitMapResponse {

    private List<HitMapIndicator> indicators;

    public List<HitMapIndicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<HitMapIndicator> indicators) {
        this.indicators = indicators;
    }
}
