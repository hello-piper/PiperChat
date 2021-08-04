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

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;

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

    public static String read(Reader reader) throws IORuntimeException {
        return read(reader, true);
    }

    public static String read(Reader reader, boolean isClose) throws IORuntimeException {
        final StringBuilder builder = StrUtil.builder();
        final CharBuffer buffer = CharBuffer.allocate(DEFAULT_BUFFER_SIZE);
        try {
            while (EOF != reader.read(buffer)) {
                builder.append(buffer.flip().toString());
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } finally {
            if (isClose) {
                close(reader);
            }
        }
        return builder.toString();
    }

    public static void writeUtf8(OutputStream out, boolean isCloseOut, Object... contents) throws IORuntimeException {
        write(out, StandardCharsets.UTF_8, isCloseOut, contents);
    }

    public static void write(OutputStream out, Charset charset, boolean isCloseOut, Object... contents) throws IORuntimeException {
        OutputStreamWriter osw = null;
        try {
            osw = getWriter(out, charset);
            for (Object content : contents) {
                if (content != null) {
                    osw.write(Convert.toStr(content, StringUtil.EMPTY));
                }
            }
            osw.flush();
        } catch (IOException e) {
            throw new IORuntimeException(e);
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
