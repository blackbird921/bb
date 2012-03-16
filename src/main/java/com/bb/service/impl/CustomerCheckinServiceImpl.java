package com.bb.service.impl;

import com.bb.domain.CustomerCheckin;
import com.bb.reference.WeekStatus;
import com.bb.service.CustomerCheckinService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class CustomerCheckinServiceImpl implements CustomerCheckinService {
    @Override
    public WeekStatus getCurrentWeekStatus(Long cid) {
        List<CustomerCheckin> checkins = CustomerCheckin.findCustomerCheckinsByCustomerAndApproved(cid).getResultList();
        WeekStatus weekStatus = new WeekStatus();

        Calendar cal = Calendar.getInstance();
        weekStatus.setDaysLeft(1 + 7 - (cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek()));
        // get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, 1 + cal.getFirstDayOfWeek());
        weekStatus.setStartDate(cal.getTime());
        cal.add(Calendar.WEEK_OF_YEAR, 1);

        weekStatus.setEndDate(cal.getTime());

        for (CustomerCheckin checkin : checkins) {
            if (checkin.getStartDate() != null && checkin.getStartDate().after(weekStatus.getStartDate())) {
                weekStatus.setDaysCompleted(weekStatus.getDaysCompleted() + 1);
            }
        }
        return weekStatus;
    }

    public static void main(String[] args) {
        System.out.println(new CustomerCheckinServiceImpl().getCurrentWeekStatus(0L).getDaysLeft());
    }
}
