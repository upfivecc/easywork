package org.easywork.console.domain.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.easywork.common.rest.request.PageQuery;

import java.io.Serial;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/10 16:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuQuery extends PageQuery {
    @Serial
    private static final long serialVersionUID = 8149952772482256268L;

    private Integer status;
    private Integer type;
    private Integer visible;
}
