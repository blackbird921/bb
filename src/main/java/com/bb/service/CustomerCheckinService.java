package com.bb.service;

import com.bb.domain.CustomerCheckin;
import com.bb.reference.WeekStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class CustomerCheckinService {
    int MIN_CHECKIN_MINUTES = 30;

    public WeekStatus getCurrentWeekStatus(Long cid) {
        return getPastWeekStatus(cid, 0);
    }

    public WeekStatus getPastWeekStatus(Long cid, Integer lastNWeek) {
        List<CustomerCheckin> checkins = CustomerCheckin.findCustomerCheckinsByCustomerAndApproved(cid).getResultList();
        WeekStatus weekStatus = new WeekStatus();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7*lastNWeek);

        weekStatus.setDaysLeft(1 + 7 - (cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek()));
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
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
        System.out.println(new CustomerCheckinService().getCurrentWeekStatus(0L).getDaysLeft());
    }
}
