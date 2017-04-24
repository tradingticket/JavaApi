package it.trade.model.callback;

import it.trade.api.TradeItApiClient;
import it.trade.model.TradeItErrorResult;
import it.trade.model.request.TradeItAnswerSecurityQuestionRequest;


public abstract class TradeItCallbackWithSecurityQuestionImpl<T> implements TradeItCallbackWithSecurityQuestion<T>  {
    private TradeItApiClient apiClient;
    private AuthenticationCallback authenticationHandler;

    @Override
    public void submitSecurityAnswer(String answer) {
        TradeItAnswerSecurityQuestionRequest answerSecurityQuestionRequest = new TradeItAnswerSecurityQuestionRequest(answer);
        if (this.apiClient != null && this.authenticationHandler != null) {
            this.apiClient.answerSecurityQuestion(answerSecurityQuestionRequest, authenticationHandler);
        } else {
            onError(new TradeItErrorResult("Error submitting answer", "An error occured while submitting security question"));
        }

    }

    @Override
    public void cancelSecurityQuestion() {
        //by default do nothing
    }

    void setApiClient(TradeItApiClient apiClient) {
        this.apiClient = apiClient;
    }

    TradeItApiClient getApiClient() {
        return this.apiClient;
    }

    void setAuthenticationHandler(AuthenticationCallback authenticationHandler) {
        this.authenticationHandler = authenticationHandler;
    }
}
