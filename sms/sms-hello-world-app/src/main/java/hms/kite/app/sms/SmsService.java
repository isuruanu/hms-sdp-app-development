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
import java.util.logging.Logger;

import static hms.kite.app.sms.RequestUtil.*;
import static hms.kite.app.util.ConfigUtil.*;

public class SmsService implements MoSmsListener{

    private static final String invalidMessageFormatResponse = ConfigUtil.getDisplayMessage("invalid.message").or("Invalid message format.");
    private static final String successMessageFormatResponse = ConfigUtil.getDisplayMessage("welcome.message").or("Welcome.");

    private static final String appId = getApplicationConfig("app.id");
    private static final String password = getApplicationConfig("app.password");

    private static final Logger logger = Logger.getLogger("logger");

    @Override
    public void init() {
    }

    @Override
    public void onReceivedSms(MoSmsReq moSmsReq) {
        //
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

        final String response = convertUnits(moMessage.get());
        return getMtSms(moSmsReq, response);
    }

    private String convertUnits(MoMessage moMessage) {

        //TODO: complete the unit conversion logic : your code goes here ...
        logger.info(MessageFormat.format("Mo Msg keyword is = [{0}], message part is = [{1}]",
                moMessage.getKeyword(), moMessage.getMessage().or("N/A")));


        if(!moMessage.getMessage().isPresent()) {
            return invalidMessageFormatResponse;
        }

        final Optional<Conversion> conversionOpt = getConversion(moMessage.getMessage().get());
        if(!conversionOpt.isPresent()) {
            return invalidMessageFormatResponse;
        }

        final Conversion conversion = conversionOpt.get();
        final String conversionMapKey = conversion.getSource() + "to" + conversion.getTarget();

        final Optional<Double> conversionScaleOpt = getConversionScale(conversionMapKey);
        if(!conversionOpt.isPresent()) {
            return invalidMessageFormatResponse;
        }

        final Double aDouble = conversionScaleOpt.get();
        final double response = Integer.valueOf(conversion.getValue()) * aDouble;

        return "Your conversion response is " + response;
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
}
