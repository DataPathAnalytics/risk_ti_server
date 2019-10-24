package com.datapath.server.riskindicators.containers;

public class TenderType {

    private String procurementMethodType;
    private Integer count;

    public String getProcurementMethodType() {
        return procurementMethodType;
    }

    public void setProcurementMethodType(String procurementMethodType) {
        this.procurementMethodType = procurementMethodType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
