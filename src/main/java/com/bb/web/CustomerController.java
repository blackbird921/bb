package com.bb.web;

import com.bb.domain.Customer;
import com.bb.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@RequestMapping( "/customers" )
@Controller
@RooWebScaffold( path = "customers", formBackingObject = Customer.class )
public class CustomerController {

    @Autowired
    private CommonsMultipartResolver multipartResolver;
    @Autowired
    private ValidationService validationService;

    @RequestMapping( method = RequestMethod.POST, produces = "text/html" )
    public String create( @Valid Customer customer, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest ) {
        boolean hasError = false;
        if ( bindingResult.hasErrors() ) {
            hasError = true;
        }

        if ( validationService.existsUniqueValue( Customer.class, "username", customer.getUsername() ) ) {
            uiModel.addAttribute( "usernameUniqueError", Boolean.TRUE );
            hasError = true;
        }

        if ( validationService.existsUniqueValue( Customer.class, "email", customer.getEmail() ) ) {
            uiModel.addAttribute( "emailUniqueError", Boolean.TRUE );
            hasError = true;
        }

        if ( hasError ) {
            populateEditForm( uiModel, customer );
            return "customers/create";
        }

        uiModel.asMap().clear();
        customer.persist();

        try {
            MultipartFile filea = customer.getAvatar();
            System.out.println( "====1=========" );

            InputStream inputStream = null;
            OutputStream outputStream = null;
            if ( filea != null && filea.getSize() > 0 ) {
                String uploadPath = httpServletRequest.getSession().getServletContext().getRealPath( "/images/upload" );
//                String fileNewName = customer.getId() + filea.getOriginalFilename().substring( filea.getOriginalFilename().lastIndexOf( "." ), filea.getOriginalFilename().length() );
                String fileNewName = customer.getId() + ".png" ;
                String fullPathName = uploadPath + "/" + fileNewName;
                System.out.println( fullPathName );
                inputStream = filea.getInputStream();
                outputStream = new FileOutputStream( fullPathName );
                int readBytes = 0;
                byte[] buffer = new byte[8192];
                while ( ( readBytes = inputStream.read( buffer, 0, 8192 ) ) != -1 ) {
                    outputStream.write( buffer, 0, readBytes );
                }
                outputStream.close();
                inputStream.close();
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return "redirect:/customers/" + encodeUrlPathSegment( customer.getId().toString(), httpServletRequest );
    }
}
