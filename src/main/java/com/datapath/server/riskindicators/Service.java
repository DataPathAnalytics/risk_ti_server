package com.datapath.server.riskindicators;

import com.datapath.server.riskindicators.containers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class Service {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String filterString(CommonRequest request) {
        String buyerIdFilter = (isEmpty(request.getBuyers()) ? "" : "AND buyer_id IN (" + collectionToCommaDelimitedString(request.getBuyers()) + ") ");
        String buyerNameFilter = StringUtils.isEmpty(request.getBuyerNameSubstring()) ? "" : "AND buyer_name_id  LIKE '%" + request.getBuyerNameSubstring() + "%'";
        String cpvFilter = (isEmpty(request.getCpv()) ? "" : "AND SUBSTRING(cpv,0,3) IN (" + collectionToCommaDelimitedString(request.getCpv()) + ") ");
        String procurementMethodTypesFilter = (isEmpty(request.getProcurentMethodTypes()) ? "" : "AND procurement_method_type IN (" + collectionToCommaDelimitedString(request.getProcurentMethodTypes().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ") ");
        String indicatorsFilter = (isEmpty(request.getIndicatorId()) ? "" : "AND indicatorid IN (" + collectionToCommaDelimitedString(request.getIndicatorId().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ") ");
        String regionsFilter = (isEmpty(request.getRegions()) ? "" : "AND region_id  IN (" + collectionToCommaDelimitedString(request.getRegions()) + ") ");
        String minAmountFilter = (Objects.isNull(request.getMinAmount()) ? "" : "AND amount >" + request.getMinAmount());
        String maxAmountFilter = (Objects.isNull(request.getMaxAmount()) ? "" : "AND amount <" + request.getMaxAmount());
        String risksFilter = (isEmpty(request.getRisksSum()) ? "" : "AND risks_count IN (" + collectionToCommaDelimitedString(request.getRisksSum()) + ") ");

        return buyerNameFilter + cpvFilter + procurementMethodTypesFilter + indicatorsFilter + regionsFilter + buyerIdFilter + maxAmountFilter + minAmountFilter + risksFilter;
    }

    @RequestMapping(value = "/tenders/types", method = POST)
    public TenderTypesResponse getTenderTypes(@RequestBody TenderTypesRequest request) {
        String query = "SELECT t.procurement_method_type, COUNT(t.id) count\n" +
                "FROM tender t\n" +
                "JOIN tender_cpv cpv ON cpv.tender_id = t.id\n" +
                "WHERE t.id IN (\n" +
                "SELECT tender_id FROM risk_indicators WHERE TRUE " +
                (isEmpty(request.getIndicatorId()) ? "" : "AND indicatorid IN (" + collectionToCommaDelimitedString(request.getIndicatorId().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ")") +
                (isEmpty(request.getBuyersId()) ? "" : "AND buyer_id IN (" + collectionToCommaDelimitedString(request.getBuyersId()) + ") ") +
                (StringUtils.isEmpty(request.getDate()) ? "" : "AND substring(tenderid,4,7) = '" + request.getDate() + "'") +
                (isEmpty(request.getCpv()) ? "" : "AND substring(cpv.tender_cpv, 0,3) IN (" + collectionToCommaDelimitedString(request.getCpv().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ") ") +
                ")\n" +
                "GROUP BY procurement_method_type;\n";
        List<TenderType> types = jdbcTemplate.query(query, (rs, rowNum) -> {
            TenderType type = new TenderType();
            type.setProcurementMethodType(rs.getString("procurement_method_type"));
            type.setCount(rs.getInt("count"));
            return type;
        });
        TenderTypesResponse response = new TenderTypesResponse();
        response.setTypes(types);
        return response;
    }


    @RequestMapping(value = "/buyers", method = POST)
    public BuyersResponse getBuyers(@RequestBody CommonRequest request) {
        String query = "SELECT DISTINCT buyer_id id, buyer_name_id as name FROM risks_summary\n" +
                "WHERE TRUE " + filterString(request) + "ORDER BY buyer_name_id  LIMIT 15";

        List<Buyer> buyers = jdbcTemplate.query(query, (rs, rowNum) -> {
            Buyer buyer = new Buyer();
            buyer.setId(rs.getInt("id"));
            buyer.setName(rs.getString("name"));
            return buyer;
        });

        BuyersResponse response = new BuyersResponse();
        response.setBuyers(buyers);
        return response;
    }

    @RequestMapping(value = "/procurement-method-types", method = POST)
    public ProcurementMethodTypeResponse getProcurementMethodTypes(@RequestBody CommonRequest request) {
        String query = "SELECT DISTINCT procurement_method_type as procurementMethodType FROM risks_summary\n" +
                "WHERE TRUE " + filterString(request);

        List<ProcurementMethodType> procurementMethodTypes = jdbcTemplate.query(query, (rs, rowNum) -> {
            ProcurementMethodType procurementMethodType = new ProcurementMethodType();
            procurementMethodType.setProcurementMethodType(rs.getString("procurementMethodType"));
            return procurementMethodType;
        });
        ProcurementMethodTypeResponse response = new ProcurementMethodTypeResponse();
        response.setProcurementMethodTypes(procurementMethodTypes);
        return response;
    }

    @RequestMapping(value = "/risks/sum", method = POST)
    public RiskResponse getRisks(@RequestBody CommonRequest request) {
        String query = "SELECT DISTINCT risk_count FROM risks_summary\n" +
                "WHERE TRUE " + filterString(request);

        List<Risk> risks = jdbcTemplate.query(query, (rs, rowNum) -> {
            Risk risk = new Risk();
            risk.setSum(rs.getInt("risk_count"));
            return risk;
        });
        RiskResponse response = new RiskResponse();
        response.setRisks(risks);
        return response;
    }

    @RequestMapping(value = "/cpv", method = POST)
    public CPVResponse getCPV(@RequestBody CommonRequest request) {
        String query = "SELECT distinct(substring(cpv, 0,3)) cpv, cpvname FROM risks_summary\n" +
                "WHERE  cpv LIKE '%000000' " + filterString(request);
        List<CPV> cpvs = jdbcTemplate.query(query, (rs, rowNum) -> {
            CPV buyer = new CPV();
            buyer.setCode(rs.getString("cpv"));
            buyer.setName(rs.getString("cpvname"));
            return buyer;
        });

        CPVResponse response = new CPVResponse();
        response.setCpvs(cpvs);
        return response;
    }

    @RequestMapping(value = "/regions", method = POST)
    public RegionsResponse getRegions(@RequestBody CommonRequest request) {

        String query = "SELECT DISTINCT region_id id, region description FROM risks_summary\n" +
                "WHERE TRUE " + filterString(request) +
                "ORDER BY region";
        List<Region> regions = jdbcTemplate.query(query, (rs, rowNum) -> {
            Region region = new Region();
            region.setId(rs.getInt("id"));
            region.setName(rs.getString("description"));
            return region;
        });

        RegionsResponse response = new RegionsResponse();
        response.setRegions(regions);
        return response;
    }

    @RequestMapping(value = "/indicators", method = POST)
    public IndicatorResponse getIndicators(@RequestBody CommonRequest request) {
        String query = "SELECT DISTINCT indicatorid code, indicator_name description FROM risks_summary\n" +
                "WHERE TRUE " + filterString(request) +
                "ORDER BY code";

        List<Indicator> indicators = jdbcTemplate.query(query, (rs, rowNum) -> {
            Indicator indicator = new Indicator();
            indicator.setCode(rs.getString("code"));
            indicator.setDescription(rs.getString("description"));
            return indicator;
        });

        IndicatorResponse response = new IndicatorResponse();
        response.setIndicators(indicators);
        return response;

    }

    @RequestMapping(value = "/indicators/tendersrisks", method = POST)
    public IndicatorsTendersRisksResponse getIndicatorsTendersRisks(@RequestBody IndicatorsTendersRisksRequest request) {
        String query = "SELECT ti.id,ti.code,ti.description,COUNT(tender_id) tenders,SUM(risk) risks_sum\n" +
                "FROM risk_indicators ri\n" +
                "  JOIN transact_indicator ti ON ri.indicator_id = ti.id\n" +
                "  JOIN tender t ON ri.tender_id = t.id\n" +
                "WHERE TRUE " +
                (isEmpty(request.getProcurentMethodTypes()) ? "" : "AND t.procurement_method_type IN (" + collectionToCommaDelimitedString(request.getProcurentMethodTypes().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ") ") +
                (isEmpty(request.getBuyersId()) ? "" : "AND t.buyer_id IN (" + collectionToCommaDelimitedString(request.getBuyersId()) + ") ") +
                "GROUP BY ti.id\n" +
                "ORDER BY tenders DESC";

        List<IndicatorTendersRisks> indicators = jdbcTemplate.query(query, (rs, rowNum) -> {
            IndicatorTendersRisks indicator = new IndicatorTendersRisks();
            indicator.setId(rs.getInt("id"));
            indicator.setCode(rs.getString("code"));
            indicator.setDescription(rs.getString("description"));
            indicator.setTendersCount(rs.getInt("tenders"));
            indicator.setRiskSum(rs.getInt("risks_sum"));
            return indicator;
        });
        IndicatorsTendersRisksResponse response = new IndicatorsTendersRisksResponse();
        response.setIndicators(indicators);
        return response;
    }


    @RequestMapping(value = "/tenders", method = POST)
    public TendersResponse getTenders(@RequestBody TendersRequest request) {

        Integer pages = jdbcTemplate.queryForObject("SELECT CASE WHEN COUNT(*)%15 !=0 THEN COUNT(*)/15+1 ELSE COUNT(*)/15 END\n" +
                "FROM tender t\n" +
                "JOIN buyer b ON t.buyer_id = b.id\n" +
                "JOIN region r ON b.region_id = r.id\n" +
                "JOIN tender_cpv cpv ON cpv.tender_id = t.id\n" +
                "WHERE t.id IN (SELECT tender_id FROM risk_indicators)\n" +
                (isEmpty(request.getRegions()) ? "" : "AND region_id IN (" + collectionToCommaDelimitedString(request.getRegions()) + ") ") +
                (StringUtils.isEmpty(request.getDate()) ? "" : "AND substring(tenderid,4,7) = '" + request.getDate() + "'") +
                (Objects.isNull(request.getMinAmount()) ? "" : "AND t.amount >" + request.getMinAmount()) +
                (Objects.isNull(request.getMaxAmount()) ? "" : "AND t.amount <" + request.getMaxAmount()) +
                (isEmpty(request.getCpv()) ? "" : "AND substring(cpv.tender_cpv, 0,3) IN (" + collectionToCommaDelimitedString(request.getCpv().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ") ") +
                (StringUtils.isEmpty(request.getDate()) ? "" : "AND substring(t.tenderid,4,7) = '" + request.getDate() + "'") +
                (isEmpty(request.getProcurentMethodTypes()) ? "" : "AND t.procurement_method_type IN (" + collectionToCommaDelimitedString(request.getProcurentMethodTypes().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ") ") +
                (isEmpty(request.getIndicatorId()) ? "" : "AND t.id IN (SELECT tender_id FROM risk_indicators WHERE risk > 0 AND indicatorid IN (" + collectionToCommaDelimitedString(request.getIndicatorId().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ")\n" +
                        "GROUP BY tender_id\n" +
                        "HAVING count(*) = " + request.getIndicatorId().size() + ")\n") +

                (isEmpty(request.getBuyers()) ? "" : "AND t.buyer_id IN (" + collectionToCommaDelimitedString(request.getBuyers()) + ") "), Integer.class);

        String sql = "SELECT t.id, t.amount,t.procurement_method, t.procurement_method_type,t.buyer_id,t.tenderid,\n" +
                "(SELECT COUNT(tender_id) risks_count FROM risk_indicators WHERE tender_id = t.id),\n" +
                "(SELECT SUM(risk) risks_sum FROM risk_indicators WHERE tender_id = t.id),\n" +
                "b.name buyer_name, r.description region, cpv.tender_cpv cpv\n" +
                "FROM tender t\n" +
                "JOIN buyer b ON t.buyer_id = b.id\n" +
                "JOIN region r ON b.region_id = r.id\n" +
                "JOIN tender_cpv cpv ON cpv.tender_id = t.id\n" +
                "WHERE t.id IN (SELECT tender_id FROM risk_indicators)\n" +
                (isEmpty(request.getRegions()) ? "" : "AND region_id IN (" + collectionToCommaDelimitedString(request.getRegions()) + ") ") +
                (StringUtils.isEmpty(request.getDate()) ? "" : "AND substring(tenderid,4,7) = '" + request.getDate() + "'") +
                (Objects.isNull(request.getMinAmount()) ? "" : "AND t.amount >" + request.getMinAmount()) +
                (Objects.isNull(request.getMaxAmount()) ? "" : "AND t.amount <" + request.getMaxAmount()) +
                (isEmpty(request.getCpv()) ? "" : "AND substring(cpv.tender_cpv, 0,3) IN (" + collectionToCommaDelimitedString(request.getCpv().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ") ") +
                (StringUtils.isEmpty(request.getDate()) ? "" : "AND substring(t.tenderid,4,7) = '" + request.getDate() + "'") +
                (isEmpty(request.getIndicatorId()) ? "" : "AND t.id IN (SELECT tender_id FROM risk_indicators WHERE risk > 0 AND indicatorid IN (" + collectionToCommaDelimitedString(request.getIndicatorId().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ")\n" +
                        "GROUP BY tender_id\n" +
                        "HAVING count(*) = " + request.getIndicatorId().size() + ")\n") +
                (isEmpty(request.getProcurentMethodTypes()) ? "" : "AND t.procurement_method_type IN (" + collectionToCommaDelimitedString(request.getProcurentMethodTypes().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ") ") +
                (isEmpty(request.getBuyers()) ? "" : "AND t.buyer_id IN (" + collectionToCommaDelimitedString(request.getBuyers()) + ") ") +
                "ORDER BY t.id\n" +
                "OFFSET " + (request.getPage() == 1 ? 0 : (request.getPage() - 1) * 15) +
                " LIMIT 15";
        List<Tender> tenders = jdbcTemplate.query(sql, (rs, rowNum) -> {

            Tender tender = new Tender();
            tender.setId(rs.getInt("id"));
            tender.setTenderId(rs.getString("tenderid"));
            tender.setAmount(rs.getLong("amount"));
            tender.setProcurementMethod(rs.getString("procurement_method"));
            tender.setProcurementMethodType(rs.getString("procurement_method_type"));
            tender.setRisksCount(rs.getInt("risks_count"));
            tender.setRisksSum(rs.getInt("risks_sum"));
            tender.setBuyerId(rs.getInt("buyer_id"));
            tender.setBuyerName(rs.getString("buyer_name"));
            tender.setRegion(rs.getString("region"));
            tender.setCpv(rs.getString("cpv"));
            return tender;
        });

        TendersResponse response = new TendersResponse();
        response.setPages(pages);
        response.setTenders(tenders);
        return response;
    }


    @RequestMapping("/tenders/{tenderId}/indicators")
    public TenderIndicatorsResponse getTenderIndicators(@PathVariable Integer tenderId) {
        String query = "SELECT description ,risk FROM risk_indicators\n" +
                "  join transact_indicator on risk_indicators.indicator_id = transact_indicator.id WHERE tender_id = ?";
        List<TenderIndicator> indicators = jdbcTemplate.query(query, (rs, rowNum) -> {
            TenderIndicator indicator = new TenderIndicator();
            indicator.setIndicatorId(rs.getString("description"));
            indicator.setRisk(rs.getInt("risk"));
            return indicator;
        }, tenderId);

        TenderIndicatorsResponse response = new TenderIndicatorsResponse();
        response.setIndicators(indicators);
        return response;
    }

    @RequestMapping(value = "/buyers/tendersrisks", method = POST)
    public BuyersTendersRisksResponse getBuyersTendersRisks(@RequestBody BuyersTendersRisksRequest request) {

        String query = "select buyer_id id, (SELECT name FROM buyer WHERE id = tmp.buyer_id) , count(*) tenders_count, count(case when rcount > 0 then 1 end ) risks_count from (\n" +
                "SELECT i.buyer_id, count(DISTINCT indicatorid) icount, count(case when risk > 0 then 1 end) rcount\n" +
                "\n" +
                "  FROM risk_indicators i\n" +
                "                  JOIN tender t ON i.tender_id = t.id\n" +
                "                WHERE t.buyer_id IN (\n" +
                "                  SELECT buyer_id FROM risk_indicators\n" +
                "                  GROUP BY buyer_id\n" +
                "                  ORDER BY COUNT(tender_id) DESC\n" +
                "                  LIMIT 15\n" +
                "                )\n" +
                (isEmpty(request.getIndicatorId()) ? "" : "AND indicatorid IN (" + collectionToCommaDelimitedString(request.getIndicatorId().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ")") +

                (isEmpty(request.getProcurentMethodTypes()) ? "" :
                        "AND t.procurement_method_type IN (" + collectionToCommaDelimitedString(
                                request.getProcurentMethodTypes().stream()
                                        .map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ") ") +
                "\n" +
                "                GROUP BY i.buyer_id, tender_id) tmp\n" +
                "group by buyer_id\n" +
                "ORDER BY tenders_count DESC;";


        List<BuyerTendersRisks> buyers = jdbcTemplate.query(query, (rs, rowNum) -> {
            BuyerTendersRisks buyer = new BuyerTendersRisks();
            buyer.setId(rs.getInt("id"));
            buyer.setName(rs.getString("name"));
            buyer.setTendersCount(rs.getInt("tenders_count"));
            buyer.setRisksCount(rs.getInt("risks_count"));
            return buyer;
        });

        BuyersTendersRisksResponse response = new BuyersTendersRisksResponse();
        response.setBuyers(buyers);
        return response;
    }

    @RequestMapping(value = "/tenders/cpv", method = POST)
    public TendersCPVResponse getTendersCPV(@RequestBody TendersCPVRequest request) {
        String query = "select substring(tender_cpv,0,3) cpv, count(*) tenders_count, count(case when rcount > 0 then 1 end ) risks_count from (\n" +
                "SELECT tc.tender_cpv, count(DISTINCT indicatorid) icount, count(case when risk > 0 then 1 end) rcount\n" +
                "\n" +
                "  FROM risk_indicators i\n" +
                "                  JOIN tender t ON i.tender_id = t.id\n" +
                "                  join tender_cpv tc on tc.tender_id = t.id\n" +
                "WHERE TRUE\n" +
                (StringUtils.isEmpty(request.getDate()) ? "" : "AND substring(tenderid,4,7) = '" + request.getDate() + "'") +
                (isEmpty(request.getIndicatorId()) ? "" : "AND indicatorid IN (" + collectionToCommaDelimitedString(request.getIndicatorId().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ")") +
                (isEmpty(request.getBuyersId()) ? "" : "AND t.buyer_id IN (" + collectionToCommaDelimitedString(request.getBuyersId()) + ") ") +
                (isEmpty(request.getCpv()) ? "" : "AND substring(tc.tender_cpv, 0,3) IN (" + collectionToCommaDelimitedString(request.getCpv().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ") ") +
                "                GROUP BY tc.tender_cpv, tc.tender_id) tmp\n" +
                "group by cpv\n" +
                "ORDER BY tenders_count DESC;";


        List<TendersCPV> cpvs = jdbcTemplate.query(query, (rs, rowNum) -> {
            TendersCPV cpv = new TendersCPV();
            cpv.setCpv(rs.getString("cpv") + "000000");
            cpv.setTendersCount(rs.getInt("tenders_count"));
            cpv.setRisksCount(rs.getInt("risks_count"));
            return cpv;
        });

        TendersCPVResponse response = new TendersCPVResponse();
        response.setCpvs(cpvs);
        return response;
    }

    //2017-06-15
    @RequestMapping(value = "/indicators/tendersbuyers", method = POST)
    public IndicatorsTendersBuyersResponse getIndicatorsTendersBuyers(@RequestBody IndicatorsTendersBuyersRequest request) {
        String query = "SELECT i.id,i.code,i.description,COUNT(t.id) tenders_count FROM transact_indicator i\n" +
                "JOIN risk_indicators ri ON ri.indicator_id = i.id\n" +
                "JOIN tender t ON ri.tender_id = t.id\n" +
                "JOIN tender_cpv cpv ON cpv.tender_id = t.id\n" +
                "WHERE TRUE " +
                (isEmpty(request.getBuyersId()) ? "" : "AND t.buyer_id IN (" + collectionToCommaDelimitedString(request.getBuyersId()) + ") ") +
                (isEmpty(request.getIndicatorId()) ? "" : "AND indicatorid IN (" + collectionToCommaDelimitedString(request.getIndicatorId().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ")") +
                (Objects.isNull(request.getMinAmount()) ? "" : "AND t.amount >" + request.getMinAmount()) +
                (Objects.isNull(request.getMaxAmount()) ? "" : "AND t.amount <" + request.getMaxAmount()) +
                (isEmpty(request.getCpv()) ? "" : "AND substring(cpv.tender_cpv, 0,3) IN (" + collectionToCommaDelimitedString(request.getCpv().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ") ") +
                (StringUtils.isEmpty(request.getDate()) ? "" : "AND substring(t.tenderid,4,7) = '" + request.getDate() + "'") +
                (isEmpty(request.getProcurentMethodTypes()) ? "" : "AND t.procurement_method_type IN (" + collectionToCommaDelimitedString(request.getProcurentMethodTypes().stream().map(type -> StringUtils.quote(type)).collect(Collectors.toList())) + ") ") +
                "GROUP BY i.id\n" +
                "ORDER BY tenders_count DESC;";
        List<IndicatorTendersBuyers> indicators = jdbcTemplate.query(query, (rs, rowNum) -> {
            IndicatorTendersBuyers indicator = new IndicatorTendersBuyers();
            indicator.setId(rs.getInt("id"));
            indicator.setCode(rs.getString("code"));
            indicator.setDescription(rs.getString("description"));
            indicator.setTendersCount(rs.getInt("tenders_count"));
            return indicator;
        });
        IndicatorsTendersBuyersResponse response = new IndicatorsTendersBuyersResponse();
        response.setIndicators(indicators);
        return response;
    }

    @RequestMapping("/buyers/{buyerId}")
    public Buyer getBuyer(@PathVariable Integer buyerId) {
        String query = "SELECT id,name FROM buyer WHERE id = " + buyerId;
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            Buyer buyer = new Buyer();
            buyer.setId(rs.getInt("id"));
            buyer.setName(rs.getString("name"));
            return buyer;
        });
    }

    @RequestMapping(value = "/buyers/top")
    public BuyersResponse getTopBuyers() {
        String query = "SELECT buyer_id FROM risk_indicators \n" +
                "GROUP BY buyer_id\n" +
                "ORDER BY COUNT(tender_id) DESC\n" +
                "LIMIT 10";
        List<Buyer> buyers = jdbcTemplate.query(query, (rs, rowNum) -> {
            Buyer buyer = new Buyer();
            buyer.setId(rs.getInt("buyer_id"));
            return buyer;
        });

        BuyersResponse response = new BuyersResponse();
        response.setBuyers(buyers);
        return response;
    }

    @RequestMapping(value = "/indicators/buyerstenders", method = POST)
    public HitMapResponse getIndicatorBueyrsTenders() {
        String query = "SELECT " +
                "(SELECT description FROM transact_indicator WHERE id = indicator_id) indicator," +
                "(SELECT code FROM transact_indicator WHERE id = indicator_id) indicatorCode,\n" +
                "(SELECT name FROM buyer WHERE id = buyer_id) buyer_name,COUNT(case when risk <> 0 then 1 end ) tenders_count\n" +
                "FROM risk_indicators\n" +
                "WHERE buyer_id IN(\n" +
                "  SELECT buyer_id FROM risk_indicators\n" +
                "  GROUP BY buyer_id\n" +
                "  ORDER BY COUNT(tender_id) DESC\n" +
                "  LIMIT 10\n" +
                ")\n" +
                "GROUP BY buyer_id,indicator_id\n" +
                "ORDER BY buyer_id;";
        List<HitMapIndicator> indicators = jdbcTemplate.query(query, (rs, rowNum) -> {
            HitMapIndicator buyer = new HitMapIndicator();
            buyer.setIndicatorCode(rs.getString("indicatorCode"));
            buyer.setIndicator(rs.getString("indicator"));
            buyer.setBuyerName(rs.getString("buyer_name"));
            buyer.setTendersCount(rs.getInt("tenders_count"));
            return buyer;
        });

        HitMapResponse response = new HitMapResponse();
        response.setIndicators(indicators);
        return response;
    }

}