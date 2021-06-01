package com.csv.demo.framework.inbound;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.serializer.JsonDeserializer;


/**
 * @author chakrabortya
 *
 */

@EnableKafka
@Configuration	
public class KafkaConsumerConfiguration {

	@Autowired
	private KafkaProperties kafkaProperties;
	
	@Autowired
	KafkaRebalanceListner kafkaRebalanceListner;
	
	 @Bean
     public AtomicReference<Consumer<?, ?>> consumerRef() {
         return new AtomicReference<>();
     }
	

	 
	@Bean
	public ConsumerFactory<String, Object> defaultConsumerFactory() {
			Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,"org.apache.kafka.clients.consumer.CooperativeStickyAssignor");
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,4);
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,150000);
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Object.class));
	}

	
	@Bean
	@ConditionalOnMissingBean(name = "RFBusinessEventsTrigger")
	public ConcurrentKafkaListenerContainerFactory<String, Object> defaultContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(defaultConsumerFactory());
		factory.setErrorHandler(new KafkaConsumerErrorHandler());
		/** AckMode.MANUAL_IMMEDIATE will commit offsets just as it gets the ack, without waiting for other events 
		 *  AckMode.MANUAL is similar to AckMode.BATCH, which means after the acknowledge() method is called on a message,
		 *  the system will wait till all the messages received by the poll() method have been acknowledged.
		 *  This could take a long time, depending on multiple factors. */
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		factory.getContainerProperties().setConsumerRebalanceListener(kafkaRebalanceListner); 
		factory.setConcurrency(1);
		factory.getContainerProperties().setPollTimeout(30000);
		return factory;
	}
	
}