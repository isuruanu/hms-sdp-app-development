package hms.kite.app.ussd.service;

import com.google.common.base.Optional;
import hms.kite.app.ussd.menu.UssdMenu;

public interface UssdSessionRepo {

    void upsertSession(String sessionId, UssdMenu menu);

    void removeSession(String sessionId) throws UssdSessionNotFoundException;

    Optional<UssdMenu> getUssdMenu(String sessionId);
}
