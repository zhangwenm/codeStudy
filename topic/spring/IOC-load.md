## spring ioc容器加在过程

### ioc加载准备  
#### 注册AnnotatedBeanDefinitionReader读取器  
- 可用来读取注解，在创建该读取器的过程中（构造方法中）会注册一些内置的Bean定义
  - ConfigurationClassPostProcessor：用于扫描配置类，扫描指定路径（basePackages）下的类信息加载成Bean定义
  - AutowiredAnnotationBeanPostProcessor：处理autowire
  - RequiredAnnotationBeanPostProcessor： 处理require
  - CommonAnnotationBeanPostProcessor：处理java自带注解
  - PersistenceAnnotationBeanPostProcessor：处理jpa注解
  - EventListenerMethodProcessor：处理EventListener注解
  - DefaultEventListenerFactory：事件工厂
  > **![](/topic/spring/pic/reader-2022-09-08-1437.png)**  

#### 注册配置类 
- 将配置类加载成bean定义放入缓存


