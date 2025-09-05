package org.easywork.auth.trigger.http.controller;

import lombok.RequiredArgsConstructor;
import org.easywork.auth.domain.user.model.AuthUser;
import org.easywork.auth.domain.user.model.AuthUserQuery;
import org.easywork.auth.domain.user.service.AuthUserService;
import org.easywork.auth.trigger.http.converter.AuthUserConverter;
import org.easywork.auth.trigger.http.model.AuthUserVO;
import org.easywork.common.rest.result.PageInfo;
import org.easywork.common.rest.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理
 *
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/9/5 15:02
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class AuthUserController {

    private final AuthUserService authUserService;

    @GetMapping("/list")
    public String list() {
        return "user/list";
    }

    @PostMapping("/pageQuery")
    @ResponseBody
    public Result<PageInfo<AuthUserVO>> pageQuery(@RequestBody AuthUserQuery query) {
        PageInfo<AuthUser> pageInfo = this.authUserService.pageQuery(query);
        List<AuthUserVO> users = AuthUserConverter.INSTANCE.authUser2AuthUserVO(pageInfo.getRecords());
        return Result.success(PageInfo.of(users, pageInfo.getTotal(), pageInfo.getPage(), pageInfo.getPageSize(), pageInfo.getPages()));
    }

}
