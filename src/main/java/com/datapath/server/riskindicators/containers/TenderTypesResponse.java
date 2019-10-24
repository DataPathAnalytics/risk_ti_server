package com.datapath.server.riskindicators.containers;

import java.util.List;

public class TenderTypesResponse {

    private List<TenderType> types;

    public List<TenderType> getTypes() {
        return types;
    }

    public void setTypes(List<TenderType> types) {
        this.types = types;
    }
}
