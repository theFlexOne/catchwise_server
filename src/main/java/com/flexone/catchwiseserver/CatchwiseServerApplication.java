package com.flexone.catchwiseserver;

import com.flexone.catchwiseserver.domain.LakeMarker;
import com.flexone.catchwiseserver.serializer.LakeMarkerSerializer;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SerializerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
public class CatchwiseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatchwiseServerApplication.class, args);
    }

}
