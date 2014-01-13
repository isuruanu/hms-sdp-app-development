package hms.kite.app.sms;

import com.google.common.base.Optional;
import hms.kite.app.util.ConfigUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hms.kite.app.util.ConfigUtil.*;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/14/14
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class RequestUtil {

    private static final Pattern moMsgPattern;

    static {
        moMsgPattern = Pattern.compile(getApplicationConfig("message.template.regex"), Pattern.CASE_INSENSITIVE);
    }


    public static Optional<MoMessage> getMoMessage(String message) {
        final Matcher matcher = moMsgPattern.matcher(message);
        if(matcher.matches()) {
            return Optional.of(new MoMessage(matcher.group("keyword"), Optional.fromNullable(matcher.group("msg"))));
        } else {
            return Optional.absent();
        }
    }
}
