package cn.soyumall.capacity.core.serializer;

import io.protostuff.*;
import io.protostuff.runtime.RuntimeSchema;

public class ProtoBufSerializer implements MqSerializer {
    public <T> byte[] serialize(T object) {
        io.protostuff.Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema (object.getClass ());
        return ProtobufIOUtil.toByteArray (object, schema, LinkedBuffer.allocate (LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    public <T> T deSerialize(byte[] bytes, Class<T> clazz) {
        RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom (clazz);
        T object = runtimeSchema.newMessage ();
        ProtobufIOUtil.mergeFrom (bytes, object, runtimeSchema);
        return object;
    }
}
