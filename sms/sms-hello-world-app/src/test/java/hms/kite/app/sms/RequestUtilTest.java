package hms.kite.app.sms;

import com.google.common.base.Optional;
import org.testng.Assert;
import org.testng.annotations.Test;

import static hms.kite.app.sms.RequestUtil.getAccountId;
import static hms.kite.app.sms.RequestUtil.getMoMessage;
import static org.testng.Assert.*;

public class RequestUtilTest {
    @Test
    public void testGetMoMessage1() throws Exception {
        final Optional<MoMessage> moMessage = getMoMessage("donate");
        assertTrue(moMessage.isPresent());
        assertEquals(moMessage.get().getKeyword(), "donate");
        assertFalse(moMessage.get().getMessage().isPresent());
    }


    @Test
    public void testGetMoMessage2() throws Exception {
        final Optional<MoMessage> moMessage = getMoMessage("donate              5");
        assertTrue(moMessage.isPresent());
        assertEquals(moMessage.get().getKeyword(), "donate");
        assertTrue(moMessage.get().getMessage().isPresent());
        assertEquals(moMessage.get().getMessage().get(), "5");
    }

    @Test
    public void testGetMoMessage3() throws Exception {
        final Optional<MoMessage> moMessage = getMoMessage("donate              5     ");
        assertTrue(moMessage.isPresent());
        assertEquals(moMessage.get().getKeyword(), "donate");
        assertTrue(moMessage.get().getMessage().isPresent());
        assertEquals(moMessage.get().getMessage().get(), "5     ");
    }

    @Test
    public void testGetMoMessage4() throws Exception {
        final Optional<MoMessage> moMessage = getMoMessage("DoNate              5     ");
        assertTrue(moMessage.isPresent());
        assertEquals(moMessage.get().getKeyword(), "DoNate");
        assertTrue(moMessage.get().getMessage().isPresent());
        assertEquals(moMessage.get().getMessage().get(), "5     ");
    }

    @Test
    public void testGetAccountId1() {
        final Optional<String> accountId = getAccountId("tel:kjalkdfjaoijfdaoi1987981789179871");
        assertTrue(accountId.isPresent());
        assertEquals(accountId.get(), "kjalkdfjaoijfdaoi1987981789179871");
    }

}

