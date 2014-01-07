package hms.kite.app.ussd.menu;

import hms.kite.samples.api.ussd.OperationType;

public class UssdMenu {
    private String menuId;
    private String message;
    private OperationType ussdOpType;

    public UssdMenu(String menuId, String message, OperationType ussdOpType) {
        this.menuId = menuId;
        this.message = message;
        this.ussdOpType = ussdOpType;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUssdOpType(OperationType ussdOpType) {
        this.ussdOpType = ussdOpType;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getMessage() {
        return message;
    }

    public OperationType getUssdOpType() {
        return ussdOpType;
    }
}
