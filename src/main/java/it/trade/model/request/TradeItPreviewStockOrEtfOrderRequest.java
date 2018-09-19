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

    @SerializedName("userDisabledMargin")
    @Expose
    public boolean userDisabledMargin;

    @SerializedName("orderQuantityType")
    @Expose
    public String orderQuantityType;

    public TradeItPreviewStockOrEtfOrderRequest() {};

    public TradeItPreviewStockOrEtfOrderRequest(String accountNumber,
                                                String orderAction,
                                                String orderQuantity,
                                                String orderSymbol,
                                                String orderPriceType,
                                                String orderLimitPrice,
                                                String orderStopPrice,
                                                String orderExpiration,
                                                String orderQuantityType,
                                                boolean userDisabledMargin
    ) {
        this.accountNumber = accountNumber;
        this.orderAction = orderAction;
        this.orderQuantity = orderQuantity;
        this.orderSymbol = orderSymbol;
        this.orderPriceType = orderPriceType;
        this.orderLimitPrice = orderLimitPrice;
        this.orderStopPrice = orderStopPrice;
        this.orderExpiration = orderExpiration;
        this.orderQuantityType = orderQuantityType;
        this.userDisabledMargin = userDisabledMargin;
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
                ", orderQuantityType='" + orderQuantityType + '\'' +
                ", userDisabledMargin'= " + userDisabledMargin + '\'' +
                "}, " + super.toString();
    }
}
