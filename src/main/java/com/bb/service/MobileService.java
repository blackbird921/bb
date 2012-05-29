package com.bb.service;

import com.bb.domain.Card;
import com.bb.domain.CustomerProfit;
import com.bb.domain.ProductCommit;
import com.bb.domain.ProductStake;
import com.bb.reference.MobileRegisterList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MobileService {

    public MobileRegisterList getRegisterList() {
        MobileRegisterList list = new MobileRegisterList();
        list.setCommits(ProductCommit.findAllProductCommits());
        list.setStakes(ProductStake.findAllProductStakes());
        list.setCards(Card.findAllCards());
        return list;
    }

    public boolean checkin(Long cid, String gpsInfo){
        boolean result = false;


        return result;
    }

    public static void main(String[] args) {
//        System.out.println(new CustomerProfitServiceImpl().(0L).getDaysLeft());
    }
}
