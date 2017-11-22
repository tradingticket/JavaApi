package it.trade.api

import it.trade.model.TradeItErrorResult
import it.trade.model.TradeItSecurityQuestion
import it.trade.model.callback.AuthenticationCallback
import it.trade.model.callback.TradeItCallback
import it.trade.model.callback.TradeItCallbackWithSecurityQuestion
import it.trade.model.callback.TradeItCallbackWithSecurityQuestionImpl
import it.trade.model.reponse.*
import it.trade.model.reponse.TradeItAvailableBrokersResponse.Broker
import it.trade.model.request.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import spock.lang.Specification

/**
 * Unit tests
 */
class TradeItApiClientSpec extends Specification {

	TradeItApi tradeItApi = Mock(TradeItApi)
	TradeItApiClient apiClient = new TradeItApiClient(tradeItApi)

	void setup() {
		TradeItRequestWithKey.API_KEY = "My Api Key"
	}

	def "GetAvailableBrokers handles a successful response from trade it"() {
		given: "a successful response from trade it"
			Call<TradeItAvailableBrokersResponse> call = Mock(Call)
			1 * tradeItApi.getAvailableBrokers(_ as TradeItRequestWithKey) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItAvailableBrokersResponse> callback = args[0]
				TradeItAvailableBrokersResponse tradeItAvailableBrokersResponse = new TradeItAvailableBrokersResponse()

				Broker broker1 = Mock(Broker)
				broker1.shortName = "Broker1"
				broker1.longName = "My long Broker1"

				Broker broker2 = Mock(Broker)
				broker2.shortName = "Broker2"
				broker2.longName = "My long Broker2"

				Broker broker3 = Mock(Broker)
				broker3.shortName = "Broker3"
				broker3.longName = "My long Broker3"

				List<Broker> brokerList = [broker1, broker2, broker3]
				tradeItAvailableBrokersResponse.brokerList = brokerList
				tradeItAvailableBrokersResponse.sessionToken = "My session token"
				tradeItAvailableBrokersResponse.longMessages = null
				tradeItAvailableBrokersResponse.status = TradeItResponseStatus.SUCCESS
				Response<TradeItAvailableBrokersResponse> response = Response.success(tradeItAvailableBrokersResponse);
				callback.onResponse(call, response);
			}


		when: "calling getAvailableBrokers"
			int successCallBackCount = 0
			int errorCallBackCount = 0
			List<TradeItAvailableBrokersResponse.Broker> brokerList = null
			apiClient.getAvailableBrokers(new TradeItCallback<List<Broker>>() {
				@Override
				public void onSuccess(List<TradeItAvailableBrokersResponse.Broker> brokerListResponse) {
					successCallBackCount++
					brokerList = brokerListResponse
				}

				@Override
				public void onError(TradeItErrorResult error) {
					errorCallBackCount++
				}
			});

		then: "expects the successCallback called once"
			successCallBackCount == 1
			errorCallBackCount == 0

