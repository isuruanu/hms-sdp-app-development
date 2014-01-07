package hms.kite.app.ussd.service;

import hms.kite.app.ussd.menu.UssdMenu;
import hms.kite.samples.api.SdpException;
import hms.kite.samples.api.ussd.MoUssdListener;
import hms.kite.samples.api.ussd.UssdRequestSender;
import hms.kite.samples.api.ussd.messages.MoUssdReq;
import hms.kite.samples.api.ussd.messages.MoUssdResp;
import hms.kite.samples.api.ussd.messages.MtUssdReq;

import java.net.MalformedURLException;
import java.net.URL;

import static hms.kite.app.ussd.util.ConfigUtil.*;

public class UssdService implements MoUssdListener {

    private static final UssdMenuResolver ussdMenuResolver;
    private static final String nblUssdEndpoint = getApplicationConfig("nbl.ussd.endpoint");
    private static final String appId = getApplicationConfig("app.id");
    private static final String appPassword = getApplicationConfig("app.password");

    private UssdRequestSender ussdRequestSender;

    static {
        ussdMenuResolver = new UssdMenuResolver();
    }

    @Override
    public void init() {
        try {
            ussdRequestSender = new UssdRequestSender(new URL(nblUssdEndpoint));
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void onReceivedUssd(MoUssdReq moUssdReq) {
        final String sessionId = moUssdReq.getSessionId();
        final UssdMenu ussdMenu = ussdMenuResolver.resolve(sessionId, moUssdReq.getMessage().trim());
        try {
            final MtUssdReq mtUssdReq = getMtUssdReq(moUssdReq, ussdMenu);

            ussdRequestSender.sendUssdRequest(mtUssdReq);
        } catch (SdpException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private MtUssdReq getMtUssdReq(MoUssdReq moUssdReq, UssdMenu ussdMenu) {
        final MtUssdReq mtUssdReq = new MtUssdReq();
        mtUssdReq.setApplicationId(appId);
        mtUssdReq.setPassword(appPassword);
        mtUssdReq.setDestinationAddress(moUssdReq.getSourceAddress());
        mtUssdReq.setMessage(ussdMenu.getMessage());
        mtUssdReq.setUssdOperation(ussdMenu.getUssdOpType().getName());
        mtUssdReq.setVersion(moUssdReq.getVersion());
        mtUssdReq.setEncoding(moUssdReq.getEncoding());
        mtUssdReq.setSessionId(moUssdReq.getSessionId());
        return mtUssdReq;
    }
}
