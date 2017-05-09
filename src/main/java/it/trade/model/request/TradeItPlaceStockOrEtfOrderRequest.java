package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItPlaceStockOrEtfOrderRequest extends TradeItRequestWithSession {
    @SerializedName("orderId")
    @Expose
    public String orderId;

    public TradeItPlaceStockOrEtfOrderRequest(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "TradeItPlaceStockOrEtfOrderRequest{" +
                "orderId='" + orderId + '\'' +
                "}, " + super.toString();
    }
}
