/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.common.util;

import com.alibaba.fastjson.JSON;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * IoUtil
 *
 * @author piper
 */
public final class IoUtil {

    /**
     * 默认缓存大小 8192
     */
    public static final int DEFAULT_BUFFER_SIZE = 2 << 12;
    /**
     * 默认中等缓存大小 16384
     */
    public static final int DEFAULT_MIDDLE_BUFFER_SIZE = 2 << 13;
    /**
     * 默认大缓存大小 32768
     */
    public static final int DEFAULT_LARGE_BUFFER_SIZE = 2 << 14;
    /**
     * 数据流末尾
     */
    public static final int EOF = -1;

    public static String read(Reader reader) throws IMException {
        return read(reader, true);
    }

    public static String read(Reader reader, boolean isClose) throws IMException {
        final StringBuilder builder = new StringBuilder();
        final CharBuffer buffer = CharBuffer.allocate(DEFAULT_BUFFER_SIZE);
        try {
            while (EOF != reader.read(buffer)) {
                builder.append(buffer.flip());
            }
        } catch (IOException e) {
            throw new IMException(IMErrorEnum.SERVER_ERROR);
        } finally {
            if (isClose) {
                close(reader);
            }
        }
        return builder.toString();
    }

    public static void writeUtf8(OutputStream out, boolean isCloseOut, Object... contents) throws IMException {
        write(out, StandardCharsets.UTF_8, isCloseOut, contents);
    }

    public static void write(OutputStream out, Charset charset, boolean isCloseOut, Object... contents) throws IMException {
        OutputStreamWriter osw = null;
        try {
            osw = getWriter(out, charset);
            for (Object content : contents) {
                if (content != null) {
                    if (content instanceof String) {
                        osw.write((String) content);
                    } else {
                        osw.write(JSON.toJSONString(content));
                    }
                }
            }
            osw.flush();
        } catch (IOException e) {
            throw new IMException(IMErrorEnum.SERVER_ERROR);
        } finally {
            if (isCloseOut) {
                close(osw);
            }
        }
    }

    public static OutputStreamWriter getWriter(OutputStream out, Charset charset) {
        if (null == out) {
            return null;
        }
        if (null == charset) {
            return new OutputStreamWriter(out);
        } else {
            return new OutputStreamWriter(out, charset);
        }
    }

    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
        }
    }

}
