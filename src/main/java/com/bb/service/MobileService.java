package com.bb.service;

import com.bb.domain.*;
import com.bb.reference.CustomerCheckinEndType;
import com.bb.reference.MobileLocationListItem;
import com.bb.reference.MobileRegisterList;
import com.bb.util.AutowiredLogger;
import com.bb.util.GpsDistanceCalc;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MobileService {
    @AutowiredLogger
    private Logger logger;
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

    public CustomerCheckin checkinStart(Long cid, Float lat, Float lon){
        Location location = locationService.getCurrentLocation(lat, lon);
        logger.info("location:{}", location);
        Customer customer = Customer.findCustomer(cid);

        CustomerCheckin customerCheckin = new CustomerCheckin();
        if (location != null && customer != null) {
            customerCheckin.setCustomer(customer);
            customerCheckin.setIsApproved(false);
            customerCheckin.setStartDate(new Date());
            customerCheckin.setTimeLengthInMinute(0);
            customerCheckin.setLocation(location);
            customerCheckin.persist();

        }
        return customerCheckin;
    }

    public CustomerCheckin checkinKeepalive(Long cid, Double lat, Double lon, Long customerCheckinId){
        CustomerCheckin customerCheckin = CustomerCheckin.findCustomerCheckin(customerCheckinId);
        logger.info("customerCheckin:{}", customerCheckin);
        if (customerCheckin != null) {
            if (lat == 0D && lon == 0D) {
                customerCheckin.setEndType(CustomerCheckinEndType.No_Signal);
                customerCheckin.setEndDate(new Date());
            } else {
                Location location = customerCheckin.getLocation();
                double distance = GpsDistanceCalc.computeDistance(lat, lon, location.getLatitude(), location.getLongitude());
                logger.info("distance={}", distance);
                if (distance <= configurationService.getCheckinValidRange()) {
                    logger.info("updating customerCheckinId={}", customerCheckin.getId());
                    customerCheckin.setEndDate(new Date());
                    customerCheckin.setEndType(CustomerCheckinEndType.Keepalive);
                    if (!customerCheckin.getIsApproved()) {
                        customerCheckin.setIsApproved(customerCheckin.getEndDate().getTime() - customerCheckin.getStartDate().getTime() > 30 * 60 * 1000);
                    }
                }
            }
            customerCheckin.merge();
        }

        return customerCheckin;
    }

    public CustomerCheckin checkinEnd(Long cid, Double lat, Double lon, Long customerCheckinId){
        CustomerCheckin customerCheckin = CustomerCheckin.findCustomerCheckin(customerCheckinId);
        logger.info("customerCheckin:{}", customerCheckin);
        if (customerCheckin != null) {
            if (lat == 0D && lon == 0D) {
                customerCheckin.setEndType(CustomerCheckinEndType.No_Signal);
                customerCheckin.setEndDate(new Date());
            } else {
                Location location = customerCheckin.getLocation();
                double distance = GpsDistanceCalc.computeDistance(lat, lon, location.getLatitude(), location.getLongitude());
                logger.info("distance={}", distance);
                if (distance <= configurationService.getCheckinValidRange()) {
                    logger.info("updating customerCheckinId={}", customerCheckin.getId());
                    customerCheckin.setEndDate(new Date());
                    customerCheckin.setEndType(CustomerCheckinEndType.Check_Out);
                    if (!customerCheckin.getIsApproved()) {
                        customerCheckin.setIsApproved(customerCheckin.getEndDate().getTime() - customerCheckin.getStartDate().getTime() > 30 * 60 * 1000);
                    }
                }
            }
            customerCheckin.merge();
        }

        return customerCheckin;
    }



    public static void main(String[] args) {
//        System.out.println(new CustomerProfitServiceImpl().(0L).getDaysLeft());
    }
}
