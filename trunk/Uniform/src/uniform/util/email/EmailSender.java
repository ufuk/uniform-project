package uniform.util.email;

import java.io.File;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	public Boolean send(String to, String subject, String text) {
		try {
			System.out.println("E-posta gönderiliyor...");
			
			String filePathAndName = this.getClass().getResource("/").getFile();
			filePathAndName = filePathAndName.replaceFirst("WEB-INF/classes/", "WEB-INF/smtp.cfg.xml");
			SmtpServerConfiguration ssc = new SmtpServerConfiguration(new File(filePathAndName));

			Session session = Session.getDefaultInstance(ssc.configure(), null);
			
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(ssc.getUser()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(text);

			Transport transport = session.getTransport("smtp");
			transport.connect(ssc.getHost(), ssc.getUser(), ssc.getPassword());
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			System.out.println("E-posta gönderildi.");
			return true;
		} catch (Exception e) {
			System.out.println("E-posta gönderilemedi! Açýklama: " + e.getMessage());
			return false;
			// e.printStackTrace();
		}		
	}
	
}
