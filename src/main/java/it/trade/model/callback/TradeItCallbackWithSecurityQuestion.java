package it.trade.model.callback;

import it.trade.model.TradeItSecurityQuestion;

public interface TradeItCallbackWithSecurityQuestion<T> extends TradeItCallback<T>  {
    void onSecurityQuestion(TradeItSecurityQuestion securityQuestion);
    void cancelSecurityQuestion();
    void submitSecurityAnswer(String answer);
}
