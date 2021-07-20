package org.nanda.email.controller.rest;

import org.nanda.email.dto.SendEmailBodyDTO;
import org.nanda.email.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendEmailRest {

	@Autowired
	private TemplateService templateService;

	@RequestMapping(path = "/api/send/template-email", produces = MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> sendTemplateEmail(@RequestBody SendEmailBodyDTO dto) {
		templateService.sendEmailByTemplate(dto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

}
