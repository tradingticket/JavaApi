package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItProxyVoteUrlRequest extends TradeItRequestWithSession {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    @SerializedName("symbol")
    @Expose
    public String symbol;

    public TradeItProxyVoteUrlRequest() {

    }

    public TradeItProxyVoteUrlRequest(String sessionToken, String accountNumber, String symbol) {
        this.sessionToken = sessionToken;
        this.accountNumber = accountNumber;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "TradeItProxyVoteUrlRequest{" +
            "accountNumber='" + accountNumber + '\'' +
            ", symbol='" + symbol + '\'' +
            ", sessionToken='" + sessionToken + '\'' +
            '}';
    }
}
