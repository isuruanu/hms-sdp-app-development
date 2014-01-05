package hms.kite.samples.util;

import hms.kite.samples.menu.UssdMenu;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/3/14
 * Time: 4:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class UssdSessionManager {
    private static final Map<String, UssdMenu> sessionStore = new ConcurrentHashMap<String, UssdMenu>();

    public static UssdMenu getCurrentUssdMenu(String sessionId) {
        return sessionStore.get(sessionId);
    }

    public static void setSession(String sessionId, UssdMenu ussdMenu) {
        sessionStore.put(sessionId, ussdMenu);
    }
}
