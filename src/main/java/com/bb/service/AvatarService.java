package com.bb.service;

import com.bb.domain.Customer;
import com.bb.util.AutowiredLogger;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class AvatarService {
    String FILE_SUFFIX = ".png";

    @AutowiredLogger
    Logger logger;


    public String uploadAvatar(Customer customer, String realPath) {
        String fullPathName = null;
        try {
            MultipartFile file = customer.getAvatar();

            InputStream inputStream = null;
            OutputStream outputStream = null;
            logger.info("method of uploadAvatar...........");
            if (file != null && file.getSize() > 0) {
                String fileNewName = customer.getId() + FILE_SUFFIX;
                fullPathName = realPath + "/" + fileNewName;
                logger.info(fullPathName);
                inputStream = file.getInputStream();
                outputStream = new FileOutputStream(fullPathName);
                int readBytes = 0;
                byte[] buffer = new byte[8192];
                while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
                    outputStream.write(buffer, 0, readBytes);
                }
                outputStream.close();
                inputStream.close();
                customer.setHasAvatar(true);
                customer.setAvatar(null);
            }
        } catch (Exception e) {
            logger.error("Error in uploading avatar.", e);
        }

        return fullPathName;
    }
}
