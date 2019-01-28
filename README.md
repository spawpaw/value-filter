# 脱敏工具

## 特性
本工具用于POJO的脱敏，如用户姓名字段、身份证字段等。  
✔ 提供spring-boot-starter，和spring-boot无缝集成  
✔ 高度可定制，可自由添加符合自己业务场景的脱敏器  
✔ 为原生类型及java自身的容器提供了多种基本脱敏器

## 一、如何使用
#### 引入Maven包
在pom.xml中添加如下依赖：
```xml
<dependency>

</dependency>
```
#### 在application.yml中配置

#### 在需要进行脱敏的字段上加上相关脱敏注解

#### 使用属性注入使用本工具
```java
public class DemoService{
    //通过属性注入的方式获取本工具的实例
    @Autowired
    private DesensitizationService desensitizationService;

    @RequestMapping()
    public User findUsers(){
        List<User> userList=mapper.selectAll();
        return  desensitizationService.desensitize(userList);
    }
    
}
```

## 如何定义自己的脱敏器
#### Step1.自定义脱敏类型
#### Step2.自定义脱敏注解
