package com.csv.demo.framework.inbound;

import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

/**
 * @author chakrabortya
 *
 */

public class KafkaConsumerErrorHandler implements ErrorHandler {


	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer,
			MessageListenerContainer container) {

		try {
			logger.info("KafkaConsumerErrorHandler Error Message is {} Error cause, ",thrownException.getMessage(), thrownException.getCause());
			String topicPartionOffset = thrownException.getMessage()
					.split("Error deserializing key/value for partition ")[1]
							.split(". If needed, please seek past the record to continue consumption.")[0];

			String topic = topicPartionOffset.split("-")[0];
			String offsetStr = topicPartionOffset.split("offset ")[1];
			String partitionStr = topicPartionOffset.split("-")[1].split(" at")[0];

			int partition = Integer.parseInt(partitionStr);
			long offset = Long.parseLong(offsetStr);

			logger.info("Skipping Topic= {} Partition= {} & Offset= {}. ", topic, partition, offset);

			TopicPartition topicPartition = new TopicPartition(topic, partition);
			consumer.seek(topicPartition, offset + 1);
		} catch (NumberFormatException e) {
			logger.error("NumberFormatException is ===== {}.",e.getMessage());
		} catch (Exception ex) {
			logger.error("Exception is ===== {}.",ex.getMessage());
		}
	}

	@Override
	public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) {

	}

	@Override
	public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord, Consumer<?, ?> consumer) {
		try {
			logger.info("KafkaConsumerErrorHandler:::::::22222::::::Error Message is {}.",e.getMessage());
			
			String topicPartionOffset = e.getMessage().split("Error deserializing key/value for partition ")[1]
					.split(". If needed, please seek past the record to continue consumption.")[0];

			String topic = topicPartionOffset.split("-")[0] + "-topic";
			String offsetStr = topicPartionOffset.split("offset ")[1];
			String partitionStr = topicPartionOffset.split("-")[1].split(" at")[0];

			int partition = Integer.parseInt(partitionStr);
			long offset = Long.parseLong(offsetStr);

			logger.info("Skipping Topic= {} Partition= {} & Offset= {}. ", topic, partition, offset);

			TopicPartition topicPartition = new TopicPartition(topic, partition);
			consumer.seek(topicPartition, offset + 1);
		} catch (NumberFormatException en) {
			logger.error("NumberFormatException is ===== {}.",en.getMessage());
		} catch (Exception ex) {
			logger.error("Exception is ===== {}.",ex.getMessage());
		}
	}
}