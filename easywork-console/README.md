
easywork - 让您有更多的时间陪家人和朋友！

## 通用管理后台

### 开发计划：

1. 基础功能：

- [ ] 用户管理
- [ ] 角色管理
- [ ] 权限管理
- [ ] 菜单管理
- [ ] 字典管理
- [ ] 日志管理

2. 核心功能：
- [ ] 登录与认证
- [ ] 仪表盘
- [ ] 代码生成器

3. 拓展功能：
- [ ] 文件上传
- [ ] API 文档
- [ ] 多数据库支持
- [ ] 多语言支持



### 字典表的设计

```aiignore

字典表                     字典项表
t_dict                    t_dict_item
├── 用户状态 (ID=1)        ├── 启用 (dict_id=1, value='1')
├── 性别类型 (ID=2)        ├── 禁用 (dict_id=1, value='0')
└── 订单状态 (ID=3)        ├── 男 (dict_id=2, value='1')
                          ├── 女 (dict_id=2, value='2')
                          ├── 未知 (dict_id=2, value='0')
                          ├── 待付款 (dict_id=3, value='1')
                          ├── 已付款 (dict_id=3, value='2')
                          └── 已取消 (dict_id=3, value='3')
```


### 部门设计
```aiignore
公司 (type=1)                              # 顶级组织
├── 研发部 (type=2)                        # 部门级
│   ├── 前端组 (type=3)                    # 小组级
│   ├── 后端组 (type=3)                    # 小组级
│   └── 测试组 (type=3)                    # 小组级
├── 产品部 (type=2)                        # 部门级
│   ├── 产品组 (type=3)                    # 小组级
│   └── 设计组 (type=3)                    # 小组级
└── 运营部 (type=2)                        # 部门级
    ├── 市场组 (type=3)                    # 小组级
    └── 客服组 (type=3)                    # 小组级
```

### mybatis-plus 使用原则

- 简单查询 → 使用 MyBatis Plus 内置方法
- 复杂关联 → 使用 @Select 注解或 XML
- 业务组装 → 在 Repository 实现类中完成
- 类型安全 → 优先使用 LambdaQueryWrapper

















