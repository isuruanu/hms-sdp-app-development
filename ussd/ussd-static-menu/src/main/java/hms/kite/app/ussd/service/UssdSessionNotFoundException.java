package hms.kite.app.ussd.service;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/8/14
 * Time: 4:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class UssdSessionNotFoundException extends Exception {
    public UssdSessionNotFoundException() {
        super("Ussd session not found.");
    }
}
