package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CryptoTradeOrderDetails {

    @SerializedName("orderLimitPrice")
    @Expose
    public Double orderLimitPrice;

    @SerializedName("orderStopPrice")
    @Expose
    public Double orderStopPrice;

    @SerializedName("orderExpiration")
    @Expose
    public String orderExpiration;

    @SerializedName("orderAction")
    @Expose
    public String orderAction;

    @SerializedName("orderPair")
    @Expose
    public String orderPair;

    @SerializedName("orderPriceType")
    @Expose
    public String orderPriceType;

    @SerializedName("orderQuantity")
    @Expose
    public Double orderQuantity;

    @SerializedName("orderQuantityType")
    @Expose
    public String orderQuantityType;
}
