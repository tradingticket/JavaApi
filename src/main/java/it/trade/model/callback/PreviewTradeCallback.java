package it.trade.model.callback;

import it.trade.model.TradeItErrorResult;
import it.trade.model.reponse.TradeItResponse;
import it.trade.model.reponse.TradeItResponseStatus;
import retrofit2.Call;
import retrofit2.Response;

public abstract class PreviewTradeCallback<TradeItResponseType, TradeItCallBackType> extends DefaultCallback<TradeItResponseType, TradeItCallBackType> {

    protected PreviewTradeCallback(TradeItCallback<TradeItCallBackType> callback) {
        super(callback);
    }

    @Override
    public void onResponse(Call<TradeItResponseType> call, Response<TradeItResponseType> response) {
        if (response.isSuccessful()) {
            TradeItResponseType responseType = response.body();
            TradeItResponse tradeItResponse = responseType instanceof TradeItResponse ? (TradeItResponse) responseType : null;
            if (tradeItResponse == null) {
                onErrorResponse(new TradeItErrorResult());
            } else if (tradeItResponse.status == TradeItResponseStatus.REVIEW_ORDER) {
                onSuccessResponse(response);
            } else {
                onErrorResponse(new TradeItErrorResult(tradeItResponse.code, tradeItResponse.shortMessage, tradeItResponse.longMessages));
            }
        } else {
            onErrorResponse(new TradeItErrorResult(response.code()));
        }
    }
}
