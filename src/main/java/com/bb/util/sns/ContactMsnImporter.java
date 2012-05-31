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
        messenger = MsnMessengerFactory.createMsnMessenger(email, password);
        messenger.setSupportedProtocol(new MsnProtocol[]{MsnProtocol.MSNP11});
        messenger.login();

        messenger.addContactListListener(new MsnContactListAdapter() {
            public void contactListInitCompleted(MsnMessenger messenger) {
                listContacts();
            }
        });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        messenger.logout();

    }

    private void listContacts() {
        MsnContact[] cons = messenger.getContactList().getContacts();
        for (MsnContact con : cons) {
//            System.out.println(con.getDisplayName()+" "+con.getEmail());
            contacts.put(con.getEmail().getEmailAddress(), con.getDisplayName());
        }
        isContactObtained = true;
    }

    public boolean isContactObtained() {
        return isContactObtained;
    }



    public static void main(String[] args) throws Exception {
        new ContactMsnImporter("zengq@hotmail.com", "mubai7").run();
    }

}