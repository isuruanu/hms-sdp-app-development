package hms.kite.samples.menu;

import hms.kite.samples.api.ussd.OperationType;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/3/14
 * Time: 4:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface UssdMenu {

    OperationType ussdOpType();

    Map<String, String> getMenuContext();

    String menuContent();

    UssdMenu resolveMenu(Map<String, String> context);
}
