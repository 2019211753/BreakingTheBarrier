package com.lrm.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * user的avatar在序列化时自动转换成base64
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2022-01-05
 */
public class AvatarSerializer extends StdSerializer<String> {

    public AvatarSerializer() {
        this(null);
    }

    public AvatarSerializer(Class<String> t) {
        super(t);
    }

    @Override
    public void serialize(String avatar, JsonGenerator gen, SerializerProvider arg2)
            throws IOException {
        gen.writeString(FileUtils.convertAvatar(avatar));
    }
}
