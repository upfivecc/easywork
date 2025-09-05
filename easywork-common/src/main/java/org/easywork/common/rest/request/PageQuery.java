package org.easywork.common.rest.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/8/29 21:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageQuery extends BaseReq {
    @Serial
    private static final long serialVersionUID = -4876208878611545864L;
    private Integer pageNum;
    private Integer pageSize;

}
