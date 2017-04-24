package it.trade.model.callback;


import it.trade.model.TradeItErrorResult;

public interface TradeItCallback<T> {

    void onSuccess(T type);

    void onError(TradeItErrorResult error);
}
