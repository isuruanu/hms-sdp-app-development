package hms.kite.samples.repo.service;

import hms.kite.samples.repo.domain.Donor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: isuruanu
 * Date: 1/3/14
 * Time: 7:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class InMemoryRepositoryService implements RepositoryService {

    ConcurrentHashMap<String, Donor> donorHasMap = new ConcurrentHashMap<String, Donor>();

    @Override
    public Donor findDonorById(String donorId) {
        return donorHasMap.get(donorId);
    }

    @Override
    public void insertDonor(Donor donor) {
        donorHasMap.put(donor.getMsisdn(), donor);
    }
}
