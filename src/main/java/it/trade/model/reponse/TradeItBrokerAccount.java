package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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

    @SerializedName("orderCapabilities")
    @Expose
    public List<OrderCapability> orderCapabilities;

    @Override
    public String toString() {
        return "TradeItBrokerAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", accountBaseCurrency='" + accountBaseCurrency + '\'' +
                ", orderCapabilities='" + orderCapabilities + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeItBrokerAccount that = (TradeItBrokerAccount) o;

        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (accountBaseCurrency != null ? !accountBaseCurrency.equals(that.accountBaseCurrency) : that.accountBaseCurrency != null)
            return false;
        return orderCapabilities != null ? orderCapabilities.equals(that.orderCapabilities) : that.orderCapabilities == null;
    }

    @Override
    public int hashCode() {
        int result = accountNumber != null ? accountNumber.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (accountBaseCurrency != null ? accountBaseCurrency.hashCode() : 0);
        result = 31 * result + (orderCapabilities != null ? orderCapabilities.hashCode() : 0);
        return result;
    }
}
