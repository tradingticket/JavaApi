package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItGetAllTransactionsHistoryRequest extends TradeItRequestWithSession {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    public TradeItGetAllTransactionsHistoryRequest(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "TradeItGetAllTransactionsHistoryRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                "}, " + super.toString();
    }
}
