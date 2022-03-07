package com.lrm.util;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * 内容在序列化时自动转换成html
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2022-01-16
 */
public class ContentSerializerUtils extends StdSerializer<String> {

    public ContentSerializerUtils() {
        this(null);
    }

    public ContentSerializerUtils(Class<String> t) {
        super(t);
    }

    @Override
    public void serialize(String content, JsonGenerator gen, SerializerProvider arg2)
            throws IOException {
        gen.writeString(MarkdownUtils.markdownToHtmlExtensions(content));
    }
}