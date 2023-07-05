package com.flexone.catchwiseserver.util;

import com.bedatadriven.jackson.datatype.jts.parsers.*;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.flexone.catchwiseserver.serializer.GeometrySerializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

public class JtsModule extends SimpleModule {

    public JtsModule(){
        super("JtsModule", new Version(1, 0, 0, (String)null, "com.bedatadriven", "jackson-datatype-jts"));
        this.addSerializer(Geometry.class, new GeometrySerializer());
    }

    public JtsModule(GeometryFactory geometryFactory){
        super("JtsModule", new Version(1, 0, 0, (String)null, "com.bedatadriven", "jackson-datatype-jts"));
        this.addSerializer(Geometry.class, new GeometrySerializer());
    }

}
