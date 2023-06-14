package com.flexone.catchwiseserver.util;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.locationtech.jts.geom.*;

public class JtsModule extends SimpleModule {
    public JtsModule() {
        this(new GeometryFactory());
    }

    public JtsModule(GeometryFactory geometryFactory) {

    }


    public void setupModule(Module.SetupContext context) {
        super.setupModule(context);
    }
}
