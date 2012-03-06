// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bb.web;

import com.bb.domain.ref.RefPaymentType;
import com.bb.web.RefPaymentTypeController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect RefPaymentTypeController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String RefPaymentTypeController.create(@Valid RefPaymentType refPaymentType, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, refPaymentType);
            return "refpaymenttypes/create";
        }
        uiModel.asMap().clear();
        refPaymentType.persist();
        return "redirect:/refpaymenttypes/" + encodeUrlPathSegment(refPaymentType.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String RefPaymentTypeController.createForm(Model uiModel) {
        populateEditForm(uiModel, new RefPaymentType());
        return "refpaymenttypes/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String RefPaymentTypeController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("refpaymenttype", RefPaymentType.findRefPaymentType(id));
        uiModel.addAttribute("itemId", id);
        return "refpaymenttypes/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String RefPaymentTypeController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("refpaymenttypes", RefPaymentType.findRefPaymentTypeEntries(firstResult, sizeNo));
            float nrOfPages = (float) RefPaymentType.countRefPaymentTypes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("refpaymenttypes", RefPaymentType.findAllRefPaymentTypes());
        }
        return "refpaymenttypes/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String RefPaymentTypeController.update(@Valid RefPaymentType refPaymentType, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, refPaymentType);
            return "refpaymenttypes/update";
        }
        uiModel.asMap().clear();
        refPaymentType.merge();
        return "redirect:/refpaymenttypes/" + encodeUrlPathSegment(refPaymentType.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String RefPaymentTypeController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, RefPaymentType.findRefPaymentType(id));
        return "refpaymenttypes/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String RefPaymentTypeController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        RefPaymentType refPaymentType = RefPaymentType.findRefPaymentType(id);
        refPaymentType.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/refpaymenttypes";
    }
    
    void RefPaymentTypeController.populateEditForm(Model uiModel, RefPaymentType refPaymentType) {
        uiModel.addAttribute("refPaymentType", refPaymentType);
    }
    
    String RefPaymentTypeController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}