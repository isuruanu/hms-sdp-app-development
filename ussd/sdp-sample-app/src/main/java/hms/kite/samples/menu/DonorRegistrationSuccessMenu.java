package hms.kite.samples.menu;

import hms.kite.samples.api.ussd.OperationType;
import hms.kite.samples.repo.domain.Donor;
import hms.kite.samples.repo.service.InMemoryRepositoryService;
import hms.kite.samples.repo.service.RepositoryService;
import hms.kite.samples.util.ContextKeys;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static hms.kite.samples.util.ConfigUtil.getConfig;
import static hms.kite.samples.util.ContextKeys.*;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/3/14
 * Time: 5:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class DonorRegistrationSuccessMenu implements UssdMenu {

    Map<String, String> context;
    RepositoryService repositoryService;

    public DonorRegistrationSuccessMenu(Map<String, String> context) {
        this.context = context;
        repositoryService = new InMemoryRepositoryService();
    }

    @Override
    public OperationType ussdOpType() {
        return OperationType.MT_FIN;
    }

    @Override
    public Map<String, String> getMenuContext() {
        if(context == null) {
            context = new HashMap<String, String>();
        }
        return context;
    }

    @Override
    public String menuContent() {

        final Donor donor = new Donor(context.get(ContextKeys.msisdnK), context.get(bloodGroupK), context.get(rhesusFactorK));
        repositoryService.insertDonor(donor);

        final String format = MessageFormat.format(getConfig("donor.registration.success.page"), context.get(bloodGroupK), context.get(rhesusFactorK));
        return format.toString();
    }

    @Override
    public UssdMenu resolveMenu(Map<String, String> context) {
        this.context = context;
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
