package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItPreviewStockOrEtfOrderResponse extends TradeItResponse  {
    @SerializedName("orderDetails")
    @Expose
    public OrderDetails orderDetails;

    @SerializedName("orderId")
    @Expose
    public String orderId;

    /**
     * @deprecated Use orderDetails.warnings.
     */
    @SerializedName("ackWarningsList")
    @Expose
    @Deprecated
    public List<String> ackWarningsList = new ArrayList<>();

    /**
     * @deprecated Use orderDetails.warnings.
     */
    @SerializedName("warningsList")
    @Expose
    @Deprecated
    public List<String> warningsList = new ArrayList<>();

    @Override
    public String toString() {
        return "TradeItPreviewStockOrEtfOrderResponse{" +
                ", orderDetails=" + orderDetails +
                ", orderId=" + orderId +
                "}, " + super.toString();
    }
}
