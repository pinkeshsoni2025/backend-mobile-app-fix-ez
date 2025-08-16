package com.coders.jwt.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.beans.factory.annotation.Value;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/service")
public class SmsController {
	 /*@Value("${spring.sms.twillioAcSID}")
	 private String twillioAcSID;
	 @Value("${spring.sms.twillioAcTOKEN}")
	 private String twillioAcTOKEN;*/

	//private String twillioAcSID="ACf4ed935794b7001734be2e9f2fb4f65f";
	//private String twillioAcTOKEN="527ce14d49661d7f7a47c0a5903961ad";

	private String twillioAcSID="";
	private String twillioAcTOKEN="";
	 
	@GetMapping(value = "/sendSMS")
	public ResponseEntity<String> sendSMS() {

		Twilio.init(twillioAcSID, twillioAcTOKEN);
		Twilio.setUsername(twillioAcSID);
		

		Message.creator(new PhoneNumber("+918088693873"),
				new PhoneNumber("+12319303965"), "Hello from EmployeeFS 📞").create();

		return new ResponseEntity<String>("Message send successfully", HttpStatus.OK);

	}

}
