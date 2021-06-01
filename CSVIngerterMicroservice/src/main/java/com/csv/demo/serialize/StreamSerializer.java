package com.csv.demo.serialize;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StreamSerializer<Object> implements Serializer<Object> {

	@Override
	public void configure(Map<String, ?> map, boolean b) {
		/**
		 * for future use
		 */
	}

	@Override
	public byte[] serialize(String topic, Object data) {
		byte[] ret = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			ret = mapper.writeValueAsString(data).getBytes();
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	
	public void close() {

	}

}
