package it.trade.model.callback;


import it.trade.api.TradeItApiClient;
import it.trade.model.TradeItErrorResult;
import it.trade.model.TradeItSecurityQuestion;
import it.trade.model.reponse.TradeItAuthenticateResponse;
import it.trade.model.reponse.TradeItResponse;
import retrofit2.Call;
import retrofit2.Response;

public abstract class AuthenticationCallback<TradeItResponseType, TradeItCallBackType> extends DefaultCallback<TradeItResponseType, TradeItCallBackType> {

    protected AuthenticationCallback(TradeItCallbackWithSecurityQuestion callback, TradeItApiClient apiClient) {
        super(callback);
        if (callback != null) {
            ((TradeItCallbackWithSecurityQuestionImpl) callback).setApiClient(apiClient);
            ((TradeItCallbackWithSecurityQuestionImpl) callback).setAuthenticationHandler(this);
        }
    }

    @Override
    public void onResponse(Call<TradeItResponseType> call, Response<TradeItResponseType> response) {
        if (response.isSuccessful()) {
            TradeItResponseType responseType = response.body();
            TradeItResponse tradeItResponse = responseType instanceof TradeItResponse ? (TradeItResponse) responseType : null;
            if (tradeItResponse == null) {
                onErrorResponse(new TradeItErrorResult());
            } else if (tradeItResponse.isSuccessful()) {
                TradeItAuthenticateResponse authenticateResponse = responseType instanceof TradeItAuthenticateResponse ? (TradeItAuthenticateResponse) responseType : null;
                if (tradeItResponse == null) {
                    onErrorResponse(new TradeItErrorResult());
                } else {
                    onSuccessResponse(response);
                }
            } else if (tradeItResponse.isSecurityQuestion()) {
                TradeItAuthenticateResponse authenticateResponse = responseType instanceof TradeItAuthenticateResponse ? (TradeItAuthenticateResponse) responseType : null;
                if (tradeItResponse == null) {
                    onErrorResponse(new TradeItErrorResult());
                } else {
                    ((TradeItCallbackWithSecurityQuestionImpl) callback).getApiClient().setSessionToken(authenticateResponse.sessionToken);
                    ((TradeItCallbackWithSecurityQuestion) callback).onSecurityQuestion(new TradeItSecurityQuestion(authenticateResponse.securityQuestion, authenticateResponse.securityQuestionOptions));
                }
            } else {
                onErrorResponse(new TradeItErrorResult(tradeItResponse.code, tradeItResponse.shortMessage, tradeItResponse.longMessages));
            }
        } else {
            onErrorResponse(new TradeItErrorResult(response.code()));
        }
    }
}
