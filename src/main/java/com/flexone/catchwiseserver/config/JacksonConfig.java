package com.flexone.catchwiseserver.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.flexone.catchwiseserver.serializer.GeometrySerializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {


  @Bean
  public JtsModule jtsModule() {
    return new JtsModule();
  }

  private static class JtsModule extends SimpleModule {
    public JtsModule(){
      super("JtsModule", new Version(1, 0, 0, (String)null, "com.bedatadriven", "jackson-datatype-jts"));
      this.addSerializer(Geometry.class, new GeometrySerializer());
    }
    public JtsModule(GeometryFactory geometryFactory){
      super("JtsModule", new Version(1, 0, 0, (String)null, "com.bedatadriven", "jackson-datatype-jts"));
      this.addSerializer(Geometry.class, new GeometrySerializer());
    }

  }


}
