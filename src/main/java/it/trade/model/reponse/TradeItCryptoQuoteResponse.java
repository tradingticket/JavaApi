package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class TradeItCryptoQuoteResponse extends TradeItResponse {

    @SerializedName("ask")
    @Expose
    public BigDecimal ask;

    @SerializedName("bid")
    @Expose
    public BigDecimal bid;

    @SerializedName("last")
    @Expose
    public BigDecimal last;

    @SerializedName("open")
    @Expose
    public BigDecimal open;

    @SerializedName("dayHigh")
    @Expose
    public BigDecimal dayHigh;

    @SerializedName("dayLow")
    @Expose
    public BigDecimal dayLow;

    @SerializedName("volume")
    @Expose
    public BigDecimal volume;

    @SerializedName("dateTime")
    @Expose
    public String dateTime;
}
