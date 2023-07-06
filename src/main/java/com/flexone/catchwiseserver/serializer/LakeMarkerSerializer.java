package com.flexone.catchwiseserver.serializer;

import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
import com.flexone.catchwiseserver.repository.LakeMarkerProjection;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

public class LakeMarkerSerializer implements StreamSerializer {
    @Override
    public void write(ObjectDataOutput out, Object object) throws IOException {
        LakeMarkerProjection lakeMarkerProjection = (LakeMarkerProjection) object;

        double[] coordinates = new double[2];
        coordinates[0] = lakeMarkerProjection.getMarker().getX();
        coordinates[1] = lakeMarkerProjection.getMarker().getY();

        out.writeLong(lakeMarkerProjection.getLakeId());
        out.writeUTF(lakeMarkerProjection.getLakeName());
        out.writeUTF(lakeMarkerProjection.getCountyName());
        out.writeUTF(lakeMarkerProjection.getStateName());
        out.writeDoubleArray(coordinates);
    }

    @Override
    public Object read(ObjectDataInput in) throws IOException {
        LakeMarkerDTO lakeMarkerDTO = new LakeMarkerDTO();
        lakeMarkerDTO.setLakeId(in.readLong());
        lakeMarkerDTO.setLakeName(in.readUTF());
        lakeMarkerDTO.setCountyName(in.readUTF());
        lakeMarkerDTO.setStateName(in.readUTF());
        lakeMarkerDTO.setCoordinates(in.readDoubleArray());
        return lakeMarkerDTO;
    }

    @Override
    public int getTypeId() {
        return 2;
    }
}
