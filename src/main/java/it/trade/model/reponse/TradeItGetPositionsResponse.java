package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItGetPositionsResponse extends TradeItResponse {
    @SerializedName("currentPage")
    @Expose
    public Integer currentPage;

    @SerializedName("positions")
    @Expose
    public List<TradeItPosition> positions = new ArrayList<TradeItPosition>();

    @SerializedName("totalPages")
    @Expose
    public Integer totalPages;

    @Override
    public String toString() {
        return "TradeItGetPositionsResponse{" +
                "currentPage=" + currentPage +
                ", positions=" + positions +
                ", totalPages=" + totalPages +
                "}, " + super.toString();
    }
}
