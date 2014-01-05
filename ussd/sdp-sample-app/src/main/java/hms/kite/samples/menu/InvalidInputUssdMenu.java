package hms.kite.samples.menu;

import hms.kite.samples.api.ussd.OperationType;

import java.util.HashMap;
import java.util.Map;

import static hms.kite.samples.util.ConfigUtil.getConfig;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/3/14
 * Time: 5:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class InvalidInputUssdMenu implements UssdMenu{

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
        return getConfig("invalid.input");
    }

    @Override
    public UssdMenu resolveMenu(Map<String, String> context) {
        this.context = context;
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
