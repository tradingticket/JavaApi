package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItPlaceStockOrEtfOrderResponse extends TradeItResponse {
    @SerializedName("broker")
    @Expose
    public String broker;

    @SerializedName("confirmationMessage")
    @Expose
    public String confirmationMessage;

    @SerializedName("orderInfo")
    @Expose
    public OrderInfo orderInfo;

    @SerializedName("orderNumber")
    @Expose
    public String orderNumber;

    @SerializedName("timestamp")
    @Expose
    public String timestamp;

    @Override
    public String toString() {
        return "TradeItPlaceStockOrEtfOrderResponse{" +
                "broker='" + broker + '\'' +
                ", confirmationMessage='" + confirmationMessage + '\'' +
                ", orderInfo=" + orderInfo +
                ", orderNumber='" + orderNumber + '\'' +
                ", timestamp='" + timestamp + '\'' +
                "}, " + super.toString();
    }
}