package com.datapath.server.riskindicators.containers;

public class Tender {

    private Integer id;
    private String tenderId;
    private Long amount;
    private String procurementMethod;
    private String procurementMethodType;
    private Integer risksCount;
    private Integer risksSum;
    private Integer buyerId;
    private String buyerName;
    private String region;
    private String cpv;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getProcurementMethod() {
        return procurementMethod;
    }

    public void setProcurementMethod(String procurementMethod) {
        this.procurementMethod = procurementMethod;
    }

    public String getProcurementMethodType() {
        return procurementMethodType;
    }

    public void setProcurementMethodType(String procurementMethodType) {
        this.procurementMethodType = procurementMethodType;
    }

    public Integer getRisksCount() {
        return risksCount;
    }

    public void setRisksCount(Integer risksCount) {
        this.risksCount = risksCount;
    }

    public Integer getRisksSum() {
        return risksSum;
    }

    public void setRisksSum(Integer risksSum) {
        this.risksSum = risksSum;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCpv() {
        return cpv;
    }

    public void setCpv(String cpv) {
        this.cpv = cpv;
    }
}
