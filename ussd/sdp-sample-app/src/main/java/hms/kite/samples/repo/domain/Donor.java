package hms.kite.samples.repo.domain;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/3/14
 * Time: 7:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class Donor {

    private final String msisdn;

    private final String bloodGroup;

    private final String rhesusFactor;

    public Donor(String msisdn, String bloodGroup, String rhesusFactor) {
        this.msisdn = msisdn;
        this.bloodGroup = bloodGroup;
        this.rhesusFactor = rhesusFactor;
    }


    public String getMsisdn() {
        return msisdn;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getRhesusFactor() {
        return rhesusFactor;
    }
}
