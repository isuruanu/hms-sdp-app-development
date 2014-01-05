package hms.kite.samples.menu;

import hms.kite.samples.api.ussd.OperationType;
import hms.kite.samples.util.ContextKeys;

import java.util.*;

import static hms.kite.samples.util.ConfigUtil.getConfig;
import static hms.kite.samples.util.ContextKeys.inputK;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/3/14
 * Time: 5:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class BloodGroupUssdMenu implements UssdMenu {

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
        return getConfig("blood.groups");
    }

    @Override
    public UssdMenu resolveMenu(Map<String, String> context) {
        this.context = context;
        final String input = context.get(inputK).trim();
        final List<String> validInput = Arrays.asList("1", "2", "3", "4");

        final HashMap<String, String> bloodGroup = new HashMap<String, String>();
        bloodGroup.put("1", "A");
        bloodGroup.put("2", "B");
        bloodGroup.put("3", "AB");
        bloodGroup.put("4", "O");

        if(validInput.contains(input)) {
            context.put(ContextKeys.bloodGroupK, bloodGroup.get(input));
            return new RhesusFactorSelectionMenu();
        }

        else {
            return new InvalidInputUssdMenu();
        }
    }
}
