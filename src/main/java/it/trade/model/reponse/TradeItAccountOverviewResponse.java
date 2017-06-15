package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItAccountOverviewResponse extends TradeItResponse {

    @SerializedName("accountOverview")
    @Expose
    public TradeItAccountOverview accountOverview;

    @SerializedName("fxAccountOverview")
    @Expose
    public TradeItFxAccountOverview fxAccountOverview;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeItAccountOverviewResponse that = (TradeItAccountOverviewResponse) o;

        if (accountOverview != null ? !accountOverview.equals(that.accountOverview) : that.accountOverview != null)
            return false;
        return fxAccountOverview != null ? fxAccountOverview.equals(that.fxAccountOverview) : that.fxAccountOverview == null;
    }

    @Override
    public int hashCode() {
        int result = accountOverview != null ? accountOverview.hashCode() : 0;
        result = 31 * result + (fxAccountOverview != null ? fxAccountOverview.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TradeItAccountOverviewResponse{" +
                "accountOverview=" + accountOverview +
                ", fxAccountOverview=" + fxAccountOverview +
                "}, " + super.toString();
    }
}
