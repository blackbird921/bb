package com.bb.service;

import com.bb.domain.CustomerProduct;
import com.bb.reference.WeekStatus;
import org.springframework.stereotype.Service;

@Service
public interface CustomerProductService {

    CustomerProduct getCurrentProduct(Long cid);

    CustomerProduct getFutureProduct(Long cid);

}
