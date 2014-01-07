package hms.kite.app.ussd.service;

import com.google.common.base.Optional;
import hms.kite.app.ussd.menu.UssdMenu;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUssdSessionRepo implements UssdSessionRepo {

    Map<String, UssdMenu> sessionMap = new ConcurrentHashMap<String, UssdMenu>();

    @Override
    public void upsertSession(String sessionId, UssdMenu menu) {
        sessionMap.put(sessionId, menu);
    }

    @Override
    public void removeSession(String sessionId) throws UssdSessionNotFoundException {
        if(sessionMap.containsKey(sessionId))  {
            sessionMap.remove(sessionId);
        } else {
            throw new UssdSessionNotFoundException();
        }
    }

    @Override
    public Optional<UssdMenu> getUssdMenu(String sessionId) {
        return Optional.fromNullable(sessionMap.get(sessionId));
    }
}
