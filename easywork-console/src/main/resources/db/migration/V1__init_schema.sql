-- EasyWork Console 数据库初始化脚本
-- 作者: upfive
-- 日期: 2025/09/09

-- 创建数据库
CREATE DATABASE IF NOT EXISTS easywork DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE easywork;

-- 用户表
CREATE TABLE sys_user (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) COMMENT '昵称',
    real_name VARCHAR(50) COMMENT '真实姓名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    avatar VARCHAR(500) COMMENT '头像URL',
    gender TINYINT(1) DEFAULT 0 COMMENT '性别 1-男 2-女 0-未知',
    dept_id BIGINT(20) COMMENT '部门ID',
    dept_name VARCHAR(100) COMMENT '部门名称',
    status TINYINT(1) DEFAULT 1 COMMENT '状态 1-启用 0-禁用',
    last_login_time DATETIME COMMENT '最后登录时间',
    last_login_ip VARCHAR(50) COMMENT '最后登录IP',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT(20) COMMENT '创建人ID',
    update_by BIGINT(20) COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 0-未删除 1-已删除',
    version INT(11) DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    UNIQUE KEY uk_email (email),
    UNIQUE KEY uk_phone (phone),
    KEY idx_dept_id (dept_id),
    KEY idx_status (status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 部门表
CREATE TABLE sys_dept (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    parent_code VARCHAR(50) COMMENT '父部门编码',
    name VARCHAR(50) NOT NULL COMMENT '部门名称',
    code VARCHAR(50) NOT NULL COMMENT '部门编码',
    type TINYINT(1) DEFAULT 1 COMMENT '部门类型 1-公司 2-部门 3-小组',
    leader_id BIGINT(20) COMMENT '部门负责人ID',
    leader_name VARCHAR(50) COMMENT '部门负责人姓名',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    address VARCHAR(200) COMMENT '部门地址',
    status TINYINT(1) DEFAULT 1 COMMENT '状态 1-启用 0-禁用',
    level INT(11) DEFAULT 1 COMMENT '部门层级',
    sort INT(11) DEFAULT 0 COMMENT '排序号',
    path VARCHAR(500) COMMENT '层级路径 格式:父code/父code/当前code',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT(20) COMMENT '创建人ID',
    update_by BIGINT(20) COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 0-未删除 1-已删除',
    version INT(11) DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    KEY idx_parent_code (parent_code),
    KEY idx_leader_id (leader_id),
    KEY idx_type (type),
    KEY idx_status (status),
    KEY idx_level (level),
    KEY idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- 角色表
CREATE TABLE sys_role (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(50) NOT NULL COMMENT '角色名称',
    code VARCHAR(50) NOT NULL COMMENT '角色代码',
    description VARCHAR(200) COMMENT '角色描述',
    status TINYINT(1) DEFAULT 1 COMMENT '状态 1-启用 0-禁用',
    data_scope TINYINT(1) DEFAULT 1 COMMENT '数据范围 1-全部数据 2-本部门及以下数据 3-本部门数据 4-仅本人数据 5-自定义数据',
    sort INT(11) DEFAULT 0 COMMENT '排序号',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT(20) COMMENT '创建人ID',
    update_by BIGINT(20) COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 0-未删除 1-已删除',
    version INT(11) DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    KEY idx_status (status),
    KEY idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 权限表
CREATE TABLE sys_permission (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    parent_code VARCHAR(100) COMMENT '父权限编码',
    name VARCHAR(50) NOT NULL COMMENT '权限名称',
    code VARCHAR(100) NOT NULL COMMENT '权限代码',
    type TINYINT(1) NOT NULL COMMENT '权限类型 1-菜单 2-按钮 3-API',
    description VARCHAR(200) COMMENT '权限描述',
    resource VARCHAR(200) COMMENT '资源路径',
    method VARCHAR(10) COMMENT 'HTTP方法',
    status TINYINT(1) DEFAULT 1 COMMENT '状态 1-启用 0-禁用',
    icon VARCHAR(100) COMMENT '图标',
    level INT(11) DEFAULT 1 COMMENT '层级',
    sort INT(11) DEFAULT 0 COMMENT '排序号',
    path VARCHAR(500) COMMENT '路径 格式:父code/父code/当前code',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT(20) COMMENT '创建人ID',
    update_by BIGINT(20) COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 0-未删除 1-已删除',
    version INT(11) DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    KEY idx_parent_code (parent_code),
    KEY idx_type (type),
    KEY idx_status (status),
    KEY idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 菜单表
CREATE TABLE sys_menu (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    parent_code VARCHAR(50) COMMENT '父菜单编码',
    name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    code VARCHAR(50) NOT NULL COMMENT '菜单代码',
    type TINYINT(1) NOT NULL COMMENT '菜单类型 1-目录 2-菜单 3-按钮',
    icon VARCHAR(100) COMMENT '菜单图标',
    route_path VARCHAR(200) COMMENT '路由路径',
    component VARCHAR(200) COMMENT '组件路径',
    permission VARCHAR(100) COMMENT '权限标识',
    visible TINYINT(1) DEFAULT 1 COMMENT '状态 1-显示 0-隐藏',
    status TINYINT(1) DEFAULT 1 COMMENT '状态 1-启用 0-禁用',
    cache TINYINT(1) DEFAULT 0 COMMENT '是否缓存 1-缓存 0-不缓存',
    external TINYINT(1) DEFAULT 0 COMMENT '是否外链 1-是 0-否',
    external_url VARCHAR(500) COMMENT '外链地址',
    level INT(11) DEFAULT 1 COMMENT '层级',
    sort INT(11) DEFAULT 0 COMMENT '排序号',
    path VARCHAR(500) COMMENT '路径 格式:父code/父code/当前code',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT(20) COMMENT '创建人ID',
    update_by BIGINT(20) COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 0-未删除 1-已删除',
    version INT(11) DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    KEY idx_parent_code (parent_code),
    KEY idx_type (type),
    KEY idx_visible (visible),
    KEY idx_status (status),
    KEY idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 用户角色关系表
CREATE TABLE sys_user_role (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT(20) NOT NULL COMMENT '用户ID',
    role_id BIGINT(20) NOT NULL COMMENT '角色ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT(20) COMMENT '创建人ID',
    update_by BIGINT(20) COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 0-未删除 1-已删除',
    version INT(11) DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_role (user_id, role_id),
    KEY idx_user_id (user_id),
    KEY idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系表';

-- 角色权限关系表
CREATE TABLE sys_role_permission (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    role_id BIGINT(20) NOT NULL COMMENT '角色ID',
    permission_id BIGINT(20) NOT NULL COMMENT '权限ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by BIGINT(20) COMMENT '创建人ID',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_permission (role_id, permission_id),
    KEY idx_role_id (role_id),
    KEY idx_permission_id (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关系表';

-- 字典表
CREATE TABLE sys_dict (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(50) NOT NULL COMMENT '字典名称',
    code VARCHAR(50) NOT NULL COMMENT '字典编码',
    description VARCHAR(200) COMMENT '字典描述',
    status TINYINT(1) DEFAULT 1 COMMENT '状态 1-启用 0-禁用',
    sort INT(11) DEFAULT 0 COMMENT '排序号',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT(20) COMMENT '创建人ID',
    update_by BIGINT(20) COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 0-未删除 1-已删除',
    version INT(11) DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code),
    KEY idx_status (status),
    KEY idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- 字典项表
CREATE TABLE sys_dict_item (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    dict_id BIGINT(20) NOT NULL COMMENT '字典ID',
    label VARCHAR(50) NOT NULL COMMENT '字典项标签',
    value VARCHAR(100) NOT NULL COMMENT '字典项值',
    description VARCHAR(200) COMMENT '字典项描述',
    status TINYINT(1) DEFAULT 1 COMMENT '状态 1-启用 0-禁用',
    sort INT(11) DEFAULT 0 COMMENT '排序号',
    css_class VARCHAR(100) COMMENT '样式属性',
    is_default TINYINT(1) DEFAULT 0 COMMENT '是否默认 1-是 0-否',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT(20) COMMENT '创建人ID',
    update_by BIGINT(20) COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 0-未删除 1-已删除',
    version INT(11) DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (id),
    KEY idx_dict_id (dict_id),
    KEY idx_status (status),
    KEY idx_sort (sort),
    CONSTRAINT fk_dict_item_dict FOREIGN KEY (dict_id) REFERENCES sys_dict (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典项表';

-- 操作日志表
CREATE TABLE sys_operate_log (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT(20) COMMENT '操作用户ID',
    username VARCHAR(50) COMMENT '操作用户名',
    module VARCHAR(50) COMMENT '操作模块',
    operate_type VARCHAR(20) COMMENT '操作类型',
    description VARCHAR(200) COMMENT '操作描述',
    method VARCHAR(10) COMMENT '请求方法',
    uri VARCHAR(200) COMMENT '请求URI',
    params TEXT COMMENT '请求参数',
    result TEXT COMMENT '返回结果',
    status TINYINT(1) DEFAULT 1 COMMENT '操作状态 1-成功 0-失败',
    error_msg TEXT COMMENT '错误信息',
    ip VARCHAR(50) COMMENT '操作IP',
    location VARCHAR(100) COMMENT '操作地点',
    browser VARCHAR(100) COMMENT '浏览器',
    os VARCHAR(100) COMMENT '操作系统',
    execute_time BIGINT(20) COMMENT '执行时间（毫秒）',
    operate_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT(20) COMMENT '创建人ID',
    update_by BIGINT(20) COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 0-未删除 1-已删除',
    version INT(11) DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_module (module),
    KEY idx_operate_type (operate_type),
    KEY idx_status (status),
    KEY idx_operate_time (operate_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- 登录日志表
CREATE TABLE sys_login_log (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT(20) COMMENT '用户ID',
    username VARCHAR(50) COMMENT '用户名',
    login_type TINYINT(1) DEFAULT 1 COMMENT '登录类型 1-用户名密码 2-OAuth2 3-短信验证码',
    status TINYINT(1) DEFAULT 1 COMMENT '登录状态 1-成功 0-失败',
    ip VARCHAR(50) COMMENT '登录IP',
    location VARCHAR(100) COMMENT '登录地点',
    browser VARCHAR(100) COMMENT '浏览器',
    os VARCHAR(100) COMMENT '操作系统',
    message VARCHAR(200) COMMENT '登录信息',
    login_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    logout_time DATETIME COMMENT '登出时间',
    session_id VARCHAR(100) COMMENT '会话ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT(20) COMMENT '创建人ID',
    update_by BIGINT(20) COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 0-未删除 1-已删除',
    version INT(11) DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_username (username),
    KEY idx_login_type (login_type),
    KEY idx_status (status),
    KEY idx_login_time (login_time),
    KEY idx_ip (ip)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志表';

-- 文件信息表
CREATE TABLE sys_file_info (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    file_name VARCHAR(200) NOT NULL COMMENT '文件名称',
    original_name VARCHAR(200) NOT NULL COMMENT '原始文件名',
    file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
    file_url VARCHAR(500) COMMENT '文件URL',
    file_size BIGINT(20) NOT NULL COMMENT '文件大小（字节）',
    content_type VARCHAR(100) COMMENT '文件类型',
    extension VARCHAR(20) COMMENT '文件扩展名',
    md5 VARCHAR(32) COMMENT '文件MD5',
    storage_type TINYINT(1) DEFAULT 1 COMMENT '存储类型 1-本地存储 2-OSS存储 3-云存储',
    bucket_name VARCHAR(100) COMMENT '存储桶名称',
    upload_by BIGINT(20) COMMENT '上传用户ID',
    upload_by_name VARCHAR(50) COMMENT '上传用户名',
    business_type VARCHAR(50) COMMENT '业务类型',
    business_id VARCHAR(100) COMMENT '业务ID',
    status TINYINT(1) DEFAULT 1 COMMENT '状态 1-正常 0-删除',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT(20) COMMENT '创建人ID',
    update_by BIGINT(20) COMMENT '更新人ID',
    deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 0-未删除 1-已删除',
    version INT(11) DEFAULT 1 COMMENT '版本号',
    PRIMARY KEY (id),
    KEY idx_file_name (file_name),
    KEY idx_md5 (md5),
    KEY idx_storage_type (storage_type),
    KEY idx_upload_by (upload_by),
    KEY idx_business_type (business_type),
    KEY idx_business_id (business_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件信息表';

-- 初始化示例数据（基于code的树形结构）

-- 部门数据
INSERT INTO sys_dept (parent_code, name, code, type, status, level, sort, path, remark) VALUES
(NULL, '总公司', 'company', 1, 1, 1, 1, 'company', '总公司'),
('company', '技术中心', 'tech', 2, 1, 2, 1, 'company/tech', '技术研发中心'),
('company', '产品中心', 'product', 2, 1, 2, 2, 'company/product', '产品管理中心'),
('company', '运营中心', 'operation', 2, 1, 2, 3, 'company/operation', '运营管理中心'),
('tech', '前端开发组', 'frontend', 3, 1, 3, 1, 'company/tech/frontend', '前端开发团队'),
('tech', '后端开发组', 'backend', 3, 1, 3, 2, 'company/tech/backend', '后端开发团队'),
('tech', '测试组', 'test', 3, 1, 3, 3, 'company/tech/test', '软件测试团队'),
('product', 'UI设计组', 'ui', 3, 1, 3, 1, 'company/product/ui', 'UI/UX设计团队'),
('product', '产品管理组', 'pm', 3, 1, 3, 2, 'company/product/pm', '产品管理团队');

-- 菜单数据
INSERT INTO sys_menu (parent_code, name, code, type, icon, route_path, component, visible, status, level, sort, path, remark) VALUES
(NULL, '系统管理', 'system', 1, 'setting', '/system', NULL, 1, 1, 1, 1, 'system', '系统管理目录'),
('system', '用户管理', 'user_mgmt', 2, 'user', '/system/user', 'system/user/index', 1, 1, 2, 1, 'system/user_mgmt', '用户管理菜单'),
('system', '部门管理', 'dept_mgmt', 2, 'tree', '/system/dept', 'system/dept/index', 1, 1, 2, 2, 'system/dept_mgmt', '部门管理菜单'),
('system', '角色管理', 'role_mgmt', 2, 'peoples', '/system/role', 'system/role/index', 1, 1, 2, 3, 'system/role_mgmt', '角色管理菜单'),
('system', '菜单管理', 'menu_mgmt', 2, 'tree-table', '/system/menu', 'system/menu/index', 1, 1, 2, 4, 'system/menu_mgmt', '菜单管理菜单'),
('user_mgmt', '新增用户', 'user_add', 3, NULL, NULL, NULL, 0, 1, 3, 1, 'system/user_mgmt/user_add', '新增用户按钮'),
('user_mgmt', '编辑用户', 'user_edit', 3, NULL, NULL, NULL, 0, 1, 3, 2, 'system/user_mgmt/user_edit', '编辑用户按钮'),
('user_mgmt', '删除用户', 'user_delete', 3, NULL, NULL, NULL, 0, 1, 3, 3, 'system/user_mgmt/user_delete', '删除用户按钮');

-- 权限数据
INSERT INTO sys_permission (parent_code, name, code, type, description, resource, method, status, level, sort, path, remark) VALUES
(NULL, '系统管理', 'sys', 1, '系统管理模块', '/system', 'GET', 1, 1, 1, 'sys', '系统管理模块权限'),
('sys', '用户管理', 'sys:user', 1, '用户管理模块', '/system/user', 'GET', 1, 2, 1, 'sys/sys:user', '用户管理模块权限'),
('sys:user', '查询用户', 'sys:user:query', 3, '查询用户列表', '/api/users', 'GET', 1, 3, 1, 'sys/sys:user/sys:user:query', '查询用户权限'),
('sys:user', '新增用户', 'sys:user:add', 3, '新增用户', '/api/users', 'POST', 1, 3, 2, 'sys/sys:user/sys:user:add', '新增用户权限'),
('sys:user', '编辑用户', 'sys:user:edit', 3, '编辑用户', '/api/users/*', 'PUT', 1, 3, 3, 'sys/sys:user/sys:user:edit', '编辑用户权限'),
('sys:user', '删除用户', 'sys:user:delete', 3, '删除用户', '/api/users/*', 'DELETE', 1, 3, 4, 'sys/sys:user/sys:user:delete', '删除用户权限'),
('sys', '部门管理', 'sys:dept', 1, '部门管理模块', '/system/dept', 'GET', 1, 2, 2, 'sys/sys:dept', '部门管理模块权限'),
('sys:dept', '查询部门', 'sys:dept:query', 3, '查询部门列表', '/api/depts', 'GET', 1, 3, 1, 'sys/sys:dept/sys:dept:query', '查询部门权限'),
('sys:dept', '新增部门', 'sys:dept:add', 3, '新增部门', '/api/depts', 'POST', 1, 3, 2, 'sys/sys:dept/sys:dept:add', '新增部门权限');