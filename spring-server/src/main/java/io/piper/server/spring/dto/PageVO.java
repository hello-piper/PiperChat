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
package io.piper.server.spring.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@ApiModel("分页数据")
public class PageVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("数据")
    private List<T> list;

    @ApiModelProperty("当前页码")
    private int pageNum;

    @ApiModelProperty("每页显示条数")
    private int pageSize;

    @ApiModelProperty("总页数")
    private int pages;

    @ApiModelProperty("总条数")
    private long total;

    @ApiModelProperty("附加属性")
    private Map<String, Object> extra;

    public static <T> PageVO<T> build(List<T> list, int pageNum, int pageSize, int pages, long total) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setList(list);
        pageVO.setPageNum(pageNum);
        pageVO.setPageSize(pageSize);
        pageVO.setPages(pages);
        pageVO.setTotal(total);
        return pageVO;
    }
}
