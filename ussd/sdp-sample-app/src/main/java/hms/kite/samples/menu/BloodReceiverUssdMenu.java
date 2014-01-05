package hms.kite.samples.menu;

import hms.kite.samples.api.ussd.OperationType;
import hms.kite.samples.util.ConfigUtil;
import hms.kite.samples.util.ContextKeys;

import java.util.HashMap;
import java.util.Map;

import static hms.kite.samples.util.ContextKeys.*;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/3/14
 * Time: 7:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class BloodReceiverUssdMenu implements UssdMenu {


    Map<String, String> context;

    @Override
    public OperationType ussdOpType() {
        return OperationType.MT_CONT;
    }

    @Override
    public Map<String, String> getMenuContext() {
        if(context == null) {
            context = new HashMap<String, String>();
        }
        return context;
    }


    @Override
    public String menuContent() {
        return ConfigUtil.getConfig("blood.receiver.first.page");
    }

    @Override
    public UssdMenu resolveMenu(Map<String, String> context) {
        final String address = context.get(inputK);
        context.put(receiverAddress, address);
        return new ReceiverRegistrationSuccessMenu(address);
    }
}
