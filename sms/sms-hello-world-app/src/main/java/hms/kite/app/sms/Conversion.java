package hms.kite.app.sms;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/17/14
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Conversion {
    private final String source;
    private final String target;
    private final String value;

    public Conversion(String source, String target, String value) {
        this.source = source;
        this.target = target;
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getValue() {
        return value;
    }
}
