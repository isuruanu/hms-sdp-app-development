package hms.kite.samples.menu;

import hms.kite.samples.api.ussd.OperationType;

import java.util.HashMap;
import java.util.Map;

import static hms.kite.samples.util.ContextKeys.*;
import static hms.kite.samples.util.ConfigUtil.*;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/3/14
 * Time: 4:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class WelcomeUssdMenu implements UssdMenu{

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
        return getConfig("welcome.page");
    }

    @Override
    public UssdMenu resolveMenu(Map<String, String> context) {
        if(context.get(inputK).equals("1")) {
            return new BloodGroupUssdMenu();
        } else if(context.get(inputK).equals("2")) {
            return new BloodReceiverUssdMenu();
        }
        else {
            return new InvalidInputUssdMenu();
        }
    }
}
