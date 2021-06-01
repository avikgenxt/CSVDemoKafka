package com.csv.demo.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csv.demo.framework.outbound.GenericProducer;
import com.csv.demo.model.ResponseMessage;
import com.csv.demo.model.UserEmail;
import com.csv.demo.model.UserPasswordRecovory;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CSVParserServiceImpl implements CSVParserService {

	@Value("${user-password-recovery-topic}")
	String userPasswordRecoveryTopic;

	@Value("${user-email-topic}")
	String userEmailTopic;

	
	@Override
	public ResponseMessage parseUserRecovery(MultipartFile csvFile) {

		try	 {

			CsvMapper csvMapper = new CsvMapper();

			CsvSchema schema = CsvSchema.emptySchema().withHeader().withLineSeparator("\n").withColumnSeparator(';'); 

			ObjectReader oReader = csvMapper.readerFor(UserPasswordRecovory.class).with(schema);
			try (Reader reader = new InputStreamReader(csvFile.getInputStream())) {
				MappingIterator<UserPasswordRecovory> userPassWordRecovery = oReader.readValues(reader);
				while (userPassWordRecovery.hasNext()) {
					UserPasswordRecovory current = userPassWordRecovery.next();

					GenericProducer producer = GenericProducer.getInstance();
					producer.send(Arrays.asList(userPasswordRecoveryTopic), null,UUID.randomUUID().toString(), current);

					log.info("values {}", current.toString());
				}
			}

		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
		return null;

	}
	
	
	@Override
	public ResponseMessage parseUserEmail(MultipartFile csvFile) {

		try	 {

			CsvMapper csvMapper = new CsvMapper();

			CsvSchema schema = CsvSchema.emptySchema().withHeader().withLineSeparator("\n").withColumnSeparator(';'); 

			ObjectReader oReader = csvMapper.readerFor(UserEmail.class).with(schema);

			try (Reader reader = new InputStreamReader(csvFile.getInputStream())) {
				MappingIterator<UserEmail> userEmail = oReader.readValues(reader);
				while (userEmail.hasNext()) {
					UserEmail current = userEmail.next();

					GenericProducer producer = GenericProducer.getInstance();
					producer.send(Arrays.asList(userEmailTopic), null,UUID.randomUUID().toString(), current);

					log.info("values {}", current.toString());
				}
			}

		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
		return null;

	}

}
