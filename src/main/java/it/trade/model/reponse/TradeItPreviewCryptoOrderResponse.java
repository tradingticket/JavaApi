package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItPreviewCryptoOrderResponse extends TradeItResponse {
    @SerializedName("orderId")
    @Expose
    public String orderId;

    @SerializedName("orderDetails")
    @Expose
    public CryptoPreviewOrderDetails orderDetails;
}
