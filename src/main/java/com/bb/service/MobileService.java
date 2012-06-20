package com.bb.service;

import com.bb.domain.*;
import com.bb.reference.MobileRegisterList;
import com.bb.util.GpsDistanceCalc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MobileService {
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private LocationService locationService;

    public MobileRegisterList getRegisterList() {
        MobileRegisterList list = new MobileRegisterList();
        list.setCommits(ProductCommit.findAllProductCommits());
        list.setStakes(ProductStake.findAllProductStakes());
        list.setCards(Card.findAllCards());
        return list;
    }

    public boolean checkinStart(Long cid, Float lat, Float lon){
        boolean result = false;
        Location location = locationService.getCurrentLocation(lat, lon);
        Customer customer = Customer.findCustomer(cid);

        if (location != null && customer != null) {
            CustomerCheckin customerCheckin = new CustomerCheckin();
            customerCheckin.setCustomer(customer);
            customerCheckin.setIsApproved(false);
            customerCheckin.setStartDate(new Date());
            customerCheckin.setTimeLengthInMinute(0);
            customerCheckin.persist();
            result = true;
        }

        return result;
    }

    public boolean checkinKeepalive(Long cid, Float lat, Float lon, Long locationId){
        boolean result = true;


        return result;
    }

    public boolean checkinEnd(Long cid, Float lat, Float lon, Long locationId){
        boolean result = true;


        return result;
    }



    public static void main(String[] args) {
//        System.out.println(new CustomerProfitServiceImpl().(0L).getDaysLeft());
    }
}
