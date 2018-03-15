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
    @Deprecated
    /**
     * @deprecated use todayGainLossAbsolute instead
     */
    public Double todayGainLossDollar;

    @SerializedName("todayGainLossAbsolute")
    @Expose
    public Double todayGainLossAbsolute;

    @SerializedName("todayGainLossPercentage")
    @Expose
    public Double todayGainLossPercentage;

    @SerializedName("totalGainLossDollar")
    @Expose
    @Deprecated
    /**
     * @deprecated use totalGainLossAbsolute instead
     */
    public Double totalGainLossDollar;

    @SerializedName("totalGainLossAbsolute")
    @Expose
    public Double totalGainLossAbsolute;

    @SerializedName("totalGainLossPercentage")
    @Expose
    public Double totalGainLossPercentage;

    @SerializedName("exchange")
    @Expose
    public String exchange;

    @SerializedName("currency")
    @Expose
    public String currency;

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
                ", todayGainLossAbsolute=" + todayGainLossAbsolute +
                ", todayGainLossPercentage=" + todayGainLossPercentage +
                ", totalGainLossDollar=" + totalGainLossDollar +
                ", totalGainLossAbsolute=" + totalGainLossAbsolute +
                ", totalGainLossPercentage=" + totalGainLossPercentage +
                ", exchange='" + exchange + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeItPosition that = (TradeItPosition) o;

        if (costbasis != null ? !costbasis.equals(that.costbasis) : that.costbasis != null) return false;
        if (holdingType != null ? !holdingType.equals(that.holdingType) : that.holdingType != null) return false;
        if (lastPrice != null ? !lastPrice.equals(that.lastPrice) : that.lastPrice != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) return false;
        if (symbolClass != null ? !symbolClass.equals(that.symbolClass) : that.symbolClass != null) return false;
        if (todayGainLossDollar != null ? !todayGainLossDollar.equals(that.todayGainLossDollar) : that.todayGainLossDollar != null)
            return false;
        if (todayGainLossAbsolute != null ? !todayGainLossAbsolute.equals(that.todayGainLossAbsolute) : that.todayGainLossAbsolute != null)
            return false;
        if (todayGainLossPercentage != null ? !todayGainLossPercentage.equals(that.todayGainLossPercentage) : that.todayGainLossPercentage != null)
            return false;
        if (totalGainLossDollar != null ? !totalGainLossDollar.equals(that.totalGainLossDollar) : that.totalGainLossDollar != null)
            return false;
        if (totalGainLossAbsolute != null ? !totalGainLossAbsolute.equals(that.totalGainLossAbsolute) : that.totalGainLossAbsolute != null)
            return false;
        if (totalGainLossPercentage != null ? !totalGainLossPercentage.equals(that.totalGainLossPercentage) : that.totalGainLossPercentage != null)
            return false;
        if (exchange != null ? !exchange.equals(that.exchange) : that.exchange != null) return false;
        return currency != null ? currency.equals(that.currency) : that.currency == null;
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
        result = 31 * result + (todayGainLossAbsolute != null ? todayGainLossAbsolute.hashCode() : 0);
        result = 31 * result + (todayGainLossPercentage != null ? todayGainLossPercentage.hashCode() : 0);
        result = 31 * result + (totalGainLossDollar != null ? totalGainLossDollar.hashCode() : 0);
        result = 31 * result + (totalGainLossAbsolute != null ? totalGainLossAbsolute.hashCode() : 0);
        result = 31 * result + (totalGainLossPercentage != null ? totalGainLossPercentage.hashCode() : 0);
        result = 31 * result + (exchange != null ? exchange.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }
}
