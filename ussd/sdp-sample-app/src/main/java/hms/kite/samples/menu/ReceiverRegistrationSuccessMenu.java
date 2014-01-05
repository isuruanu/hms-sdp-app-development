package hms.kite.samples.menu;

import hms.kite.samples.api.ussd.OperationType;
import hms.kite.samples.util.ConfigUtil;

import java.text.MessageFormat;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/3/14
 * Time: 7:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReceiverRegistrationSuccessMenu implements UssdMenu {

    String address;

    public ReceiverRegistrationSuccessMenu(String address) {
        this.address = address;
    }

    @Override
    public OperationType ussdOpType() {
        return OperationType.MT_FIN;
    }

    @Override
    public Map<String, String> getMenuContext() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String menuContent() {
        final String message = ConfigUtil.getConfig("blood.receiver.final.page");
        return MessageFormat.format(message, address);
    }

    @Override
    public UssdMenu resolveMenu(Map<String, String> context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
