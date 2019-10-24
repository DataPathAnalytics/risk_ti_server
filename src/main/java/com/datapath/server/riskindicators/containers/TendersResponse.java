package com.datapath.server.riskindicators.containers;

import java.util.List;

public class TendersResponse {

    private Integer pages;
    private List<Tender> tenders;

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<Tender> getTenders() {
        return tenders;
    }

    public void setTenders(List<Tender> tenders) {
        this.tenders = tenders;
    }
}
