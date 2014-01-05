package hms.kite.samples.menu;

import hms.kite.samples.api.ussd.OperationType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hms.kite.samples.util.ConfigUtil.getConfig;
import static hms.kite.samples.util.ContextKeys.inputK;
import static hms.kite.samples.util.ContextKeys.rhesusFactorK;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/3/14
 * Time: 5:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class RhesusFactorSelectionMenu implements UssdMenu {

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
        return getConfig("rhesus.factor");
    }

    @Override
    public UssdMenu resolveMenu(Map<String, String> context) {
        this.context = context;
        final String input = context.get(inputK).trim();
        final List<String> validInput = Arrays.asList("1", "2");

        final HashMap<String, String> rhesusFactor = new HashMap<String, String>();
        rhesusFactor.put("1", "POS(+)");
        rhesusFactor.put("2", "NEG(-)");

        if(validInput.contains(input)) {
            context.put(rhesusFactorK, rhesusFactor.get(input));
            return new DonorRegistrationSuccessMenu(context);
        }
        else {
            return new InvalidInputUssdMenu();
        }
    }
}
