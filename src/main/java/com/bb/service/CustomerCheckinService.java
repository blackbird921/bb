package com.bb.service;

import com.bb.reference.WeekStatus;
import org.springframework.stereotype.Service;

@Service
public interface CustomerCheckinService {
    int MIN_CHECKIN_MINUTES = 30;
    
    WeekStatus getCurrentWeekStatus(Long cid);

}
