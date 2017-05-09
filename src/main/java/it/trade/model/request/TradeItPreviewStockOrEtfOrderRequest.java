package it.trade.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItPreviewStockOrEtfOrderRequest extends TradeItRequestWithSession {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    @SerializedName("orderAction")
    @Expose
    public String orderAction;

    @SerializedName("orderQuantity")
    @Expose
    public String orderQuantity;

    @SerializedName("orderSymbol")
    @Expose
    public String orderSymbol;

    @SerializedName("orderPriceType")
    @Expose
    public String orderPriceType;

    @SerializedName("orderLimitPrice")
    @Expose
    public String orderLimitPrice;

    @SerializedName("orderStopPrice")
    @Expose
    public String orderStopPrice;

    @SerializedName("orderExpiration")
    @Expose
    public String orderExpiration;

    public TradeItPreviewStockOrEtfOrderRequest() {};

    public TradeItPreviewStockOrEtfOrderRequest(String accountNumber,
                                                String orderAction,
                                                String orderQuantity,
                                                String orderSymbol,
                                                String orderPriceType,
                                                String orderLimitPrice,
                                                String orderStopPrice,
                                                String orderExpiration) {
        this.accountNumber = accountNumber;
        this.orderAction = orderAction;
        this.orderQuantity = orderQuantity;
        this.orderSymbol = orderSymbol;
        this.orderPriceType = orderPriceType;
        this.orderLimitPrice = orderLimitPrice;
        this.orderStopPrice = orderStopPrice;
        this.orderExpiration = orderExpiration;
    }

    @Override
    public String toString() {
        return "TradeItPreviewStockOrEtfOrderRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", orderAction='" + orderAction + '\'' +
                ", orderQuantity='" + orderQuantity + '\'' +
                ", orderSymbol='" + orderSymbol + '\'' +
                ", orderPriceType='" + orderPriceType + '\'' +
                ", orderLimitPrice='" + orderLimitPrice + '\'' +
                ", orderStopPrice='" + orderStopPrice + '\'' +
                ", orderExpiration='" + orderExpiration + '\'' +
                "}, " + super.toString();
    }
}
