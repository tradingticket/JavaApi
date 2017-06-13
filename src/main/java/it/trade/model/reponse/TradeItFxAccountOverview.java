package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItFxAccountOverview {

    @SerializedName("totalValueBaseCurrency")
    @Expose
    public Double totalValueBaseCurrency;

    @SerializedName("totalValueUSD")
    @Expose
    public Double totalValueUSD;

    @SerializedName("buyingPowerBaseCurrency")
    @Expose
    public Double buyingPowerBaseCurrency;

    @SerializedName("unrealizedProfitAndLossBaseCurrency")
    @Expose
    public Double unrealizedProfitAndLossBaseCurrency;

    @SerializedName("realizedProfitAndLossBaseCurrency")
    @Expose
    public Double realizedProfitAndLossBaseCurrency;

    @SerializedName("marginBalanceBaseCurrency")
    @Expose
    public Double marginBalanceBaseCurrency;

    @Override
    public String toString() {
        return "TradeItFxAccountOverview{" +
                "totalValueBaseCurrency=" + totalValueBaseCurrency +
                ", totalValueUSD=" + totalValueUSD +
                ", buyingPowerBaseCurrency=" + buyingPowerBaseCurrency +
                ", unrealizedProfitAndLossBaseCurrency=" + unrealizedProfitAndLossBaseCurrency +
                ", realizedProfitAndLossBaseCurrency=" + realizedProfitAndLossBaseCurrency +
                ", marginBalanceBaseCurrency=" + marginBalanceBaseCurrency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeItFxAccountOverview that = (TradeItFxAccountOverview) o;

        if (totalValueBaseCurrency != null ? !totalValueBaseCurrency.equals(that.totalValueBaseCurrency) : that.totalValueBaseCurrency != null)
            return false;
        if (totalValueUSD != null ? !totalValueUSD.equals(that.totalValueUSD) : that.totalValueUSD != null)
            return false;
        if (buyingPowerBaseCurrency != null ? !buyingPowerBaseCurrency.equals(that.buyingPowerBaseCurrency) : that.buyingPowerBaseCurrency != null)
            return false;
        if (unrealizedProfitAndLossBaseCurrency != null ? !unrealizedProfitAndLossBaseCurrency.equals(that.unrealizedProfitAndLossBaseCurrency) : that.unrealizedProfitAndLossBaseCurrency != null)
            return false;
        if (realizedProfitAndLossBaseCurrency != null ? !realizedProfitAndLossBaseCurrency.equals(that.realizedProfitAndLossBaseCurrency) : that.realizedProfitAndLossBaseCurrency != null)
            return false;
        return marginBalanceBaseCurrency != null ? marginBalanceBaseCurrency.equals(that.marginBalanceBaseCurrency) : that.marginBalanceBaseCurrency == null;
    }

    @Override
    public int hashCode() {
        int result = totalValueBaseCurrency != null ? totalValueBaseCurrency.hashCode() : 0;
        result = 31 * result + (totalValueUSD != null ? totalValueUSD.hashCode() : 0);
        result = 31 * result + (buyingPowerBaseCurrency != null ? buyingPowerBaseCurrency.hashCode() : 0);
        result = 31 * result + (unrealizedProfitAndLossBaseCurrency != null ? unrealizedProfitAndLossBaseCurrency.hashCode() : 0);
        result = 31 * result + (realizedProfitAndLossBaseCurrency != null ? realizedProfitAndLossBaseCurrency.hashCode() : 0);
        result = 31 * result + (marginBalanceBaseCurrency != null ? marginBalanceBaseCurrency.hashCode() : 0);
        return result;
    }
}
