package com.bb.domain.ref;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(sequenceName = "Ref_Sex_SEQ")
public class RefSex {

    @NotNull
    @Size(max = 30)
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
