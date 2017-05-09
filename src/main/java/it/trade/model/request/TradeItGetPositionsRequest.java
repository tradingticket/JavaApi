package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItGetPositionsRequest extends TradeItRequestWithSession {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    @SerializedName("page")
    @Expose
    public Integer page;

    public TradeItGetPositionsRequest(String accountNumber, Integer page) {
        this.accountNumber = accountNumber;
        this.page = page;
    }

    @Override
    public String toString() {
        return "TradeItGetPositionsRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                "page='" + page + '\'' +
                "}, " + super.toString();
    }
}
