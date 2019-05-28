# ITD_HMI小组工程

	> 本工程用于智能HMI小组的全部工程，库，所支持项目，工作进度的归档和查看。

# 文件结构说明

* doc
	> 用于存放各种文档

* projects
	> 用于存放各个项目

* others
	> 用于存放其他文件

* share
	> 用于存放已经打包成库的模块，以方便源码的保护， 代码的复用和跨平台的编译。

# 使用规范

* 上传规则
	> 1. 创建 `用户名+dev` 作为开发者的开发分支
	> 2. 创建 `用户名+stable` 作为开发者的稳定版本分支
	> 3. 禁止在master分支提交代码，
	> 4. 每周review代码后， stable分支的代码会并入master分之，应保持master分之可用代码的整洁，稳定版本会打tag。
	> 5. 每个工程都应有 `README.md` 和 `CHANGE.md` 。分别用于提供必要的工程说明和大版本更迭的必要说明。
	> 6. 本地端编译的文件应写在 `.gitignore` 文件中，禁止上传。
	> 7. `不符合规定的代码会要求重写！ `

* HTML/JS/CSS的代码规范

* C/C++代码规范
	> 严格按照谷歌的编程规范
	>> * [谷歌的编程规范图](http://10.10.51.40:3000/feng.ding/itd_hmi_group/blob/master/doc/google_coding_standard.jpg)
	>> * [谷歌的编程规文档](http://10.10.51.40:3000/feng.ding/itd_hmi_group/blob/master/doc/google_coding_standard.pdf)
	
* C/C++其他规范 
	> 1. ROS
	>> * service/msg: 按照类名写
	>> * topic：驼峰，首字母小写
	>> * package：名字首字母小写 
	> 2. 文件夹名字：小写单词下划线分割
	> 3. 所以文件都应有namespace，一律为`itd_lidar`
	> 4. 文件头的copyright等信息注意填写，有修改的注意在author中添加自己的名字
	> 5. 项目中的可执行文件均使用`yaml`文件传递参数, S32V版本的和ROS版本的区分

ps: [工作计划](http://10.10.51.40:3000/feng.ding/itd_hmi_group/blob/master/doc/work_list.ods)可查看小组各人员的工作安排和优先级。
