package com.csv.demo.framework.inbound;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.csv.demo.model.UserEmail;
import com.csv.demo.model.UserPasswordRecovory;
import com.csv.demo.service.UserDataProcessingService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chakrabortya
 *
 */

@Component
@Slf4j
public class KafkaConsumer {
	protected final Logger logger = LoggerFactory.getLogger(getClass());


	@Value("${user-password-recovery-topic}")
	String userPasswordRecoveryTopic;

	@Value("${user-email-topic}")
	String userEmailTopic;

	
	@Autowired
	UserDataProcessingService service;


	@KafkaListener(topics = "${user-password-recovery-topic}", groupId = "userPasswordRecoveryTopic-Group", containerFactory = "defaultContainerFactory") 
	public void rainforestEDGEConsumer(ConsumerRecord<String, Object> record, Acknowledgment ack) throws Exception {

		UserPasswordRecovory pojo;
		ObjectMapper mapper = new ObjectMapper();
		log.info("Value {}",record.value());

		try {
			pojo= mapper.convertValue(record.value(), UserPasswordRecovory.class);
			service.saveUserPasswordRecovery(pojo);
			ack.acknowledge();

		}  catch (Exception e) {
			logger.error("Exception is ::::::::::::::::: {}", e.getMessage());
		}
	}


	@KafkaListener(id="1", topics = "${user-email-topic}", groupId = "userEmailTopic--Group", containerFactory = "defaultContainerFactory") 
	public void BusinessEventConsumer(ConsumerRecord<String, Object> record, Acknowledgment ack) throws Exception {

		UserEmail pojo;
		ObjectMapper mapper = new ObjectMapper();
		log.info("Value {}",record.value());

		try {
			pojo= mapper.convertValue(record.value(), UserEmail.class);
			service.saveUserEmail(pojo);
			ack.acknowledge();

		}  catch (Exception e) {
			logger.error("Exception is ::::::::::::::::: {}", e.getMessage());
		}
	}

}