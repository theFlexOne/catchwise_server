package com.flexone.catchwiseserver.serializer;

import com.flexone.catchwiseserver.domain.LakeMarker;
import com.flexone.catchwiseserver.dto.LakeMarkerDTO;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;
import java.util.List;

public class LakeMarkerSerializer implements StreamSerializer<List<LakeMarkerDTO>> {
    @Override
    public void write(ObjectDataOutput out, List<LakeMarkerDTO> lakeMarkerDTOS) throws IOException {
        out.writeInt(lakeMarkerDTOS.size());
        for (LakeMarkerDTO lakeMarkerDTO : lakeMarkerDTOS) {
            out.writeLong(lakeMarkerDTO.getLakeId());
            out.writeUTF(lakeMarkerDTO.getLakeName());
            out.writeDoubleArray(lakeMarkerDTO.getCoordinates());
            if (lakeMarkerDTO.getDistance() > 0)
                out.writeDouble(lakeMarkerDTO.getDistance());
        }
    }

    @Override
    public List<LakeMarkerDTO> read(ObjectDataInput in) throws IOException {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            Long lakeId = in.readLong();
            String lakeName = in.readUTF();
            double[] coordinates = in.readDoubleArray();
            double distance = in.readDouble();
            return List.of(new LakeMarkerDTO(lakeId, lakeName, coordinates, distance));
        }
        return null;
    }

    @Override
    public int getTypeId() {
        return 1;
    }
}