package hms.kite.samples.repo.service;

import hms.kite.samples.repo.domain.Donor;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/3/14
 * Time: 7:47 AM
 * To change this template use File | Settings | File Templates.
 */
public interface RepositoryService {

    Donor findDonorById(String donorId);

    void insertDonor(Donor donor);
}
