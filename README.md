# JeeModel简介
 **[JeeModel](http://www.jeemodel.com/)** ， **寓意是：JavaEE项目模型** 

## 介绍
JeeModel是一个定位于基于SpringBoot的JavaWeb项目脚手架。

其中：jeemodel-unit的子工程是基于这个脚手架，开发出来的一些基础功能模块。模块与模块之间解耦，developer可根据实际情况，选择使用。

## 软件架构
### 后端 
1. SpringBoot：知名开箱即用框架，提供各种默认配置来简化项目配置。
2. Mybatis：优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。
3. MyBatis-Plus： MyBatis的增强工具，在 MyBatis 的基础上只做增强不做改变，简化开发、提高效率。
4. PageHelper：Mybatis框架的一个分页插件。
5. jwt：Token生成与解析。
6. spring-security：安全认证。
7. 其他:
- 数据库连接池：Druid，Alibaba开源官方说是Java语言中最好的数据库连接池。还能够提供强大的监控和扩展功能。
- 多数据源管理：dynamic-datasource-spring-boot-starter，苞米豆的动态多数据源管理，与MyBatis-Plus完美兼容。
- 数据库：MySQL。
- NoSQL：Redis，主要用作缓存和解决分布式缓存方案。


### 前端 
核心是VUE：支持VUE v2.x和 VUE v3.两个版本



## 模块功能
### 1、管理系统
1. 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
2. 参数管理：对系统动态配置常用参数。
3. 通知公告：系统通知公告信息发布维护。
4. 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5. 部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
6. 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
7. 岗位管理：配置系统用户所属担任职务。
8. 用户管理：
- 管理用户：用户是系统操作者，该功能主要完成系统用户配置。
- 在线用户：当前系统中活跃用户状态监控。
9. 日志管理：
- 操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
- 登录日志：系统登录日志记录查询包含登录异常。
### 2、研发工具
1. 系统接口：基于springfox3.0（swagger3），支持Swagger-ui和knife4j两种在线文档。
2. VUE2.0表单构建：拖动表单元素生成相应的v2.x页面，支持下载。
3. Echarts可视化图表库：基于JavaScript的数据可视化图表库，提供直观，生动，可交互，可个性化定制的数据可视化图表。。
4. 代码生成：前后端CRUD代码的生成和下载（java、vue、xml、sql）。

### 3、拓展功能
1. 数据监控：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。
2. 缓存监控：对系统的缓存信息查询，命令统计等。    


## 使用说明

1.  拉代码
2.  SQL语句构建数据库
3.  修改配置
4.  Running 跑起来看看

点击加入【QQ交流群】[![加入QQ群](https://img.shields.io/badge/223606797-blue.svg)](https://jq.qq.com/?_wv=1027&k=3l0rfaJP)

## 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


## 关于开源
开源协议： Apache License 2.0 。

基于开源软件【[若依-前后端分离版本（开源协议：MIT）](https://gitee.com/y_project/RuoYi-Vue)】， 

详细是【[RuoYi-Vue](https://gitee.com/y_project/RuoYi-Vue)】 2021-12-13 提交 / 2743785aafd8a9156b65f2a24a0dc388594233d0

我重构调整了前后端项目结构，优化了一些功能点，简化了结构，把**公共框架**与**业务模块**分离，拓展性增强。更适合作为JavaWeb开发的脚手架。


## 特别鸣谢：
- [element](https://github.com/ElemeFE/element)，
- [vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)，
- [eladmin-web](https://github.com/elunez/eladmin-web)，
- [若依-前后端分离版本](https://gitee.com/y_project/RuoYi-Vue)。

