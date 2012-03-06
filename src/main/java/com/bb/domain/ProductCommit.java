package com.bb.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(sequenceName = "Product_Commit_SEQ")
public class ProductCommit {

    @NotNull
    @Min(1L)
    @Max(7L)
    private Long commits;

    private Boolean isActive;
}
