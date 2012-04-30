package com.turnertech;



import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Mitch
 * Date: 4/29/12
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class imapExec {

    public static int exec(String user, String pass) {

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        int unread = 0;

        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", user, pass);
            //System.out.println(store);

            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_WRITE);
            unread = inbox.getUnreadMessageCount();

            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message messages[] = inbox.search(ft);


            for (Message message : messages) {
                //System.out.print(newline);
                message.setFlag(Flags.Flag.SEEN, true);
            }


        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            //Toast nspe = Toast.makeText(cleargmail.this, e.toString(), Toast.LENGTH_SHORT);
            // nspe.show();
            //System.exit(1);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Toast me = Toast.makeText(cleargmail.this, "Login Failure", Toast.LENGTH_SHORT);
            // me.show();
            //System.exit(2);
        }
        return unread;

    }
}
