package hms.kite.samples.client;

import hms.kite.samples.api.SdpException;
import hms.kite.samples.api.ussd.MoUssdListener;
import hms.kite.samples.api.ussd.OperationType;
import hms.kite.samples.api.ussd.UssdRequestSender;
import hms.kite.samples.api.ussd.messages.MoUssdReq;
import hms.kite.samples.api.ussd.messages.MtUssdReq;
import hms.kite.samples.menu.UssdMenu;
import hms.kite.samples.menu.WelcomeUssdMenu;
import hms.kite.samples.util.ConfigUtil;
import hms.kite.samples.util.ContextKeys;
import hms.kite.samples.util.UssdSessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static hms.kite.samples.util.UssdSessionManager.*;

public class SampleUssdService implements MoUssdListener {

    UssdRequestSender ussdRequestSender;
    ConfigUtil configUtil;
    String applicationId = "APP_000202";
    String password = "password";

    @Override
    public void init() {
        try {
            ussdRequestSender = new UssdRequestSender(new URL("http://localhost:7000/ussd/send"));
            configUtil = new ConfigUtil();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void onReceivedUssd(MoUssdReq moUssdReq) {

        final MtUssdReq mtUssdReq = getMtUssdReq(moUssdReq);
        try {
            ussdRequestSender.sendUssdRequest(mtUssdReq);
        } catch (SdpException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private MtUssdReq getMtUssdReq(MoUssdReq moUssdReq) {
        final String sourceMsisdn = moUssdReq.getSourceAddress();
        final String sessionId = moUssdReq.getSessionId();
        final String encoding = moUssdReq.getEncoding();
        final String message = moUssdReq.getMessage();

        final MtUssdReq mtUssdReq = new MtUssdReq();
        mtResponseGenerator(moUssdReq, mtUssdReq);
        mtUssdReq.setApplicationId(applicationId);
        mtUssdReq.setDestinationAddress(sourceMsisdn);
        mtUssdReq.setEncoding(encoding);
        mtUssdReq.setSessionId(sessionId);


        return mtUssdReq;
    }

    public void mtResponseGenerator(MoUssdReq moUssdReq, MtUssdReq mtUssdReq) {

        final UssdMenu currentUssdMenu = getCurrentUssdMenu(moUssdReq.getSessionId());
        UssdMenu nextUssdMenu;

        if(currentUssdMenu == null) {
            nextUssdMenu = new WelcomeUssdMenu();
        } else {
            final Map<String,String> menuContext = currentUssdMenu.getMenuContext();
            menuContext.put(ContextKeys.inputK, moUssdReq.getMessage().trim());
            nextUssdMenu = currentUssdMenu.resolveMenu(menuContext);
        }

        setSession(moUssdReq.getSessionId(), nextUssdMenu);
        mtUssdReq.setMessage(nextUssdMenu.menuContent());
        mtUssdReq.setUssdOperation(nextUssdMenu.ussdOpType().getName());
    }

}
