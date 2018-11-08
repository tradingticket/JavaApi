package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItPlaceCryptoOrderResponse extends TradeItResponse {

    @SerializedName("orderNumber")
    @Expose
    public String orderNumber;

    @SerializedName("confirmationMessage")
    @Expose
    public String confirmationMessage;

    @SerializedName("broker")
    @Expose
    public String broker;

    @SerializedName("accountBaseCurrency")
    @Expose
    public String accountBaseCurrency;

    @SerializedName("timestamp")
    @Expose
    public String timestamp;

    @SerializedName("orderDetails")
    @Expose
    public CryptoTradeOrderDetails orderDetails;
}
