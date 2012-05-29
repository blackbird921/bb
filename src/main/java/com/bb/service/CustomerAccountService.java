package com.bb.service;

import com.bb.domain.CustomerProfit;
import com.bb.domain.CustomerTransaction;
import com.bb.reference.AccountInfo;
import com.bb.reference.AccountInfoItem;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomerAccountService {

    public AccountInfo getAccountInfo(Long cid) {
        List<CustomerProfit> profits = CustomerProfit.findAllProfitByCustomerId(cid);
        List<CustomerTransaction> transactions = CustomerTransaction.findAllTransactionByCustomerId(cid);

        AccountInfo accountInfo = new AccountInfo();

        for (CustomerProfit p : profits) {
            if (p.getAmount() != null && !p.getAmount().equals(0L)) {
                AccountInfoItem item = new AccountInfoItem();
                accountInfo.getItems().add(item);
                item.setBonus(p.getAmount());
                item.setTimestamp(p.getEndDate());
            }
        }

        for (CustomerTransaction t : transactions) {
            if (t.getAmount() != null && !t.getAmount().equals(0L)) {
                AccountInfoItem item = new AccountInfoItem();
                accountInfo.getItems().add(item);
                item.setTimestamp(t.getTransactionDate());
                item.setName(t.getTransactionType().getName());
                if (t.getAmount() > 0) {
                    item.setInAmount(t.getAmount());
                }else{
                    item.setOutAmount(t.getAmount());
                }
            }
        }

        Collections.sort(accountInfo.getItems(), new AccountInfoItem.OldFirstComparator());
//        System.out.println(accountInfo.getItems());

        float balance = 0.0f;
        for (AccountInfoItem i : accountInfo.getItems()) {
            balance += i.getBonus() + i.getInAmount() + i.getOutAmount();
            i.setBalance(balance);
//            System.out.println(balance+" "+i.getTimestamp());
        }

        accountInfo.setBalance(balance);

        Collections.sort(accountInfo.getItems(), new AccountInfoItem.NewFirstComparator());

        return accountInfo;
    }


    public static void main(String[] args) {
        AccountInfo ai = new AccountInfo();
        AccountInfoItem i1 = new AccountInfoItem();
        i1.setTimestamp(new Date(2007, 01, 01));
        ai.getItems().add(i1);

        AccountInfoItem i2 = new AccountInfoItem();
        i2.setTimestamp(new Date(2009, 01, 01));
        ai.getItems().add(i2);

        AccountInfoItem i3 = new AccountInfoItem();
        i3.setTimestamp(new Date(2008, 01, 01));
        ai.getItems().add(i3);


        Collections.sort(ai.getItems(), new AccountInfoItem.OldFirstComparator());
        System.out.println(ai.getItems());
    }
}
