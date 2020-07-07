# 简介
基于拍拍贷的das 2.3.1版本进行修改，使其可以与spring boot集成，并支持hikari,druid,tomcat3中连接池<br>

**请注意**：本项目暂不支持动态修改数据库参数，即修改数据库参数必须重启才生效!!!

拍拍贷开源项目地址 : https://github.com/ppdaicorp/das

[示例项目:das-sample](/das-sample)<br>

配置文件说明 ：<br>
&nbsp;&nbsp;&nbsp;&nbsp;公共配置文件中主要配置的是全局使用的配置，例如逻辑数据库、逻辑数据库和物理数据库关系、全局账号、密码、驱动类名称等<br>

配置文件示例：
* 公共配置文件示例 : [application.properties](das-sample/src/main/resources/application.properties)
* hikari 连接池配置示例 : [application-hikari.properties](das-sample/src/main/resources/application-hikari.properties)
* druid 连接池配置示例 : [application-druid.properties](das-sample/src/main/resources/application-druid.properties)
* tomcat 连接池配置示例 : [application-tomcat.properties](das-sample/src/main/resources/application-tomcat.properties)

spring boot 自动配置示例：
* 自动配置类 : [DasConfig.java](das-sample/src/main/java/com/ichangtou/config/DasConfig.java)
 
数据库操作示例 ：
* 新增 : [InsterSampleController.java](das-sample/src/main/java/com/ichangtou/controller/InsterSampleController.java) 
* 查询 : [QuerySampleController.java](das-sample/src/main/java/com/ichangtou/controller/QuerySampleController.java) 
* 修改 : [UpdateSampleController.java](das-sample/src/main/java/com/ichangtou/controller/UpdateSampleController.java) 
* 删除 : [DeleteSampleController.java](das-sample/src/main/java/com/ichangtou/controller/DeleteSampleController.java) 

更多关于DAS的操作和文档，请参考【[拍拍贷官方网站](https://github.com/ppdaicorp/das/wiki)】的"Das Client文档目录"章节。

# 拍拍贷DAS用户文档
[wiki](https://github.com/ppdaicorp/das/wiki)
