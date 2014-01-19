package hms.kite.app.sms;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hms.kite.app.util.ConfigUtil.*;

public class RequestUtil {

    private static final Pattern moMsgPattern;
    private static final Pattern sourceAddressPattern;
    private static final Pattern convertionPattern;

    private static final Map<String, Double> conversionMap;

    static {
        moMsgPattern = Pattern.compile(getApplicationConfig("message.template.regex"), Pattern.CASE_INSENSITIVE);
        sourceAddressPattern = Pattern.compile(getApplicationConfig("source.address.pattern"));
        convertionPattern = Pattern.compile(getApplicationConfig("valid.message"));


        conversionMap = new HashMap<String, Double>();
        populateConversionMap();
    }


    public static void populateConversionMap() {
        conversionMap.put("mtokm", Double.valueOf(0.001));
        conversionMap.put("kmtom", Double.valueOf(1000));
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

    public static Optional<Conversion> getConversion(String conversionMsg) {
        final Matcher matcher = convertionPattern.matcher(conversionMsg);
        if(matcher.matches()) {
            final Conversion conversion = new Conversion(matcher.group("source"), matcher.group("target"), matcher.group("value"));
            return Optional.of(conversion);
        }

        return Optional.absent();
    }

    public static Optional<Double> getConversionScale(String key) {
        return Optional.fromNullable(conversionMap.get(key));
    }
}