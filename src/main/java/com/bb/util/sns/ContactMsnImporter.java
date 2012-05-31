package com.bb.util.sns;


import net.sf.jml.MsnContact;
import net.sf.jml.MsnMessenger;
import net.sf.jml.MsnProtocol;
import net.sf.jml.event.MsnContactListAdapter;
import net.sf.jml.impl.MsnMessengerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Roger Chen
 */
public class ContactMsnImporter implements Runnable{

    private static final Log log = LogFactory.getLog(ContactMsnImporter.class);

    private String email;

    private String password;

    private MsnMessenger messenger;

    private Map<String, String> contacts = new HashMap<String, String>();

    private boolean isContactObtained;

    public ContactMsnImporter(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void run() {
        System.out.println("running");
        messenger = MsnMessengerFactory.createMsnMessenger(email, password);
        messenger.setSupportedProtocol(new MsnProtocol[]{MsnProtocol.MSNP11});
        messenger.login();

        messenger.addContactListListener(new MsnContactListAdapter() {
            public void contactListInitCompleted(MsnMessenger messenger) {
                listContacts();
            }
        });

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        messenger.logout();

    }

    private void listContacts() {
        MsnContact[] cons = messenger.getContactList().getContacts();
        isContactObtained = true;
        for (MsnContact con : cons) {
//            System.out.println(con.getDisplayName()+" "+con.getEmail());
            contacts.put(con.getEmail().getEmailAddress(), con.getDisplayName());
        }
        System.out.println("here........");
        System.out.println(isContactObtained());
    }

    public boolean isContactObtained() {
        return isContactObtained;
    }

    public Map<String, String> getContacts() {
        return contacts;
    }
    
    public static void main(String[] args) throws Exception {
        ContactMsnImporter importer = new ContactMsnImporter("zengq@hotmail.com", "");
        Thread thread = new Thread(importer);
        thread.start();
        for (int i = 0; i < 15; i++) {
            System.out.println(i);
            if (importer.isContactObtained()) {
                System.out.println(importer.getContacts());
                return;
            }
            Thread.sleep(1000);
        }
    }
}