package hms.kite.app.sms;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import hms.kite.app.util.ConfigUtil;
import hms.kite.samples.api.SdpException;
import hms.kite.samples.api.caas.ChargingRequestSender;
import hms.kite.samples.api.caas.messages.ChargingRequestResponse;
import hms.kite.samples.api.caas.messages.DirectDebitRequest;
import hms.kite.samples.api.sms.MoSmsListener;
import hms.kite.samples.api.sms.SmsRequestSender;
import hms.kite.samples.api.sms.messages.MoSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsReq;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import static hms.kite.app.sms.RequestUtil.*;
import static hms.kite.app.util.ConfigUtil.*;

public class SmsService implements MoSmsListener{

    private static final String invalidMessageFormatResponse = ConfigUtil.getDisplayMessage("invalid.message").or("Invalid message format.");
    private static final String successResponse = ConfigUtil.getDisplayMessage("donate.success").or("Donation successful");
    private static final String insufficientBalanceResponse = ConfigUtil.getDisplayMessage("insufficient.balance").or("Insufficient balance");
    private static final String chargingError = ConfigUtil.getDisplayMessage("charging.error").or("Charging Error.");
    private static final String systemError = ConfigUtil.getDisplayMessage("system.error").or("Unexpected system error occurred. Please try again later.");

    private static final String appId = getApplicationConfig("app.id");
    private static final String password = getApplicationConfig("app.password");
    private static final String currencyCode = getApplicationConfig("currency.code");
    private static final String payInsName = getApplicationConfig("pay.instrument.mobile.account.name");

    @Override
    public void init() {
    }

    @Override
    public void onReceivedSms(MoSmsReq moSmsReq) {
        try {
            SmsRequestSender smsRequestSender = new SmsRequestSender(new URL(getApplicationConfig("nbl.sms.endpoint")));
            final MtSmsReq mtSmsReq = getMtSmsReq(moSmsReq);
            smsRequestSender.sendSmsRequest(mtSmsReq);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SdpException e) {
            e.printStackTrace();
        }
    }

    private MtSmsReq getMtSmsReq(MoSmsReq moSmsReq) {

        final Optional<MoMessage> moMessage = getMoMessage(moSmsReq.getMessage());

        final boolean invalidMessage = !moMessage.isPresent() || !moMessage.get().getMessage().isPresent();
        if(invalidMessage) {
            return getMtSms(moSmsReq, invalidMessageFormatResponse);
        }

        final String message = moMessage.get().getMessage().get();
        Optional<Double> donateValue = Optional.absent();

        try {
            final Double aDouble = Double.valueOf(message.trim());
            donateValue = Optional.fromNullable(aDouble);
        } catch(NumberFormatException ex) {
            donateValue = Optional.absent();
        }

        if(!donateValue.isPresent()) {
            return getMtSms(moSmsReq, invalidMessageFormatResponse);
        }

        final String donationAmount = String.valueOf(donateValue.get());
        try {
            final ChargingRequestSender chargingRequestSender = new ChargingRequestSender(new URL(getApplicationConfig("nbl.cas.dd.endpoint")));
            final ChargingRequestResponse ddResponse = chargingRequestSender.sendChargingRequest(getChargingReq(moSmsReq, donationAmount));
            if(ddResponse.getStatusCode().equals("S1000")) {
                return getMtSms(moSmsReq, MessageFormat.format(successResponse, donationAmount));
            } else if(ddResponse.getStatusCode().equals("E1308")) {
                return getMtSms(moSmsReq, MessageFormat.format(insufficientBalanceResponse, donationAmount));
            } else {
                return getMtSms(moSmsReq, chargingError);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return getMtSms(moSmsReq, systemError);
        } catch (SdpException e) {
            e.printStackTrace();
            return getMtSms(moSmsReq, chargingError);
        }
    }

    private MtSmsReq getMtSms(MoSmsReq moSmsReq, String responseMessage) {
        final MtSmsReq mtSmsReq = new MtSmsReq();
        mtSmsReq.setApplicationId(appId);
        mtSmsReq.setPassword(password);
        mtSmsReq.setDestinationAddresses(Lists.newArrayList(moSmsReq.getSourceAddress()));
        mtSmsReq.setVersion(moSmsReq.getVersion());
        mtSmsReq.setEncoding(moSmsReq.getEncoding());
        mtSmsReq.setMessage(responseMessage);
        return mtSmsReq;
    }

    private DirectDebitRequest getChargingReq(MoSmsReq moSmsReq, String amount) {
        final DirectDebitRequest ddRequest = new DirectDebitRequest();
        ddRequest.setExternalTrxId("1234");
        ddRequest.setAmount(amount);
        ddRequest.setApplicationId(appId);
        ddRequest.setPassword(password);
        ddRequest.setSubscriberId(moSmsReq.getSourceAddress());
        ddRequest.setCurrency(currencyCode);
        final Optional<String> accountId = getAccountId(moSmsReq.getSourceAddress());
        if(accountId.isPresent()) {
            ddRequest.setAccountId(accountId.get());
        }
        ddRequest.setPaymentInstrumentName(payInsName);
        return ddRequest;
    }
}
