## spring 注解方式ioc容器加载分析

### ioc加载准备  
#### 调用无参构造函数,创建了Bean工厂（父类构造方法）注册AnnotatedBeanDefinitionReader读取器   
- DefaultListableBeanFactory:通过继承关系可以发现该类处于底层，具有很丰富的功能  
- **![](/topic/spring/pic/DefaultListableBeanFactory.png)**
- AnnotatedBeanDefinitionReader:可用来读取注解，在创建该读取器的过程中（构造方法中）会注册一些内置的Bean定义
  - ConfigurationClassPostProcessor：用于扫描配置类，扫描指定路径（basePackages）下的类信息加载成Bean定义
  - AutowiredAnnotationBeanPostProcessor：处理autowire
  - RequiredAnnotationBeanPostProcessor： 处理require
  - CommonAnnotationBeanPostProcessor：处理java自带注解
  - PersistenceAnnotationBeanPostProcessor：处理jpa注解
  - EventListenerMethodProcessor：处理EventListener注解
  - DefaultEventListenerFactory：事件工厂
- **![](/topic/spring/pic/reader-2022-09-08-1437.png)**  

#### 注册配置类 
- 将配置类加载成bean定义放入缓存
#### 刷新容器 
- 这是容器启动非常重要的一步，基本贯穿了容器的整个生命周期
  - 准备上下文，主要是记录启动时间以及激活状态等。
  - 指定bean工厂的序列化ID
  - 对bean工厂进行属性填充
    - 设置类加载器、表达式解析器、属性资源编辑器等
    - 添加了一个bean的后置处理器ApplicationContextAwareProcessor
    - 忽略了一些接口的自动装配，防止自动注入到容器（这些接口都有setXxx方法），以便于进行手动设置
    - 注册了一些接口的自动注入
    - 注册了事件监听器探测器的后置处理器ApplicationListenerDetector
    - ……
  - 实例化并调用bean工厂的后置处理器（这个时候主要是调用我们手动添加和内置的），主要是调用ConfigurationClassPostProcessor  
  - 进行配置类解析
    - 接口直接过滤掉
    - 通过判断是否有configration或者component、componentscan、import、ImportResource注解找到配置类
    - 如果有方法标注了@Bean也符合
    - 设置beanname的生成器
    - 构建配置类的解析器对配置类进行真正解析
      - 如果有@propertySource注解进行处理
      - 解析我们的 @ComponentScan 注解，将指定路径下的类信息注册成bean定义（会排除掉当前配置类）
      - 如果扫描出来的还有配置类会进行递归解析
      - 处理 @Import annotations
      - 处理 @ImportResource annotations
      - 处理 @Bean methods 获取到我们配置类中所有标注了@Bean的方法
      - 处理配置类接口 默认方法的@Bean
      - 处理配置类的父类的 ，循环再解析
      - 处理完以上逻辑后，会会处理延时加载的DeferredImportSelectors。springboot就是通过这种方式来加载spring.factories中配置的自动装配类
        - 如果实现了ImportSelector，调用相关的aware方法。然后看是否为延时的DeferredImportSelectors，是的华不行行处理；不是的话调用selector  
        的selectImports，递归解析-- 直到成普通组件
        -如果导入的的组件是ImportBeanDefinitionRegistrar，这里不直接调用，只是解析   
        - 否则当做配置类再解析，注意这里会标记：importedBy，  表示这是Import的配置的类



