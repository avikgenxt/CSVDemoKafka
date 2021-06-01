package com.csv.demo.repository;

import com.csv.demo.model.KafkaConfigModel;

public interface KafkaConfigRepository {

	public void saveOffset(KafkaConfigModel model);
	public KafkaConfigModel getConfig(KafkaConfigModel model);
}
