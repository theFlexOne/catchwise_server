package com.flexone.catchwiseserver.serializer;

import com.flexone.catchwiseserver.dto.LakeNameDTO;
import com.flexone.catchwiseserver.repository.LakeNameProjection;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

import java.io.IOException;

public class LakeNameSerializer implements StreamSerializer {
    @Override
    public void write(ObjectDataOutput out, Object object) throws IOException {
        LakeNameProjection lakeNameProjection = (LakeNameProjection) object;
        out.writeLong(lakeNameProjection.getId());
        out.writeUTF(lakeNameProjection.getName());
        out.writeUTF(lakeNameProjection.getCounty());
        out.writeUTF(lakeNameProjection.getState());
    }

    @Override
    public Object read(ObjectDataInput in) throws IOException {
        return new LakeNameDTO(
                in.readLong(),
                in.readUTF(),
                in.readUTF(),
                in.readUTF()
        );
    }

    @Override
    public int getTypeId() {
        return 2;
    }
}
