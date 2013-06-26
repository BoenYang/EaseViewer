package team.androidreader.mail;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import team.androidreader.mainview.FileInfo;

public class MailUtil {

	private int port = 25;
	private String server; // user server
	private String user;
	private String password;

	public MailUtil(String server, int port, String user, String passwd) {
		this.port = port;
		this.user = user;
		this.password = passwd;
		this.server = server;
	}

	public void sendEmail(String email, String subject, String body,
			List<FileInfo> fileList) {
		Properties props = new Properties();
		props.put("mail.smtp.host", server);
		props.put("mail.smtp.port", String.valueOf(port));
		props.put("mail.smtp.auth", "true");
		Transport transport = null;
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage msg = new MimeMessage(session);
		try {
			transport = session.getTransport("smtp");
			transport.connect(server, user, password);
			msg.setSentDate(new Date());
			InternetAddress fromAddress = null;
			fromAddress = new InternetAddress(user);
			msg.setFrom(fromAddress);
			InternetAddress[] toAddress = new InternetAddress[1];
			toAddress[0] = new InternetAddress(email);
			msg.setRecipients(Message.RecipientType.TO, toAddress);
			msg.setSubject(subject, "UTF-8");
			MimeMultipart multi = new MimeMultipart();
			BodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(body);
			multi.addBodyPart(textBodyPart);
			for (FileInfo fileInfo : fileList) {
				FileDataSource fds = new FileDataSource(fileInfo.absolutePath);
				BodyPart fileBodyPart = new MimeBodyPart();
				fileBodyPart.setDataHandler(new DataHandler(fds));
				String fileNameNew = MimeUtility.encodeText(fds.getName(),
						"utf-8", null);
				fileBodyPart.setFileName(fileNameNew);
				multi.addBodyPart(fileBodyPart);
			}
			msg.setContent(multi);
			msg.saveChanges();
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}