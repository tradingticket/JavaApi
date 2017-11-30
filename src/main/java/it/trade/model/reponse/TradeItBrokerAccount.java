package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItBrokerAccount {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("accountBaseCurrency")
    @Expose
    public String accountBaseCurrency;

    @SerializedName("userCanDisableMargin")
    @Expose
    public boolean userCanDisableMargin;

    @Override
    public String toString() {
        return "TradeItBrokerAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", accountBaseCurrency='" + accountBaseCurrency + '\'' +
                ", userCanDisableMargin='" + userCanDisableMargin + '\'' +
                '}';
    }
}
