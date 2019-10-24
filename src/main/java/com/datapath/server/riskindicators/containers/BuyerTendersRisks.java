package com.datapath.server.riskindicators.containers;

public class BuyerTendersRisks {


    private Integer id;
    private String name;
    private Integer tendersCount;
    private Integer risksCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTendersCount() {
        return tendersCount;
    }

    public void setTendersCount(Integer tendersCount) {
        this.tendersCount = tendersCount;
    }

    public Integer getRisksCount() {
        return risksCount;
    }

    public void setRisksCount(Integer risksCount) {
        this.risksCount = risksCount;
    }
}
