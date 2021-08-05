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
package io.piper.server.spring.controller;

import io.piper.common.exception.IMResult;
import io.piper.server.spring.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "文件接口")
@RestController
@RequestMapping
public class FileController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public IMResult<String> upload(MultipartFile file) {
        return IMResult.ok(uploadService.upload(file));
    }

    @GetMapping("/download")
    @ApiOperation("下载文件")
    public IMResult<Void> download(HttpServletResponse response, @RequestParam("name") String name) {
        uploadService.download(response, name);
        return IMResult.ok();
    }
}
