package com.bb.web;

import com.bb.domain.ref.RefSex;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import javax.persistence.TypedQuery;

/**
 * A central place to register application converters and formatters.
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

    @Override
    protected void installFormatters(FormatterRegistry registry) {
        super.installFormatters(registry);
        // Register application converters and formatters
    }

    public Converter<String, RefSex> getStringToRefSexConverter() {
        return new org.springframework.core.convert.converter.Converter<String, RefSex>() {
            public RefSex convert(String id) {
                System.out.println(11);
                TypedQuery<RefSex> list = RefSex.findRefSexesByName(id);
                if (list.getSingleResult() != null) {
                    return list.getSingleResult();
                }
                return null;
            }
        };
    }

}
