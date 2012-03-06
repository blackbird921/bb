package com.bb.domain.ref;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(sequenceName = "Ref_Payment_Type_SEQ")
public class RefPaymentType {

    @NotNull
    @Size(min = 2, max = 30)
    private String name;
}
