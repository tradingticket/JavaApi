package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItCancelOrderRequest extends TradeItRequestWithSession {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    @SerializedName("orderNumber")
    @Expose
    public String orderNumber;

    public TradeItCancelOrderRequest(String accountNumber, String orderNumber) {
        this.accountNumber = accountNumber;
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "TradeItCancelOrderRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                "}, " + super.toString();
    }
}
