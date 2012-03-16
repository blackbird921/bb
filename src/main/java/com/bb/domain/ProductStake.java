package com.bb.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(sequenceName = "Product_Stake_SEQ")
public class ProductStake {

    @NotNull
    @Min(5L)
    @Max(50L)
    private Long stakes;

    private Boolean isActive;

    @Override
    public String toString() {
        return stakes.toString();
    }

}
