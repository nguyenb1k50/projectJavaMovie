package com.myclass.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class MailService {
  
    @Autowired
    public JavaMailSender emailSender;
    private TemplateEngine templateEngine;
    
    public void sendSimpleMessage(
      String to, String subject, String text) {
     
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
      
    }
    
    @Autowired
    public MailService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
 
    public String buildActiveMailContent(String url, String userName) {
        Context context = new Context();
        context.setVariable("url", url);
        context.setVariable("username", userName);
        return templateEngine.process("RegisterMail", context);
    }
    
    public void sendActiveMailTemplate(String to, String subject, String url, String userName) {
    	MimeMessage message = emailSender.createMimeMessage();
        
        MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("cinebookingsystem@abc.com", "Cinema Booking System");
			helper.setTo(to);
	        helper.setSubject(subject);
	        String content = buildActiveMailContent(url,userName);
	        helper.setText(content, true);
		} catch (MessagingException | UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
        emailSender.send(message);
    }
    
    public String buildBookingDetailMailContent(List<String[]> detail) {
    	ArrayList<String> detail1 = new ArrayList<>(Arrays.asList(detail.get(0)));
        Context context = new Context();
        context.setVariable("bookingId", detail1.get(0));
        context.setVariable("seats", detail1.get(1));
        context.setVariable("cinemaName", detail1.get(2));
        context.setVariable("movieName", detail1.get(3));
        context.setVariable("date", detail1.get(5)+" "+detail1.get(4) );
        context.setVariable("userName", detail1.get(6));
        context.setVariable("address", detail1.get(7));
        context.setVariable("phone", detail1.get(8));
        context.setVariable("url", "nhat");
        return templateEngine.process("bookingDetailMail", context);
    }
    
    public void sendBookingDetailMailTemplate(String to, String subject,List<String[]> detail) {
    	MimeMessage message = emailSender.createMimeMessage();
        
        MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("cinebookingsystem@abc.com", "Cinema Booking System");
			helper.setTo(to);
	        helper.setSubject(subject);
	        String content = buildBookingDetailMailContent(detail);
	        helper.setText(content, true);
		} catch (MessagingException | UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
        emailSender.send(message);
    }
}
