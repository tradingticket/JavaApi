package it.trade.model.request;

public enum TradeItEnvironment {

    PRODUCTION("https://ems.tradingticket.com/"),
    QA("https://ems.qa.tradingticket.com/"),
    LOCAL("https://localhost:8443/");

    private String baseUrl;

    private TradeItEnvironment(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}