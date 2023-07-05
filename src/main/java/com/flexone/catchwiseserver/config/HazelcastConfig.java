package com.flexone.catchwiseserver.config;

import com.flexone.catchwiseserver.domain.LakeMarker;
import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
import com.flexone.catchwiseserver.dto.LakeNameDTO;
import com.flexone.catchwiseserver.serializer.LakeMarkerSerializer;
import com.flexone.catchwiseserver.serializer.LakeNameSerializer;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class HazelcastConfig {

    @Bean
    public Config hazelCastConfig(){
        Config config = new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(
                        new MapConfig()
                                .setName("lakeMarkers")
                                .setEvictionConfig(
                                        new EvictionConfig()
                                                .setEvictionPolicy(EvictionPolicy.LFU)
                                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                                )
                );

        SerializerConfig lakeMarkerSerializerConfig = new SerializerConfig()
                .setImplementation(new LakeMarkerSerializer())
                .setTypeClass(LakeMarkerDTO.class);
        SerializerConfig lakeNameSerializerConfig = new SerializerConfig()
                .setImplementation(new LakeNameSerializer())
                .setTypeClass(LakeNameDTO.class);

        config.getSerializationConfig()
                .addSerializerConfig(lakeMarkerSerializerConfig)
                .addSerializerConfig(lakeNameSerializerConfig);

        return config;
    }

    @Bean
    public HazelcastInstance hazelcastInstance(Config hazelCastConfig) {
        return Hazelcast.newHazelcastInstance(hazelCastConfig);
    }

    @Bean
    public IMap<String, List<LakeMarkerDTO>> lakeMarkerCache(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("lakeMarkers");
    }

    @Bean
    public IMap<String, List<LakeNameDTO>> lakeNameCache(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("lakeNames");
    }
}
