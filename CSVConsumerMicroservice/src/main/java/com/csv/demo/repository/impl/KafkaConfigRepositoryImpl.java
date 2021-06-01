package com.csv.demo.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.csv.demo.model.KafkaConfigModel;
import com.csv.demo.repository.KafkaConfigRepository;

@Repository
public class KafkaConfigRepositoryImpl implements KafkaConfigRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	
	@Override
	public void saveOffset(KafkaConfigModel model) {
		if(null==this.getConfig(model))
			mongoTemplate.insert(model);
		else {
			Query query = new Query();
			query.addCriteria(Criteria.where("consumerTopic").is(model.getConsumerTopic()));
			query.addCriteria(Criteria.where("consumerGroup").is(model.getConsumerGroup()));
			query.addCriteria(Criteria.where("partitionNumber").is(model.getPartitionNumber()));
			Update update = new Update();
			update.set("offset", model.getOffset());
			this.mongoTemplate.findAndModify(query, update, KafkaConfigModel.class);
		}
	}

	@Override
	public KafkaConfigModel getConfig(KafkaConfigModel model) {
		Query query = new Query();
		query.addCriteria(Criteria.where("consumerTopic").is(model.getConsumerTopic()));
		query.addCriteria(Criteria.where("consumerGroup").is(model.getConsumerGroup()));
		query.addCriteria(Criteria.where("partitionNumber").is(model.getPartitionNumber()));
		return this.mongoTemplate.findOne(query, KafkaConfigModel.class);

	}

}
