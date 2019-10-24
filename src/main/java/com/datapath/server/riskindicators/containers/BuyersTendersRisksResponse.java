package com.datapath.server.riskindicators.containers;

import java.util.List;

public class BuyersTendersRisksResponse {

    private List<BuyerTendersRisks> buyers;

    public List<BuyerTendersRisks> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<BuyerTendersRisks> buyers) {
        this.buyers = buyers;
    }
}