		and: "expects a list of 3 brokers"
			brokerList?.size() == 3
			brokerList[0].shortName == "Broker1"
			brokerList[0].longName == "My long Broker1"
			brokerList[1].shortName == "Broker2"
			brokerList[1].longName == "My long Broker2"
			brokerList[2].shortName == "Broker3"
			brokerList[2].longName == "My long Broker3"
	}

	def "GetAvailableBrokers handles an error response from trade it"() {
		given: "an error response from trade it"
			String shortMessage = "This is the short message for the session expired error"
			String longMessage = "This is the long message for the session expired error"
			Call<TradeItAvailableBrokersResponse> call = Mock(Call)
			1 * tradeItApi.getAvailableBrokers(_ as TradeItRequestWithKey) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItAvailableBrokersResponse> callback = args[0]
				TradeItAvailableBrokersResponse tradeItAvailableBrokersResponse = new TradeItAvailableBrokersResponse()
				tradeItAvailableBrokersResponse.code = TradeItErrorCode.TOKEN_INVALID_OR_EXPIRED
				tradeItAvailableBrokersResponse.status = TradeItResponseStatus.ERROR
				tradeItAvailableBrokersResponse.brokerList = null
				tradeItAvailableBrokersResponse.shortMessage = shortMessage
				tradeItAvailableBrokersResponse.longMessages = [longMessage]
				tradeItAvailableBrokersResponse.sessionToken = "My session token"

				Response<TradeItAvailableBrokersResponse> response = Response.success(tradeItAvailableBrokersResponse);
				callback.onResponse(call, response);
			}

		when: "calling getAvailableBrokers"
			int successCallBackCount = 0
			int errorCallBackCount = 0
			TradeItErrorResult errorResult = null
			apiClient.getAvailableBrokers(new TradeItCallback<List<TradeItAvailableBrokersResponse.Broker>>() {
				@Override
				public void onSuccess(List<TradeItAvailableBrokersResponse.Broker> brokerListResponse) {
					successCallBackCount++
				}

				@Override
				public void onError(TradeItErrorResult error) {
					errorCallBackCount++
					errorResult = error
				}
			});

		then: "expects the errorCallBackCount called once"
			successCallBackCount == 0
			errorCallBackCount == 1

		and: "expects the following error"
			errorResult.shortMessage == shortMessage
			errorResult.longMessages == [longMessage]
	}

	def "getOAuthLoginPopupUrlForMobile handles a successful response from trade it"() {
		given: "a successful response from trade it"
			int successCallBackCount = 0
			int errorCallBackCount = 0
			String mySpecialUrl = "http://myspecialoauthurl.com?oAuthTempToken=2bae6cc8-8d37-4b4a-ae5e-6bbde9209ac4"
			Call<TradeItAvailableBrokersResponse> call = Mock(Call)
			1 * tradeItApi.getOAuthLoginPopupUrlForMobile(_  as TradeItOAuthLoginPopupUrlForMobileRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItOAuthLoginPopupUrlForMobileResponse> callback = args[0]
				TradeItOAuthLoginPopupUrlForMobileResponse tradeItOAuthLoginPopupUrlForMobileResponse = new TradeItOAuthLoginPopupUrlForMobileResponse()
				tradeItOAuthLoginPopupUrlForMobileResponse.sessionToken = "My session token"
				tradeItOAuthLoginPopupUrlForMobileResponse.longMessages = null
				tradeItOAuthLoginPopupUrlForMobileResponse.status = TradeItResponseStatus.SUCCESS
				tradeItOAuthLoginPopupUrlForMobileResponse.oAuthURL = mySpecialUrl
				Response<TradeItOAuthLoginPopupUrlForMobileResponse> response = Response.success(tradeItOAuthLoginPopupUrlForMobileResponse);
				callback.onResponse(call, response);
			}

		when: "calling getOAuthLoginPopupUrlForMobile"
			TradeItErrorResult errorResult = null
			String oAuthUrlResult = null
			apiClient.getOAuthLoginPopupUrlForMobile("My broker 1", "my internal app callback", new TradeItCallback<String>() {

				@Override
				void onSuccess(String oAuthUrl) {
					successCallBackCount++
					oAuthUrlResult = oAuthUrl
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallBackCount++
				}
			})

		then: "expects the successCallback called once"
			successCallBackCount == 1
			errorCallBackCount == 0

		and: "expects the oAuthUrl to be populated"
			oAuthUrlResult == mySpecialUrl
	}

	def "getOAuthLoginPopupUrlForMobile handles an error response from trade it"() {
		given: "An error response from trade it"
			int successCallBackCount = 0
			int errorCallBackCount = 0
			TradeItErrorCode errorCode = TradeItErrorCode.BROKER_AUTHENTICATION_ERROR
			String shortMessage = "My error when linking broker"
			Call<TradeItAvailableBrokersResponse> call = Mock(Call)
			1 * tradeItApi.getOAuthLoginPopupUrlForMobile(_  as TradeItOAuthLoginPopupUrlForMobileRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItOAuthLoginPopupUrlForMobileResponse> callback = args[0]
				TradeItOAuthLoginPopupUrlForMobileResponse tradeItOAuthLoginPopupUrlForMobileResponse = new TradeItOAuthLoginPopupUrlForMobileResponse()
				tradeItOAuthLoginPopupUrlForMobileResponse.sessionToken = "My session token"
				tradeItOAuthLoginPopupUrlForMobileResponse.longMessages = null
				tradeItOAuthLoginPopupUrlForMobileResponse.status = TradeItResponseStatus.ERROR
				tradeItOAuthLoginPopupUrlForMobileResponse.code = errorCode
				tradeItOAuthLoginPopupUrlForMobileResponse.shortMessage = shortMessage
				tradeItOAuthLoginPopupUrlForMobileResponse.oAuthURL = null

				Response<TradeItOAuthLoginPopupUrlForMobileResponse> response = Response.success(tradeItOAuthLoginPopupUrlForMobileResponse);
				callback.onResponse(call, response);
			}

		when: "calling getOAuthLoginPopupUrlForMobile"
			TradeItErrorResult errorResult = null
			apiClient.getOAuthLoginPopupUrlForMobile("My broker 1", "my internal app callback", new TradeItCallback<String>() {

				@Override
				void onSuccess(String oAuthUrl) {
					successCallBackCount++
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallBackCount++
					errorResult = error
				}
			})

		then: "expects the errorCallback called once"
			successCallBackCount == 0
			errorCallBackCount == 1

		and: "expects a populated TradeItErrorResult"
			errorResult.getErrorCode() == errorCode
			errorResult.getShortMessage() == shortMessage

	}

	def "getOAuthLoginPopupUrlForWebApp handles a successful response from trade it"() {
		given: "a successful response from trade it"
			int successCallBackCount = 0
			int errorCallBackCount = 0
			String mySpecialUrl = "http://myspecialoauthurl.com?oAuthTempToken=2bae6cc8-8d37-4b4a-ae5e-6bbde9209ac4"
			Call<TradeItAvailableBrokersResponse> call = Mock(Call)
			1 * tradeItApi.getOAuthLoginPopupUrlForWebApp(_  as TradeItOAuthLoginPopupUrlForWebAppRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItOAuthLoginPopupUrlForWebAppResponse> callback = args[0]
				TradeItOAuthLoginPopupUrlForWebAppResponse tradeItOAuthLoginPopupUrlForWebAppResponse = new TradeItOAuthLoginPopupUrlForWebAppResponse()
				tradeItOAuthLoginPopupUrlForWebAppResponse.sessionToken = "My session token"
				tradeItOAuthLoginPopupUrlForWebAppResponse.longMessages = null
				tradeItOAuthLoginPopupUrlForWebAppResponse.status = TradeItResponseStatus.SUCCESS
				tradeItOAuthLoginPopupUrlForWebAppResponse.oAuthURL = mySpecialUrl
				Response<TradeItOAuthLoginPopupUrlForWebAppResponse> response = Response.success(tradeItOAuthLoginPopupUrlForWebAppResponse);
				callback.onResponse(call, response);
			}

		when: "calling getOAuthLoginPopupUrlForWebApp"
			TradeItErrorResult errorResult = null
			String oAuthUrlResult = null
			apiClient.getOAuthLoginPopupUrlForWebApp("My broker 1", new TradeItCallback<String>() {

				@Override
				void onSuccess(String oAuthUrl) {
					successCallBackCount++
					oAuthUrlResult = oAuthUrl
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallBackCount++
				}
			})

		then: "expects the successCallback called once"
			successCallBackCount == 1
			errorCallBackCount == 0

		and: "expects the oAuthUrl to be populated"
			oAuthUrlResult == mySpecialUrl
	}

	def "getOAuthLoginPopupUrlForWebApp handles an error response from trade it"() {
		given: "An error response from trade it"
			int successCallBackCount = 0
			int errorCallBackCount = 0
			TradeItErrorCode errorCode = TradeItErrorCode.BROKER_AUTHENTICATION_ERROR
			String shortMessage = "My error when linking broker"
			Call<TradeItAvailableBrokersResponse> call = Mock(Call)
			1 * tradeItApi.getOAuthLoginPopupUrlForWebApp(_  as TradeItOAuthLoginPopupUrlForWebAppRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItOAuthLoginPopupUrlForWebAppResponse> callback = args[0]
				TradeItOAuthLoginPopupUrlForWebAppResponse tradeItOAuthLoginPopupUrlForWebAppResponse = new TradeItOAuthLoginPopupUrlForWebAppResponse()
				tradeItOAuthLoginPopupUrlForWebAppResponse.sessionToken = "My session token"
				tradeItOAuthLoginPopupUrlForWebAppResponse.longMessages = null
				tradeItOAuthLoginPopupUrlForWebAppResponse.status = TradeItResponseStatus.ERROR
				tradeItOAuthLoginPopupUrlForWebAppResponse.code = errorCode
				tradeItOAuthLoginPopupUrlForWebAppResponse.shortMessage = shortMessage
				tradeItOAuthLoginPopupUrlForWebAppResponse.oAuthURL = null

				Response<TradeItOAuthLoginPopupUrlForWebAppResponse> response = Response.success(tradeItOAuthLoginPopupUrlForWebAppResponse);
				callback.onResponse(call, response);
			}

		when: "calling getOAuthLoginPopupUrlForWebApp"
			TradeItErrorResult errorResult = null
			apiClient.getOAuthLoginPopupUrlForWebApp("My broker 1", new TradeItCallback<String>() {

				@Override
				void onSuccess(String oAuthUrl) {
					successCallBackCount++
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallBackCount++
					errorResult = error
				}
			})

		then: "expects the errorCallback called once"
			successCallBackCount == 0
			errorCallBackCount == 1

		and: "expects a populated TradeItErrorResult"
			errorResult.getErrorCode() == errorCode
			errorResult.getShortMessage() == shortMessage

	}

	def "getOAuthLoginPopupUrlForTokenUpdate handles a successful response from trade it"() {
		given: "a successful response from trade it"
			int successCallBackCount = 0
			int errorCallBackCount = 0
			String mySpecialUrl = "http://myspecialoauthurl.com?oAuthTempToken=2bae6cc8-8d37-4b4a-ae5e-6bbde9209ac4"
			Call<TradeItOAuthLoginPopupUrlForTokenUpdateResponse> call = Mock(Call)
			1 * tradeItApi.getOAuthLoginPopupURLForTokenUpdate(_  as TradeItOAuthLoginPopupUrlForTokenUpdateRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItOAuthLoginPopupUrlForTokenUpdateResponse> callback = args[0]
				TradeItOAuthLoginPopupUrlForTokenUpdateResponse tradeItOAuthLoginPopupUrlForTokenUpdateResponse = new TradeItOAuthLoginPopupUrlForTokenUpdateResponse()
				tradeItOAuthLoginPopupUrlForTokenUpdateResponse.sessionToken = "My session token"
				tradeItOAuthLoginPopupUrlForTokenUpdateResponse.longMessages = null
				tradeItOAuthLoginPopupUrlForTokenUpdateResponse.status = TradeItResponseStatus.SUCCESS
				tradeItOAuthLoginPopupUrlForTokenUpdateResponse.oAuthURL = mySpecialUrl
				Response<TradeItOAuthLoginPopupUrlForTokenUpdateResponse> response = Response.success(tradeItOAuthLoginPopupUrlForTokenUpdateResponse);
				callback.onResponse(call, response);
			}

		when: "calling getOAuthLoginPopupForTokenUpdateUrl"
			String oAuthUrlResult = null
			apiClient.getOAuthLoginPopupUrlForTokenUpdate("My broker 1", "userId", "my internal app callback", new TradeItCallback<String>() {

				@Override
				void onSuccess(String oAuthUrl) {
					successCallBackCount++
					oAuthUrlResult = oAuthUrl
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallBackCount++
				}
			})

		then: "expects the successCallback called once"
			successCallBackCount == 1
			errorCallBackCount == 0

		and: "expects the oAuthUrl to be populated"
			oAuthUrlResult == mySpecialUrl
	}

	def "linkBrokerWithOauthVerifier handles a successful response from trade it"() {
		given: "a successful response from trade it"
			String myUserId = "My trade it userId"
			String myUserToken = "My trade it userToken"
			int successCallBackCount = 0
			int errorCallBackCount = 0
			Call<TradeItOAuthAccessTokenResponse> call = Mock(Call)
			1 * tradeItApi.getOAuthAccessToken(_  as TradeItOAuthAccessTokenRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItOAuthAccessTokenResponse> callback = args[0]
				TradeItOAuthAccessTokenResponse tradeItOAuthAccessTokenResponse = new TradeItOAuthAccessTokenResponse()
				tradeItOAuthAccessTokenResponse.sessionToken = "My session token"
				tradeItOAuthAccessTokenResponse.longMessages = null
				tradeItOAuthAccessTokenResponse.status = TradeItResponseStatus.SUCCESS
				tradeItOAuthAccessTokenResponse.userId = myUserId
				tradeItOAuthAccessTokenResponse.userToken = myUserToken
				tradeItOAuthAccessTokenResponse.broker = "My broker 1"
				Response<TradeItLinkLoginResponse> response = Response.success(tradeItOAuthAccessTokenResponse);
				callback.onResponse(call, response);
			}


		when: "calling linkBrokerWithOauthVerifier"
			TradeItLinkedLogin linkedLogin = null
			apiClient.linkBrokerWithOauthVerifier("My oAuthVerifier", new TradeItCallback<TradeItLinkedLogin>() {

				@Override
				void onSuccess(TradeItLinkedLogin linkedLoginResult) {
					successCallBackCount++
					linkedLogin = linkedLoginResult
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallBackCount++
				}
			})

		then: "expects the successCallback called once"
			successCallBackCount == 1
			errorCallBackCount == 0

		and: "expects a linkedBroker containing userId and userToken"
			linkedLogin.userId == myUserId
			linkedLogin.userToken == myUserToken
			linkedLogin.broker == "My broker 1"
	}

	def "unlinkBrokerAccount handles a successful response from trade it "() {
		given: "a linked broker to unlink"
			TradeItLinkedLogin linkedLogin = Mock(TradeItLinkedLogin)

		and: "a successful response from trade it"
			int successCallBackCount = 0
			int errorCallBackCount = 0
			Call<TradeItResponse> call = Mock(Call)
			1 * tradeItApi.unlinkLogin(_  as TradeItUnlinkLoginRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItResponse> callback = args[0]
				TradeItResponse tradeItResponse = new TradeItResponse()
				tradeItResponse.sessionToken = "My session token"
				tradeItResponse.longMessages = null
				tradeItResponse.status = TradeItResponseStatus.SUCCESS
				Response<TradeItResponse> response = Response.success(tradeItResponse);
				callback.onResponse(call, response);
			}

		when: "calling unlinkBrokerAccount"
			apiClient.unlinkBrokerAccount(linkedLogin, new TradeItCallback<TradeItResponse>() {
				@Override
				void onSuccess(TradeItResponse response) {
					successCallBackCount++
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallBackCount++
				}
			})

		then: "expects the successCallback called once"
			successCallBackCount == 1
			errorCallBackCount == 0
	}

	def "authenticate handles a successful response from trade it"() {
		given: "A linked login"
			TradeItLinkedLogin linkedLogin = Mock(TradeItLinkedLogin)

		and: "A successful response from trade it"
			int successCallBackCount = 0
			int securityQuestionCallbackCount = 0
			int errorCallBackCount = 0
			TradeItBrokerAccount account1 = new TradeItBrokerAccount();
			account1.accountNumber = "My account number 1"
			account1.name = "My account name 1"
			TradeItBrokerAccount account2 = new TradeItBrokerAccount();
			account2.accountNumber = "My account number 2"
			account2.name = "My account name 2"

			Call<TradeItResponse> call = Mock(Call)
			1 * tradeItApi.authenticate(_  as TradeItAuthenticateRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItAuthenticateResponse> callback = args[0]
				TradeItAuthenticateResponse tradeItAuthenticateResponse = new TradeItAuthenticateResponse()
				tradeItAuthenticateResponse.sessionToken = "My session token"
				tradeItAuthenticateResponse.longMessages = null
				tradeItAuthenticateResponse.status = TradeItResponseStatus.SUCCESS
				tradeItAuthenticateResponse.accounts = [account1, account2]
				Response<TradeItAuthenticateResponse> response = Response.success(tradeItAuthenticateResponse);
				callback.onResponse(call, response);
			}

		when: "calling authenticate"
			List<TradeItBrokerAccount> accountsResult = null

			apiClient.authenticate(linkedLogin, new AuthenticationCallback<TradeItAuthenticateResponse, TradeItSecurityQuestion>(null, apiClient) {
				@Override
				protected void onSuccessResponse(Response<TradeItAuthenticateResponse> response) {
					accountsResult = response.body().accounts
					successCallBackCount++
				}

				@Override
				protected void onErrorResponse(TradeItErrorResult errorResult) {
					errorCallBackCount++
				}
			})

		then: "expects the successCallback called once"
			successCallBackCount == 1
			errorCallBackCount == 0

		and: "expects a list of TradeItLinkedBrokerAccount"
			accountsResult == [account1, account2]
	}

	def "authenticate handles a successful response with a security question from trade it"() {
		given: "A linked login"
			TradeItLinkedLogin linkedLogin = Mock(TradeItLinkedLogin)

		and: "A successful response with security question from trade it"
			int successCallBackCount = 0
			int securityQuestionCallbackCount = 0
			int errorCallBackCount = 0

			Call<TradeItResponse> call = Mock(Call)
			1 * tradeItApi.authenticate(_  as TradeItAuthenticateRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItAuthenticateResponse> callback = args[0]
				TradeItAuthenticateResponse tradeItAuthenticateResponse = new TradeItAuthenticateResponse()
				tradeItAuthenticateResponse.sessionToken = "My session token"
				tradeItAuthenticateResponse.longMessages = null
				tradeItAuthenticateResponse.status = TradeItResponseStatus.INFORMATION_NEEDED
				tradeItAuthenticateResponse.securityQuestion = "My security question"
				Response<TradeItAuthenticateResponse> response = Response.success(tradeItAuthenticateResponse);
				callback.onResponse(call, response);
			}

		when: "calling authenticate"
			TradeItSecurityQuestion tradeItSecurityQuestion = null
			TradeItCallbackWithSecurityQuestion securityQuestion = new TradeItCallbackWithSecurityQuestionImpl<List<TradeItBrokerAccount>>() {

				@Override
				void onSuccess(List<TradeItBrokerAccount> accounts) {
					successCallBackCount++
				}

				@Override
				void onSecurityQuestion(TradeItSecurityQuestion securityQuestion) {
					tradeItSecurityQuestion = securityQuestion
					securityQuestionCallbackCount++
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallBackCount++
				}
			}
			apiClient.authenticate(linkedLogin, new AuthenticationCallback<TradeItAuthenticateResponse, TradeItSecurityQuestion>(securityQuestion, apiClient) {
				@Override
				protected void onSuccessResponse(Response<TradeItAuthenticateResponse> response) {
					successCallBackCount++
				}

				@Override
				protected void onErrorResponse(TradeItErrorResult errorResult) {
					errorCallBackCount++
				}
			})

		then: "expects the securityQuestionCallbackCount called once"
			successCallBackCount == 0
			securityQuestionCallbackCount == 1
			errorCallBackCount == 0

		and: "expects a security question"
			tradeItSecurityQuestion.securityQuestion == "My security question"
	}

	def "authenticate handles an error response from trade it"() {
		given: "A linked login"
			TradeItLinkedLogin linkedLogin = Mock(TradeItLinkedLogin)

		and: "An error response from trade it"
			int successCallBackCount = 0
			int securityQuestionCallbackCount = 0
			int errorCallBackCount = 0

			Call<TradeItResponse> call = Mock(Call)
			1 * tradeItApi.authenticate(_  as TradeItAuthenticateRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItAuthenticateResponse> callback = args[0]
				TradeItAuthenticateResponse tradeItAuthenticateResponse = new TradeItAuthenticateResponse()
				tradeItAuthenticateResponse.sessionToken = "My session token"
				tradeItAuthenticateResponse.longMessages = null
				tradeItAuthenticateResponse.status = TradeItResponseStatus.ERROR
				tradeItAuthenticateResponse.shortMessage = "My short error message"
				tradeItAuthenticateResponse.longMessages = ["My long error message"]
				tradeItAuthenticateResponse.code = TradeItErrorCode.BROKER_AUTHENTICATION_ERROR

				Response<TradeItAuthenticateResponse> response = Response.success(tradeItAuthenticateResponse);
				callback.onResponse(call, response);
			}

		when: "calling authenticate"
			TradeItErrorResult tradeItErrorResult = null
			apiClient.authenticate(linkedLogin, new AuthenticationCallback<TradeItAuthenticateResponse, TradeItSecurityQuestion>(null, apiClient) {
				@Override
				protected void onSuccessResponse(Response<TradeItAuthenticateResponse> response) {
					successCallBackCount++
				}

				@Override
				protected void onErrorResponse(TradeItErrorResult errorResult) {
					tradeItErrorResult = errorResult
					errorCallBackCount++
				}
			})


		then: "expects the errorCallbackCount called once"
			successCallBackCount == 0
			errorCallBackCount == 1

		and: "expects a trade it error result"
			tradeItErrorResult.shortMessage == "My short error message"
			tradeItErrorResult.longMessages == ["My long error message"]
			tradeItErrorResult.errorCode == TradeItErrorCode.BROKER_AUTHENTICATION_ERROR
			tradeItErrorResult.httpCode == 200
	}

	def "previewOrder handles a successful response from trade it"() {
		given: "a preview request"
			TradeItPreviewStockOrEtfOrderRequest previewRequest = Mock(TradeItPreviewStockOrEtfOrderRequest)

		and: "a successful response from trade it"
			int successfulCallbackCount = 0
			int errorCallbackCount = 0

			Call<TradeItResponse> call = Mock(Call)
			1 * tradeItApi.previewStockOrEtfOrder(_  as TradeItPreviewStockOrEtfOrderRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItPreviewStockOrEtfOrderResponse> callback = args[0]
				TradeItPreviewStockOrEtfOrderResponse tradeItPreviewStockOrEtfOrderResponse = new TradeItPreviewStockOrEtfOrderResponse()
				tradeItPreviewStockOrEtfOrderResponse.sessionToken = "My session token"
				tradeItPreviewStockOrEtfOrderResponse.longMessages = null
				tradeItPreviewStockOrEtfOrderResponse.status = TradeItResponseStatus.REVIEW_ORDER
				tradeItPreviewStockOrEtfOrderResponse.orderId = "My Order Id"
				tradeItPreviewStockOrEtfOrderResponse.orderDetails = new OrderDetails()
				tradeItPreviewStockOrEtfOrderResponse.orderDetails.orderAction = "buy"
				tradeItPreviewStockOrEtfOrderResponse.orderDetails.orderSymbol = "My symbol"
				tradeItPreviewStockOrEtfOrderResponse.orderDetails.orderExpiration = "day"
				tradeItPreviewStockOrEtfOrderResponse.orderDetails.orderQuantity = 1
				tradeItPreviewStockOrEtfOrderResponse.orderDetails.orderPrice = "market"
				tradeItPreviewStockOrEtfOrderResponse.orderDetails.orderCommissionLabel = "MyCommissionLabel"

				Response<TradeItPreviewStockOrEtfOrderResponse> response = Response.success(tradeItPreviewStockOrEtfOrderResponse);
				callback.onResponse(call, response);
			}

		when: "calling preview order"
			TradeItPreviewStockOrEtfOrderResponse previewResponse = null
			apiClient.previewStockOrEtfOrder(previewRequest, new TradeItCallback<TradeItPreviewStockOrEtfOrderResponse>() {
				@Override
				void onSuccess(TradeItPreviewStockOrEtfOrderResponse response) {
					previewResponse = response
					successfulCallbackCount++
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallbackCount++
				}
			})

		then: "expect the sucess callback called"
			successfulCallbackCount == 1
			errorCallbackCount == 0

		and: "the preview response is correctly filled"
			previewResponse.status == TradeItResponseStatus.REVIEW_ORDER
			previewResponse.orderId == "My Order Id"
			previewResponse.orderDetails.orderAction == "buy"
			previewResponse.orderDetails.orderSymbol == "My symbol"
			previewResponse.orderDetails.orderExpiration == "day"
			previewResponse.orderDetails.orderQuantity == 1.0
			previewResponse.orderDetails.orderPrice == "market"
			previewResponse.orderDetails.orderCommissionLabel == "MyCommissionLabel"
	}

	def "previewStockOrEtfOrder handles an error response from trade it"() {
		given: "a preview request"
			TradeItPreviewStockOrEtfOrderRequest previewRequest = Mock(TradeItPreviewStockOrEtfOrderRequest)

		and: "an error response from trade it"
			int successfulCallbackCount = 0
			int errorCallbackCount = 0


			Call<TradeItResponse> call = Mock(Call)
			1 * tradeItApi.previewStockOrEtfOrder(_  as TradeItPreviewStockOrEtfOrderRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItPreviewStockOrEtfOrderResponse> callback = args[0]
				TradeItPreviewStockOrEtfOrderResponse tradeItPreviewStockOrEtfOrderResponse = new TradeItPreviewStockOrEtfOrderResponse()
				tradeItPreviewStockOrEtfOrderResponse.sessionToken = "My session token"
				tradeItPreviewStockOrEtfOrderResponse.shortMessage = "My short error message"
				tradeItPreviewStockOrEtfOrderResponse.longMessages = ["My long error message"]
				tradeItPreviewStockOrEtfOrderResponse.status = TradeItResponseStatus.ERROR
				tradeItPreviewStockOrEtfOrderResponse.code = TradeItErrorCode.BROKER_ACCOUNT_ERROR

				Response<TradeItPreviewStockOrEtfOrderResponse> response = Response.success(tradeItPreviewStockOrEtfOrderResponse);
				callback.onResponse(call, response);
			}

		when: "calling preview order"
			TradeItErrorResult errorResult = null
			apiClient.previewStockOrEtfOrder(previewRequest, new TradeItCallback<TradeItPreviewStockOrEtfOrderResponse>() {
				@Override
				void onSuccess(TradeItPreviewStockOrEtfOrderResponse response) {
					successfulCallbackCount++
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallbackCount++
					errorResult = error
				}
			})

		then: "expect the sucess callback called"
			successfulCallbackCount == 0
			errorCallbackCount == 1

		and: "the error is correctly populated"
			errorResult.errorCode == TradeItErrorCode.BROKER_ACCOUNT_ERROR
			errorResult.shortMessage == "My short error message"
			errorResult.longMessages == ["My long error message"]
			errorResult.httpCode == 200
	}

	def "placeOrder handles a successful response from trade it"() {
		given: "a successful response from trade it"
			int successfulCallbackCount = 0
			int errorCallbackCount = 0

			Call<TradeItResponse> call = Mock(Call)
			1 * tradeItApi.placeStockOrEtfOrder(_  as TradeItPlaceStockOrEtfOrderRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItPlaceStockOrEtfOrderResponse> callback = args[0]
				TradeItPlaceStockOrEtfOrderResponse tradeItPlaceStockOrEtfOrderResponse = new TradeItPlaceStockOrEtfOrderResponse()
				tradeItPlaceStockOrEtfOrderResponse.sessionToken = "My session token"
				tradeItPlaceStockOrEtfOrderResponse.longMessages = null
				tradeItPlaceStockOrEtfOrderResponse.status = TradeItResponseStatus.SUCCESS
				tradeItPlaceStockOrEtfOrderResponse.orderNumber = "My Order Id"
				tradeItPlaceStockOrEtfOrderResponse.orderInfo = new OrderInfo()
				tradeItPlaceStockOrEtfOrderResponse.orderInfo.action = "buy"
				tradeItPlaceStockOrEtfOrderResponse.orderInfo.symbol = "My symbol"
				tradeItPlaceStockOrEtfOrderResponse.orderInfo.expiration = "day"
				tradeItPlaceStockOrEtfOrderResponse.orderInfo.quantity = 1
				Price price  = new Price()
				price.type = "market"
				tradeItPlaceStockOrEtfOrderResponse.orderInfo.price = price


				Response<TradeItPlaceStockOrEtfOrderResponse> response = Response.success(tradeItPlaceStockOrEtfOrderResponse);
				callback.onResponse(call, response);
			}

		when: "calling place order"
			TradeItPlaceStockOrEtfOrderResponse placeOrderResponse = null
			apiClient.placeStockOrEtfOrder("My Order Id", new TradeItCallback<TradeItPlaceStockOrEtfOrderResponse>() {
				@Override
				void onSuccess(TradeItPlaceStockOrEtfOrderResponse response) {
					placeOrderResponse= response
					successfulCallbackCount++
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallbackCount++
				}
			})

		then: "expect the success callback called"
			successfulCallbackCount == 1
			errorCallbackCount == 0

		and: "the place order response is correctly filled"
			placeOrderResponse.status == TradeItResponseStatus.SUCCESS
			placeOrderResponse.orderNumber == "My Order Id"
			placeOrderResponse.orderInfo.action == "buy"
			placeOrderResponse.orderInfo.symbol == "My symbol"
			placeOrderResponse.orderInfo.expiration == "day"
			placeOrderResponse.orderInfo.quantity == 1.0
	}

	def "placeOrder handles an error response from trade it"() {
		given: "an error response from trade it"
			int successfulCallbackCount = 0
			int errorCallbackCount = 0
			Call<TradeItResponse> call = Mock(Call)
			1 * tradeItApi.placeStockOrEtfOrder(_  as TradeItPlaceStockOrEtfOrderRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItPlaceStockOrEtfOrderResponse> callback = args[0]
				TradeItPlaceStockOrEtfOrderResponse tradeItPlaceStockOrEtfOrderResponse = new TradeItPlaceStockOrEtfOrderResponse()
				tradeItPlaceStockOrEtfOrderResponse.sessionToken = "My session token"
				tradeItPlaceStockOrEtfOrderResponse.shortMessage = "My short error message"
				tradeItPlaceStockOrEtfOrderResponse.longMessages = ["My long error message"]
				tradeItPlaceStockOrEtfOrderResponse.status = TradeItResponseStatus.ERROR
				tradeItPlaceStockOrEtfOrderResponse.code = TradeItErrorCode.PARAMETER_ERROR

				Response<TradeItPreviewStockOrEtfOrderResponse> response = Response.success(tradeItPlaceStockOrEtfOrderResponse);
				callback.onResponse(call, response);
			}

		when: "calling preview order"
			TradeItErrorResult errorResult = null
			apiClient.placeStockOrEtfOrder("My Order Id", new TradeItCallback<TradeItPlaceStockOrEtfOrderResponse>() {
				@Override
				void onSuccess(TradeItPlaceStockOrEtfOrderResponse response) {
					successfulCallbackCount++
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallbackCount++
					errorResult = error
				}
			})

		then: "expect the sucess callback called"
			successfulCallbackCount == 0
			errorCallbackCount == 1

		and: "the error is correctly populated"
			errorResult.errorCode == TradeItErrorCode.PARAMETER_ERROR
			errorResult.shortMessage == "My short error message"
			errorResult.longMessages == ["My long error message"]
			errorResult.httpCode == 200
	}

	def "getAccountOverview handles a successful response from trade it"() {
		given: "An account number"
			String accountNumber = "MyAccountNumber"

		and: "a successful response from trade it"
			int successCallbackCount = 0
			int errorCallbackCount = 0

			Call<TradeItResponse> call = Mock(Call)
			1 * tradeItApi.getAccountOverview(_  as TradeItGetAccountOverviewRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItAccountOverviewResponse> callback = args[0]
				TradeItAccountOverviewResponse tradeItAccountOverviewResponse = new TradeItAccountOverviewResponse()
				TradeItAccountOverview tradeItAccountOverview = new TradeItAccountOverview()
				tradeItAccountOverview.availableCash = 1200.54
				tradeItAccountOverview.buyingPower = 2604.45
				tradeItAccountOverview.dayAbsoluteReturn = 100
				tradeItAccountOverview.dayPercentReturn = 0.45
				tradeItAccountOverview.totalAbsoluteReturn = -234.98
				tradeItAccountOverview.totalPercentReturn = -2.34
				tradeItAccountOverview.totalValue = 12983.34
				tradeItAccountOverviewResponse.accountOverview = tradeItAccountOverview
				tradeItAccountOverviewResponse.status = TradeItResponseStatus.SUCCESS
				Response<TradeItAccountOverviewResponse> response = Response.success(tradeItAccountOverviewResponse)
				callback.onResponse(call, response)

			}

		when: "calling refresh balance on the linked broker account"
			TradeItAccountOverviewResponse accountOverviewResponse = null
			apiClient.getAccountOverview(accountNumber, new TradeItCallback<TradeItAccountOverviewResponse>() {
				@Override
				void onSuccess(TradeItAccountOverviewResponse response) {
					accountOverviewResponse = response
					successCallbackCount++
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallbackCount++
				}
			})

		then: "expects the successCallback called once"
			successCallbackCount == 1
			errorCallbackCount == 0

		then: "expects accountOverviewResponse correctly populated"
			accountOverviewResponse.fxAccountOverview == null
			accountOverviewResponse.accountOverview.availableCash == 1200.54
			accountOverviewResponse.accountOverview.buyingPower == 2604.45
			accountOverviewResponse.accountOverview.dayAbsoluteReturn == 100
			accountOverviewResponse.accountOverview.dayPercentReturn == 0.45
			accountOverviewResponse.accountOverview.totalAbsoluteReturn == -234.98
			accountOverviewResponse.accountOverview.totalPercentReturn == -2.34
			accountOverviewResponse.accountOverview.totalValue == 12983.34

	}

	def "getAccountOverview handles an error response from trade it"() {
		given: "An account number"
			String accountNumber = "MyAccountNumber"

		and: "an error response from trade it api"
			int successCallbackCount = 0
			int errorCallbackCount = 0
			Call<TradeItResponse> call = Mock(Call)
			1 * tradeItApi.getAccountOverview(_  as TradeItGetAccountOverviewRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItAccountOverview> callback = args[0]
				TradeItAccountOverviewResponse tradeItAccountOverviewResponse = new TradeItAccountOverviewResponse()
				tradeItAccountOverviewResponse.code = TradeItErrorCode.SESSION_EXPIRED
				tradeItAccountOverviewResponse.status = TradeItResponseStatus.ERROR
				tradeItAccountOverviewResponse.shortMessage = "My short message"
				tradeItAccountOverviewResponse.longMessages = ["My long message"]

				Response<TradeItAccountOverviewResponse> response = Response.success(tradeItAccountOverviewResponse)
				callback.onResponse(call, response)

			}

		when: "calling getAccountOverview"
			TradeItErrorResult errorResult = null
			apiClient.getAccountOverview(accountNumber, new TradeItCallback<TradeItAccountOverview>() {
				@Override
				void onSuccess(TradeItAccountOverview response) {
					successCallbackCount++
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorResult = error
					errorCallbackCount++
				}
			})

		then: "expects the errorCallbackCount called once"
			successCallbackCount == 0
			errorCallbackCount == 1

		then: "expects error result correctly populated"
			errorResult.errorCode == TradeItErrorCode.SESSION_EXPIRED
			errorResult.shortMessage == "My short message"
			errorResult.longMessages == ["My long message"]
	}

	def "getPositions handles a successful response from trade it"() {
		given: "An account number"
			String accountNumber = "MyAccountNumber"

		and: "a successful response from trade it api"
			int successCallbackCount = 0
			int errorCallbackCount = 0
			TradeItPosition position1 = new TradeItPosition()
			position1.quantity = 12
			position1.symbol = "GE"
			position1.lastPrice = 29.84
			TradeItPosition position2 = new TradeItPosition()
			position2.quantity = 22
			position2.symbol = "AAPL"
			position2.lastPrice = 109.84

			Call<TradeItResponse> call = Mock(Call)
			1 * tradeItApi.getPositions(_  as TradeItGetPositionsRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItGetPositionsResponse> callback = args[0]
				TradeItGetPositionsResponse tradeItGetPositionsResponse = new TradeItGetPositionsResponse()
				tradeItGetPositionsResponse.positions = [position1, position2]
				tradeItGetPositionsResponse.status = TradeItResponseStatus.SUCCESS
				Response<TradeItGetPositionsResponse> response = Response.success(tradeItGetPositionsResponse)
				callback.onResponse(call, response)

			}

		when: "calling getPositions"
			List<TradeItPosition> positionsResult = null
			apiClient.getPositions(accountNumber, new TradeItCallback<List<TradeItPosition>>() {
				@Override
				void onSuccess(List<TradeItPosition> positions) {
					positionsResult = positions
					successCallbackCount++
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorCallbackCount++
				}
			})

		then: "expects the successCallback called once"
			successCallbackCount == 1
			errorCallbackCount == 0

		then: "expects positions to be returned"
			positionsResult == [position1, position2]

	}

	def "getPositions handles an error response from trade it"() {
		given: "An account number"
			String accountNumber = "MyAccountNumber"

		and: "an error response from trade it api"
			int successCallbackCount = 0
			int errorCallbackCount = 0
			Call<TradeItResponse> call = Mock(Call)
			1 * tradeItApi.getPositions(_  as TradeItGetPositionsRequest) >> call
			1 * call.enqueue(_) >> { args ->
				Callback<TradeItGetPositionsResponse> callback = args[0]
				TradeItGetPositionsResponse tradeItGetPositionsResponse = new TradeItGetPositionsResponse()
				tradeItGetPositionsResponse.code = TradeItErrorCode.SESSION_EXPIRED
				tradeItGetPositionsResponse.status = TradeItResponseStatus.ERROR
				tradeItGetPositionsResponse.shortMessage = "My short message"
				tradeItGetPositionsResponse.longMessages = ["My long message"]

				Response<TradeItGetPositionsResponse> response = Response.success(tradeItGetPositionsResponse)
				callback.onResponse(call, response)

			}

		when: "calling getPositions"
			TradeItErrorResult errorResult = null
			apiClient.getPositions(accountNumber, new TradeItCallback<List<TradeItPosition>>() {
				@Override
				void onSuccess(List<TradeItPosition> positions) {
					successCallbackCount++
				}

				@Override
				void onError(TradeItErrorResult error) {
					errorResult = error
					errorCallbackCount++
				}
			})

		then: "expects the errorCallbackCount called once"
			successCallbackCount == 0
			errorCallbackCount == 1

		then: "expects error result correctly populated"
			errorResult.errorCode == TradeItErrorCode.SESSION_EXPIRED
			errorResult.shortMessage == "My short message"
			errorResult.longMessages == ["My long message"]


	}
}
