package org.easywork.common.rest.result;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/8/29 21:20
 */
@Data
public class PageInfo<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 859208027633645770L;

    private List<T> records;
    private long total;
    private int page;
    private int pageSize;
    private int pages;

    public static <T> PageInfo<T> of(List<T> data, long total, int page, int pageSize, int pages) {
        PageInfo<T> result = new PageInfo<>();
        result.setRecords(data);
        result.setTotal(total);
        result.setPage(page);
        result.setPageSize(pageSize);
        result.setPages(pages);
        return result;
    }
}
