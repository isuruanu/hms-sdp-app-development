package hms.kite.app.sms;

import com.google.common.base.Optional;
import hms.kite.app.util.ConfigUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hms.kite.app.util.ConfigUtil.*;

public class RequestUtil {

    private static final Pattern moMsgPattern;
    private static final Pattern sourceAddressPattern;

    static {
        moMsgPattern = Pattern.compile(getApplicationConfig("message.template.regex"), Pattern.CASE_INSENSITIVE);
        sourceAddressPattern = Pattern.compile(getApplicationConfig("source.address.pattern"));
    }


    public static Optional<MoMessage> getMoMessage(String message) {
        final Matcher matcher = moMsgPattern.matcher(message);
        if(matcher.matches()) {
            return Optional.of(new MoMessage(matcher.group("keyword"), Optional.fromNullable(matcher.group("msg"))));
        } else {
            return Optional.absent();
        }
    }

    public static Optional<String> getAccountId(String sourceAddress) {
        final Matcher matcher = sourceAddressPattern.matcher(sourceAddress);
        if(matcher.matches()) {
            return Optional.of(matcher.group("msisdn"));
        }
        return Optional.absent();
    }
}