package it.trade.model.reponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItPosition {
    @SerializedName("costbasis")
    @Expose
    public Double costbasis;

    @SerializedName("holdingType")
    @Expose
    public String holdingType;

    @SerializedName("lastPrice")
    @Expose
    public Double lastPrice;

    @SerializedName("quantity")
    @Expose
    public Double quantity;

    @SerializedName("symbol")
    @Expose
    public String symbol;

    @SerializedName("symbolClass")
    @Expose
    public String symbolClass;

    @SerializedName("todayGainLossDollar")
    @Expose
    public Double todayGainLossDollar;

    @SerializedName("todayGainLossPercentage")
    @Expose
    public Double todayGainLossPercentage;

    @SerializedName("totalGainLossDollar")
    @Expose
    public Double totalGainLossDollar;

    @SerializedName("totalGainLossPercentage")
    @Expose
    public Double totalGainLossPercentage;

    @Override
    public String toString() {
        return "TradeItPosition{" +
                "costbasis=" + costbasis +
                ", holdingType='" + holdingType + '\'' +
                ", lastPrice=" + lastPrice +
                ", quantity=" + quantity +
                ", symbol='" + symbol + '\'' +
                ", symbolClass='" + symbolClass + '\'' +
                ", todayGainLossDollar=" + todayGainLossDollar +
                ", todayGainLossPercentage=" + todayGainLossPercentage +
                ", totalGainLossDollar=" + totalGainLossDollar +
                ", totalGainLossPercentage=" + totalGainLossPercentage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeItPosition position = (TradeItPosition) o;

        if (costbasis != null ? !costbasis.equals(position.costbasis) : position.costbasis != null)
            return false;
        if (holdingType != null ? !holdingType.equals(position.holdingType) : position.holdingType != null)
            return false;
        if (lastPrice != null ? !lastPrice.equals(position.lastPrice) : position.lastPrice != null)
            return false;
        if (quantity != null ? !quantity.equals(position.quantity) : position.quantity != null)
            return false;
        if (symbol != null ? !symbol.equals(position.symbol) : position.symbol != null)
            return false;
        if (symbolClass != null ? !symbolClass.equals(position.symbolClass) : position.symbolClass != null)
            return false;
        if (todayGainLossDollar != null ? !todayGainLossDollar.equals(position.todayGainLossDollar) : position.todayGainLossDollar != null)
            return false;
        if (todayGainLossPercentage != null ? !todayGainLossPercentage.equals(position.todayGainLossPercentage) : position.todayGainLossPercentage != null)
            return false;
        if (totalGainLossDollar != null ? !totalGainLossDollar.equals(position.totalGainLossDollar) : position.totalGainLossDollar != null)
            return false;
        return totalGainLossPercentage != null ? totalGainLossPercentage.equals(position.totalGainLossPercentage) : position.totalGainLossPercentage == null;

    }

    @Override
    public int hashCode() {
        int result = costbasis != null ? costbasis.hashCode() : 0;
        result = 31 * result + (holdingType != null ? holdingType.hashCode() : 0);
        result = 31 * result + (lastPrice != null ? lastPrice.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + (symbolClass != null ? symbolClass.hashCode() : 0);
        result = 31 * result + (todayGainLossDollar != null ? todayGainLossDollar.hashCode() : 0);
        result = 31 * result + (todayGainLossPercentage != null ? todayGainLossPercentage.hashCode() : 0);
        result = 31 * result + (totalGainLossDollar != null ? totalGainLossDollar.hashCode() : 0);
        result = 31 * result + (totalGainLossPercentage != null ? totalGainLossPercentage.hashCode() : 0);
        return result;
    }
}
