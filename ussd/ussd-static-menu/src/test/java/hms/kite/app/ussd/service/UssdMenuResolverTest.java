package hms.kite.app.ussd.service;

import com.google.common.base.Optional;
import hms.kite.app.ussd.menu.UssdMenu;
import hms.kite.samples.api.ussd.OperationType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.testng.Assert.*;

@Test
public class UssdMenuResolverTest {

    @Test
    public void testGetPreviousMenuId() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final UssdMenuResolver ussdMenuResolver = new UssdMenuResolver();
        final UssdMenu ussdMenu = new UssdMenu("1.1", "You are now at option 1.1", OperationType.MO_CONT);
        final Method getPreviousMenuIdMethod = ussdMenuResolver.getClass().getDeclaredMethod("getPreviousMenuId", UssdMenu.class);
        getPreviousMenuIdMethod.setAccessible(true);
        final Object returnValue = getPreviousMenuIdMethod.invoke(ussdMenuResolver, ussdMenu);
        final Optional<String> nextMenuId = (Optional<String>) returnValue;
        assertTrue(nextMenuId.isPresent());
        assertEquals(nextMenuId.get(), "1");
    }
}
