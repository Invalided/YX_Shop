# YX_Shop
o2o校园商铺平台Server端 <br/>
此项目提供后端服务接口
# 技术架构
![技术架构](https://images.gitee.com/uploads/images/2021/0805/091111_78645843_5558730.png "架构.png")
# 配置说明
0. 配置CentOS 7虚拟机，配置安装Redis、Nginx、Tomcat、Java环境(非必须，也可在本地配置上述服务)
1. 在本地安装Redis服务(若0中已配置则跳过)
2. 使用数据库管理工具导入DataBase文件夹下的o2o.sql
3. 导入项目文件夹至Eclipse/IDEA中，由于对用户密码加密，先在DESUtil类中对数据库用户名和密码加密,再填写数据库配置文件jdbc.properties的用户名和密码
4. 修改redis.properties文件中的hostname及password(本地填写本地ip地址,虚拟机填写虚拟机ip地址)
5. 项目中图片文件映射到本地磁盘，将Tomcat中Server.xml的<DocBase>内容修改为自定义内容
6. 以上配置无误后，运行项目，访问localhost:8000(端口号以Server.xml中配置为准) 即可
# 项目访问
根据配置后端接口API前缀为localhost:8000/o2o/<br/>
页面效果需要结合前端项目访问即可<br/>
博文地址：https://blog.csdn.net/mdzz14/article/details/119394423
