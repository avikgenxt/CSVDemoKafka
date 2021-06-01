package com.csv.demo.framework.outbound;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csv.demo.model.GenericValue;

/**
 * 
 * @author chakrabortya
 *
 */


public class GenericProducer {

	private final Logger logger = LoggerFactory.getLogger(GenericProducer.class);
	Properties producerProperties = new Properties();
	private KafkaProducer<byte[], byte[]> producer;
	private volatile boolean shutDown = false;


	private static volatile GenericProducer genericProducerInstance;

	private GenericProducer() throws IOException {

		InputStream propInput = getClass().getClassLoader().getResourceAsStream("producer-config.properties");
		producerProperties.load(propInput);
		this.producer = new KafkaProducer<>(producerProperties);
		propInput.close();
        if (genericProducerInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        
	}
	
	 public static GenericProducer getInstance() throws IOException {
	        if (genericProducerInstance == null) { //if there is no instance available... create new one
	            synchronized (GenericProducer.class) {
	                if (genericProducerInstance == null) genericProducerInstance = new GenericProducer();
	            }
	        }

	        return genericProducerInstance;
	    }

	public void send(List<String> topics, Integer partition,String key,GenericValue value) {
		send(topics, partition, key, value, new DummyCallback() );
	}

	private void send(List<String> topics, Integer partition,String key,GenericValue value, Callback callback) {
		if (shutDown) {
			throw new RuntimeException("Producer is closed.");
		}


		try {
			for(String topic:topics) {
				ProducerRecord record; 
				if(partition ==null) 
					record = new ProducerRecord<>(topic, key, value); 
				else	
					record = new ProducerRecord<>(topic, partition, key, value);
				Future<RecordMetadata> future = producer.send(record);
				logger.info("future - {}",future);
				future.get();
			}
		}catch (ProducerFencedException | OutOfOrderSequenceException |  AuthorizationException e) { 
			e.printStackTrace();
		}
		catch (KafkaException e ) {
			e.printStackTrace();
			logger.error("Error while producing event for topic", e);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while producing event for topic", e);
		}
		
	}

	public void close() {
		shutDown = true;
		try {
			producer.close();
		} catch (Exception e) {
			logger.error("Exception occurred while stopping the producer", e);
		}
	}

	private class DummyCallback implements Callback {
		@Override
		public void onCompletion(RecordMetadata recordMetadata, Exception e) {
			if (e != null) {
				logger.error("Error while producing message to topic : {}", recordMetadata.topic(), e);
			}
			logger.debug("sent message to topic:{} partition:{}  offset:{}", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
		}
	}

}
