package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItGetAllTransactionsHistoryResponse extends TradeItResponse {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    @SerializedName("transactionHistoryDetailsList")
    @Expose
    public List<TransactionDetails> transactionHistoryDetailsList = new ArrayList<TransactionDetails>();

    @Override
    public String toString() {
        return "TradeItGetAllTransactionsHistoryResponse{" +
                "accountNumber='" + accountNumber + '\'' +
                ", transactionHistoryDetailsList=" + transactionHistoryDetailsList +
                "}, " + super.toString();
    }
}
