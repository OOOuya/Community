# 社区论坛项目

上手并熟悉springboot、mybatis等技术。

## 目的

熟悉一下几个技术

- springBoot
- Spring MVC
- Mybatis
- MYSQL、H2
- Flyway
- Heroku
- Git/GitHub
- Maven
- Restful

在项目搭建完成后，自己去网上理解一下项目的基本原理。

比如Spring，可以根据教程手写微型的框架。

开源到博客或者社区中。



## 上手

### 1.初始步骤

#### 1.1 创建springboot项目

springboot内置整合了tomcat，因此我们不需要再配置factes、artifacts的东西了。

#### 1.2 加入依赖

- 加入thymeleaf依赖

  ```xml
  <dependency>
  			<groupId>org.springframework.boot</groupId>
  			<artifactId>spring-boot-starter-thymeleaf</artifactId>
  		</dependency>
  ```



#### 1.3 分析页面流程

- 通过model和前端thymeleaf进行交互，将对象传入前端页面。
- 通过controller进行页面交互。捕捉`url`进行后端处理。 

#### 1.4 知识点

`@SpringBootApplication`的执行流程

#### 1.5 运行第一个springboot

1. 创建controller

   使用快捷键 ctrl + p 可以快速查看参数。

   ```java
   @Controller
   public class HelloController {
       @GetMapping("/hello")
       public String hello(@RequestParam(name = "name") String name,  Model model){
           model.addAttribute("name", name);
           return "index";
       }
   }
   ```

2. 创建前端页面

   导入thymeleaf引用

   ```html
   xmlns:th = "http://www.thymeleaf.org"
   ```

   前端页面

3. 发现报错

   `Whitelabel Error Page`

   详细报错

   ```
   [THYMELEAF][http-nio-8887-exec-1] Exception processing template "index": Error resolving template [index], template might not exist or might not be accessible by any of the configured Template Resolvers
   ```

   发现是前端页面和后端页面没匹配上。。。

   真是犯傻了。

#### 1.6 github创建对应仓库

##### 1.6.1 github自动化部署

1. 在git bash创建deploys key

   [Generating a new SSH key and adding it to the ssh-agent - GitHub Docs](https://docs.github.com/en/github/authenticating-to-github/connecting-to-github-with-ssh/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent#generating-a-new-ssh-key)

   存放目录`D:\Learning_material\other_materials\Java_materials\git_deploy_keys`

2. 在仓库中设置生成的key

   将生成的pub文件内容作为key的content

   ![image-20210520155049357](https://i.loli.net/2021/05/20/jFLpAEflw3NXM2c.png)

3. 在idea中push 项目到仓库中

   > 在idea中配置git,连接github
   >
   > [IDEA配置GIT](https://www.cnblogs.com/hkgov/p/7694101.html)
   >
   > 生成token：[首次使用git，生成token_](https://blog.csdn.net/qq_43103581/article/details/103203605)

   

   > idea配置bash
   >
   > [idea Terminal框想要使用git命令 ](https://www.cnblogs.com/eastwjn/p/10073202.html)

   ```git
   git init 初始化当前的文件目录 ./git
   git add . 将当前目录的所有文件添加到暂存区
   git commit “init repo”  将暂存区文件添加本地仓库
   vim .git/config  .git/config下添加github的用户名和密码
   git remote add origin git@github.com:OOOuya/Community.git 添加远程地址，名字是origin
   将项目推至远程地址（也就是仓库地址）
   git push -u origin master
   ```

   > 修改远程地址
   >
   > git remote set-url origin

   出现`git@github.com: Permission denied (publickey).`

   [git@github.com: Permission denied (publickey). fatal: Could not read from remote repository的问题解决_](https://blog.csdn.net/dotphoenix/article/details/100130424)

   发现是本地仓库没有和sshkey关联上。执行ssh-add

   在执行 ssh-add出现 `Could not open a connection to your authentication agent.`

   执行ssh-add bash。

   最终成功连接

   ![image-20210520154154774](https://i.loli.net/2021/05/20/OQ81ziraE53dnJm.png)

   **push成功**

4. 添加read.me文件

   ```
   git commit --amend --no-edit
   ```

   这个文件中有token