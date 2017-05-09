package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItGetAccountOverviewRequest extends TradeItRequestWithSession {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    public TradeItGetAccountOverviewRequest(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "TradeItGetAccountOverviewRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                "}, " + super.toString();
    }
}