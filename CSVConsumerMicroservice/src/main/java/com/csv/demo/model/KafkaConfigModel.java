package com.csv.demo.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Kafka_config")
public class KafkaConfigModel implements Serializable{

	String consumerTopic;
	String consumerGroup;
	int partitionNumber;
	long offset;

	private static final long serialVersionUID = 6797446040854115042L;

	public KafkaConfigModel() {
	}

	public String getConsumerTopic() {
		return consumerTopic;
	}

	public void setConsumerTopic(String consumerTopic) {
		this.consumerTopic = consumerTopic;
	}

	public String getConsumerGroup() {
		return consumerGroup;
	}

	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}

	public int getPartitionNumber() {
		return partitionNumber;
	}

	public void setPartitionNumber(int partitionNumber) {
		this.partitionNumber = partitionNumber;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consumerGroup == null) ? 0 : consumerGroup.hashCode());
		result = prime * result + ((consumerTopic == null) ? 0 : consumerTopic.hashCode());
		result = prime * result + (int) (offset ^ (offset >>> 32));
		result = prime * result + partitionNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KafkaConfigModel other = (KafkaConfigModel) obj;
		if (consumerGroup == null) {
			if (other.consumerGroup != null)
				return false;
		} else if (!consumerGroup.equals(other.consumerGroup))
			return false;
		if (consumerTopic == null) {
			if (other.consumerTopic != null)
				return false;
		} else if (!consumerTopic.equals(other.consumerTopic))
			return false;
		if (offset != other.offset)
			return false;
		if (partitionNumber != other.partitionNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KafkaConfigModel [consumerTopic=" + consumerTopic + ", consumerGroup=" + consumerGroup
				+ ", partitionNumber=" + partitionNumber + ", offset=" + offset + "]";
	}

	public KafkaConfigModel(String consumerTopic, String consumerGroup, int partitionNumber, long offset) {
		super();
		this.consumerTopic = consumerTopic;
		this.consumerGroup = consumerGroup;
		this.partitionNumber = partitionNumber;
		this.offset = offset;
	}




}