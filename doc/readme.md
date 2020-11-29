# 服务介绍

类似云盘，可以操作(增删改查)指定目录下的资源(文本，视频，音频，图片等)；

技术选型：
1. 前端thymeleaf+layui
2. 整体项目用springboot实现
3. 数据库连接池druid
4. 缓存redis

注：主要是练手用，后面根据自己使用情况或者想法进行优化和增加功能

# 更新日志

## V1.0.0

更新时间：2020-11-28

1. 可以浏览指定目录下的文件，点击../回退
2. 可以对文件进行删除和查看(音频视频可以播放，简单用HTML5实现)，下载操作
3. 可以在当前查看的目录下面进行文件上传和新建文件夹的操作
4. 数据库和redis暂时未使用，对应代码注释了

### 部署

Maven打包后修改config/application.yml中的source.root.path配置即可
使用；config/db.yml没用，里面对应的代码暂时注释了；

启动没有提供脚本文件，可以在项目根目录下执行下面命令：
   
    nohup java -cp ".:lib/*" com.xck.ServerApplication > nohup.out 2>&1 &
    
