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
package io.piper.server.spring.service;

import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.config.ServerProperties;
import io.piper.common.util.IdUtil;
import io.piper.common.util.IoUtil;
import io.piper.common.util.IpUtil;
import io.piper.common.util.YamlUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class UploadService {

    private String ip;
    private Integer port;

    @PostConstruct
    public void init() {
        ip = IpUtil.getIpVo().getIp();
        port = YamlUtil.getConfig("server", ServerProperties.class).getPort();
    }

    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String type = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String name = "/file/" + IdUtil.fastSimpleUUID() + "." + type;
        FileOutputStream localFile = null;
        try {
            localFile = new FileOutputStream(name);
            IoUtil.copy(file.getInputStream(), localFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(localFile);
        }
        return ip + ":" + port + name;
    }

    public void download(HttpServletResponse response, String name) {
        boolean startsWith = name.startsWith("/file/");
        if (!startsWith) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        File file = new File(name);
        if (!file.exists()) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(name);
            IoUtil.copy(fis, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(fis);
        }
    }
}
