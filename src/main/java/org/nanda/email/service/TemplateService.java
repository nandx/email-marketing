package org.nanda.email.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONObject;
import org.nanda.email.dto.SendEmailBodyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class TemplateService {

	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private TemplateEngine templateEngine;

	public void sendEmailByTemplate(SendEmailBodyDTO body) {
		JSONObject json = new JSONObject(body);
		System.out.println("SendEmailBodyDTO : " + json.toString(4));

		if (!EmailValidator.getInstance().isValid(body.getEmailreceiver()))
			return;

		Context thymeleafContext = new Context();
		thymeleafContext.setVariable("fullname", body.getFullname());
		String htmlBody = templateEngine.process("email-templates/welcome-email-template", thymeleafContext);

		try {
			sendHtmlMessage(body.getEmailreceiver(), "Selamat Datang", htmlBody);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
		System.out.println("to : " + to);
		System.out.println("subject : " + subject);
		System.out.println("htmlBody : " + htmlBody);

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlBody, true);
		emailSender.send(message);
	}

}
