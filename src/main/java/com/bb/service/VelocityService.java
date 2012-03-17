package com.bb.service;

import com.bb.util.AutowiredLogger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class VelocityService {
    @AutowiredLogger
    private Logger logger;

    public VelocityService() throws Exception {
        Properties p = new Properties();
        p.setProperty("resource.loader", "file, class");
        p.setProperty("file.resource.loader.cache", "true");
        p.setProperty("file.resource.loader.modificationCheckInterval", "0");
        p.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        Velocity.init(p);
    }

    public String format(String classpathFullName, Map<String, Object> objects) {
        StringWriter email = new StringWriter();
        try {
            Template tp = Velocity.getTemplate(classpathFullName);
            VelocityContext context = new VelocityContext();
            for (Map.Entry<String, Object> entry: objects.entrySet()) {
                context.put(entry.getKey(), entry.getValue());
            }
            tp.merge(context, email);
        } catch (IOException ioe) {
            this.logger.error("Error merging data with template ", ioe);
        } catch (Exception e) {
            this.logger.error("Format template error ", e);
        }

        return email.toString();
    }


    public static void main(String[] args) throws Exception {
        System.out.println(new VelocityService().format("emails/emailContent.vm", new HashMap<String, Object>()));
    }
}

