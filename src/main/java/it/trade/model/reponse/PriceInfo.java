package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceInfo {
    @SerializedName("conditionType")
    @Expose
    public String conditionType;

    @SerializedName("initialStopPrice")
    @Expose
    public Double initialStopPrice;

    @SerializedName("conditionSymbol")
    @Expose
    public String conditionSymbol;

    @SerializedName("trailPrice")
    @Expose
    public Double trailPrice;

    @SerializedName("conditionFollowPrice")
    @Expose
    public Double conditionFollowPrice;

    @SerializedName("limitPrice")
    @Expose
    public Double limitPrice;

    @SerializedName("triggerPrice")
    @Expose
    public Double triggerPrice;

    @SerializedName("conditionPrice")
    @Expose
    public Double conditionPrice;

    @SerializedName("bracketLimitPrice")
    @Expose
    public Double bracketLimitPrice;

    @SerializedName("type")
    @Expose
    public String type;

    @SerializedName("stopPrice")
    @Expose
    public Double stopPrice;

    @Override
    public String toString() {
        return "PriceInfo{" +
                "conditionType='" + conditionType + '\'' +
                ", initialStopPrice=" + initialStopPrice +
                ", conditionSymbol='" + conditionSymbol + '\'' +
                ", trailPrice=" + trailPrice +
                ", conditionFollowPrice=" + conditionFollowPrice +
                ", limitPrice=" + limitPrice +
                ", triggerPrice=" + triggerPrice +
                ", conditionPrice=" + conditionPrice +
                ", bracketLimitPrice=" + bracketLimitPrice +
                ", type='" + type + '\'' +
                ", stopPrice=" + stopPrice +
                '}';
    }
}
