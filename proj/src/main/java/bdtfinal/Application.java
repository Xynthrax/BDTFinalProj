package bdtfinal;

import java.io.IOException;
import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerRecord;  
import org.apache.spark.SparkConf;  
import org.apache.spark.streaming.Durations;  
import org.apache.spark.streaming.api.java.JavaInputDStream;  
import org.apache.spark.streaming.api.java.JavaStreamingContext;  
import org.apache.spark.streaming.kafka010.ConsumerStrategies;  
import org.apache.spark.streaming.kafka010.KafkaUtils;  
import org.apache.spark.streaming.kafka010.LocationStrategies;  
import org.apache.kafka.common.serialization.StringDeserializer;

public class Application {
	
	public static class DtoConvertor {
        public static PopulationDTO convertToDto(String s) {
            final String[] data = s.trim().split(",");
            PopulationDTO dto = new PopulationDTO();
            dto.setId(data[0].trim());
            dto.setArea(data[13].trim());
            dto.setCountryName(data[2].trim());
            dto.setDensity(data[14].trim());
            dto.setGrowthRate(data[15].trim());
            dto.setPopulation2022(data[5].trim());
            dto.setPopulation2020(data[6].trim());
            dto.setPopulation2015(data[7].trim());
            dto.setPopulation1970(data[12].trim());
            dto.setWorldPopulationPercentage(data[7].trim());
            
            return dto;
        }
    }
	
	private static HBaseConn hbase;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		// Spark configuration
		SparkConf sparkConf = new SparkConf().setMaster("local").set("spark.serializer", "org.apache.spark.serializer.KryoSerializer").setAppName("AppName");  
        
		// Kafka properties
		Map<String, Object> kafkaParams = new HashMap<>();  
		kafkaParams.put("bootstrap.servers", "localhost:9092");  
		kafkaParams.put("key.deserializer", StringDeserializer.class);  
		kafkaParams.put("value.deserializer", StringDeserializer.class);  
		kafkaParams.put("group.id", "DEFAULT_GROUP_ID");  
		kafkaParams.put("auto.offset.reset", "latest");  
		kafkaParams.put("enable.auto.commit", false);
		
		// Hbase connection
		hbase = new HBaseConn();
		
		try(JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(6))) {  
			//JavaInputDStream<ConsumerRecord<String, String>> stream2 = KafkaUtils.createDirectStream(ssc, LocationStrategies.PreferConsistent(), 
					//ConsumerStrategies.<String, String>Subscribe(Arrays.asList("population-topic"), kafkaParams));
					
			//stream2.foreachRDD(a -> {
			//	System.out.println(a.collect());
			//	System.out.println();
			//});
			
			JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(
					ssc, 
					LocationStrategies.PreferConsistent(), 
					ConsumerStrategies.<String, String>Subscribe( Arrays.asList("population-topic") , kafkaParams)
					);
			
//			stream.foreachRDD(a -> {
//				System.out.println(a.collect());
//				System.out.println();
//			});
			
			stream.foreachRDD(rdd -> {
				System.out.println(rdd.collect());
				List<PopulationDTO> data = rdd.map(ConsumerRecord::value)
												.map(DtoConvertor::convertToDto)
												.collect();
				hbase.saveDataToHbase(data);
		    });
			ssc.start();  
			ssc.awaitTermination();
		}
	}  	
	
	

}
