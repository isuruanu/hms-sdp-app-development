package hms.kite.app.sms;

import com.google.common.collect.Lists;
import hms.kite.samples.api.SdpException;
import hms.kite.samples.api.sms.MoSmsListener;
import hms.kite.samples.api.sms.SmsRequestSender;
import hms.kite.samples.api.sms.messages.MoSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsReq;

import java.net.MalformedURLException;
import java.net.URL;

import static hms.kite.app.util.ConfigUtil.*;

public class SmsService implements MoSmsListener{
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SdpException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private MtSmsReq getMtSmsReq(MoSmsReq moSmsReq) {
        final MtSmsReq mtSmsReq = new MtSmsReq();
        mtSmsReq.setApplicationId(getApplicationConfig("app.id"));
        mtSmsReq.setPassword(getApplicationConfig("app.password"));
        mtSmsReq.setDestinationAddresses(Lists.newArrayList(moSmsReq.getSourceAddress()));
        mtSmsReq.setVersion(moSmsReq.getVersion());
        mtSmsReq.setEncoding(moSmsReq.getEncoding());
        mtSmsReq.setMessage("Hello Scan interval");
        return mtSmsReq;
    }

}
