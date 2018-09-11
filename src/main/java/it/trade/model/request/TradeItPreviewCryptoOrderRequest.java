package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItPreviewCryptoOrderRequest extends TradeItRequestWithSession {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    @SerializedName("orderAction")
    @Expose
    public String orderAction;

    @SerializedName("orderQuantity")
    @Expose
    public Double orderQuantity;

    @SerializedName("orderPair")
    @Expose
    public String orderPair;

    @SerializedName("orderPriceType")
    @Expose
    public String orderPriceType;

    @SerializedName("orderExpiration")
    @Expose
    public String orderExpiration;

    @SerializedName("orderLimitPrice")
    @Expose
    public Double orderLimitPrice;

    @SerializedName("orderStopPrice")
    @Expose
    public Double orderStopPrice;

    @SerializedName("orderQuantityType")
    @Expose
    public String orderQuantityType;

    public TradeItPreviewCryptoOrderRequest(
            String sessionToken,
            String accountNumber,
            String orderAction,
            Double orderQuantity,
            String orderPair,
            String orderPriceType,
            String orderExpiration,
            Double orderLimitPrice,
            Double orderStopPrice,
            String orderQuantityType
    ) {
        this.sessionToken = sessionToken;
        this.accountNumber = accountNumber;
        this.orderAction = orderAction;
        this.orderQuantity = orderQuantity;
        this.orderPair = orderPair;
        this.orderPriceType = orderPriceType;
        this.orderExpiration = orderExpiration;
        this.orderLimitPrice = orderLimitPrice;
        this.orderStopPrice = orderStopPrice;
        this.orderQuantityType = orderQuantityType;
    }

    public TradeItPreviewCryptoOrderRequest() {}

    @Override
    public String toString() {
        return "TradeItPreviewCryptoOrderRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", orderAction='" + orderAction + '\'' +
                ", orderQuantity=" + orderQuantity +
                ", orderPair='" + orderPair + '\'' +
                ", orderPriceType='" + orderPriceType + '\'' +
                ", orderExpiration='" + orderExpiration + '\'' +
                ", orderLimitPrice=" + orderLimitPrice +
                ", orderStopPrice=" + orderStopPrice +
                ", orderQuantityType='" + orderQuantityType + '\'' +
                "}, " + super.toString();
    }
}
