package hms.kite.app.sms;

import com.google.common.base.Optional;
import junit.framework.Assert;
import org.testng.annotations.Test;

import static hms.kite.app.sms.RequestUtil.getMoMessage;

public class RequestUtilTest {
    @Test
    public void testGetMoMessage() throws Exception {
        final Optional<MoMessage> moMessage = getMoMessage("donate 5");
        Assert.assertTrue(moMessage.isPresent());
    }
}
