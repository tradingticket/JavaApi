package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import it.trade.model.request.TradeItRequestWithSession;

public class TradeItGetAllOrderStatusRequest extends TradeItRequestWithSession {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    public TradeItGetAllOrderStatusRequest(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "TradeItGetAllOrderStatusRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                "}, " + super.toString();
    }
}
