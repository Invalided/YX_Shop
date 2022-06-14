# YX_Shop
o2o校园商铺平台 <br/>
项目中Vue部分学习使用了开源项目https://github.com/tuture-dev/vue-admin-template
# 技术架构
![技术架构](https://images.gitee.com/uploads/images/2021/0805/091111_78645843_5558730.png "架构.png")
# 配置说明
0. 配置CentOS 7虚拟机，配置安装Redis、Nginx、Tomcat、Java环境(非必须，若采用本地安装Redis的方式可跳过这一步)
1. 在本地安装Redis服务(若0中已配置则跳过)
2. 使用数据库管理工具导入DataBase文件夹下的o2o.sql
3. 导入项目文件夹至Eclipse中，由于对用户密码加密，先在DESUtil类中对数据库用户名和密码加密,再填写数据库配置文件jdbc.properties的用户名和密码
4. 修改redis.properties文件中的hostname及password(本地填写本地ip地址,虚拟机填写虚拟机ip地址)
5. 项目中图片文件映射到本地磁盘，将Tomcat中Server.xml的<DocBase>内容修改为自定义内容
6. 以上配置无误后，运行项目，访问localhost:8000(端口号以Server.xml中配置为准) 即可
# 项目访问
用户首页：localhost:8000/o2o/frontend/index <br/>
商家后台：localhost:8000/o2o/local/login <br/>
管理员页：localhost:8000/o2o/superadmin/show<br/>
博文地址：https://blog.csdn.net/mdzz14/article/details/119394423