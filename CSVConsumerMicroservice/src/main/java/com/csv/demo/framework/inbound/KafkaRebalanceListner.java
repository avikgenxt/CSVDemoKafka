package com.csv.demo.framework.inbound;

import java.util.Collection;
import java.util.Set;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.stereotype.Component;

import com.csv.demo.model.KafkaConfigModel;
import com.csv.demo.repository.KafkaConfigRepository;

@Component
public class KafkaRebalanceListner implements ConsumerAwareRebalanceListener{
	
	@Autowired
	KafkaConfigRepository kafkaConfigRepository;

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	Consumer<?, ?> consumer;

	public KafkaRebalanceListner(Consumer<?, ?> consumer) {
		this.consumer=consumer;
	}

	public KafkaRebalanceListner() {
	}


	@Override
	public void onPartitionsRevokedBeforeCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
		System.out.println("Calling on partition revoked before commit");
		partitions.forEach(partition ->{
			int partitionNumber = partition.partition();
			long offset = consumer.position(partition);
			Set<String> listOfConsumers= consumer.subscription();
			String consumerName = listOfConsumers.iterator().next();
			String consumerGroup = consumer.groupMetadata().groupId();
			System.out.println("Called for consumer -"+consumerName + " Consumer Group -"+consumerGroup + " Partition -"+ partitionNumber + "currently at offset-" + offset);
			
		});    
		
	}

	@Override
	public void onPartitionsRevokedAfterCommit(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
		System.out.println("Calling on partition revoked after commit");
		partitions.forEach(partition ->{
			int partitionNumber = partition.partition();
			long offset = consumer.position(partition);
			String topic = partition.topic();
			Set<String> listOfConsumers= consumer.subscription();
			String consumerName = listOfConsumers.iterator().next();
			String consumerGroup = consumer.groupMetadata().groupId();
			kafkaConfigRepository.saveOffset(new KafkaConfigModel(topic, consumerGroup,partitionNumber, offset));
			System.out.println("After commit Called for consumer -"+consumerName + " Consumer Group -"+consumerGroup + " Partition -"+ partitionNumber + "currently at offset-" + offset);
			consumer.commitSync();
		});
	}

	@Override
	public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {

		partitions.forEach(partition ->{
			int partitionNumber = partition.partition();
			String topic = partition.topic();
			long offset = consumer.position(partition);
			Set<String> listOfConsumers= consumer.subscription();
			String consumerName = listOfConsumers.iterator().next();
			String consumerGroup = consumer.groupMetadata().groupId();
			KafkaConfigModel config = kafkaConfigRepository.getConfig(new KafkaConfigModel(topic, consumerGroup,partitionNumber, 0L));
			if(null!=config) {
				consumer.seek(partition, config.getOffset() + 1);	
			}else
				consumer.seek(partition, offset);
		});
	}

	@Override
	public void onPartitionsLost(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
		partitions.forEach(partition ->{
			int partitionNumber = partition.partition();
			long offset = consumer.position(partition);
			String topic = partition.topic();
			Set<String> listOfConsumers= consumer.subscription();
			String consumerName = listOfConsumers.iterator().next();
			String consumerGroup = consumer.groupMetadata().groupId();
			kafkaConfigRepository.saveOffset(new KafkaConfigModel(topic, consumerGroup,partitionNumber, offset));
			System.out.println("After commit Called for consumer -"+consumerName + " Consumer Group -"+consumerGroup + " Partition -"+ partitionNumber + "currently at offset-" + offset);
			consumer.commitSync();
		});

	}

}