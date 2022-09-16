## spring 注解方式ioc容器加载配置类分析
> IOC：控制反转，是一种设计思想。而spring ioc容器则是这种设计思想的具体实现.  
> 作用：将Bean的创建和维护从开发者转交到spring容器，开发者需要用的时候只需从容器上下文中获取即可，  
> 从而使开发人员解放出来专注于业务本身。  
![](/doc/spring/pic/2022-09-08-2053-ioc.png)
### ioc加载准备  
> Demo
```java 
@Configuration
@ComponentScan(basePackages = {"com.ccc.xxx"})
        //excludeFilters={@ComponentScan.Filter(type=FilterType.ANNOTATION,value={Controller.class})})
public class MainConfig {

}
```
```java 
public static void main(String[] args)   {
		// 加载spring上下文
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);

		Animal animal =  (Animal) context.getBean("animal");
		System.out.println(animal.getName());
	}
```
```java 
public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
		//调用构造函数 
		this();
		//注册我们的配置类（还没注冊）
		register(annotatedClasses);
		//IOC容器刷新接口
		refresh();
	}  
```
![](/topic/spring/pic/AnnotationConfigApplicationContext.png)  

      public GenericApplicationContext() {
         /**
         * 调用父类的构造函数,为ApplicationContext spring上下文对象初始beanFactory
         * 为啥是DefaultListableBeanFactory？我们去看BeanFactory接口的时候
         * 发DefaultListableBeanFactory是最底层的实现，功能是最全的
         */
        this.beanFactory = new DefaultListableBeanFactory();
      }
#### 调用无参构造函数,创建了Bean工厂（父类构造方法），注册AnnotatedBeanDefinitionReader读取器  
- DefaultListableBeanFactory:通过继承关系可以发现该类处于底层，具有很丰富的功能  
**![](/topic/spring/pic/DefaultListableBeanFactory.png)**
- AnnotatedBeanDefinitionReader:可用来读取注解，在创建该读取器的过程中（构造方法中）会注册一些内置的Bean定义
  - ConfigurationClassPostProcessor：用于扫描配置类，扫描指定路径（basePackages）下的类信息加载成Bean定义
  - AutowiredAnnotationBeanPostProcessor：处理autowire
  - RequiredAnnotationBeanPostProcessor： 处理require
  - CommonAnnotationBeanPostProcessor：处理java自带注解
  - PersistenceAnnotationBeanPostProcessor：处理jpa注解
  - EventListenerMethodProcessor：处理EventListener注解
  - DefaultEventListenerFactory：事件工厂
![](/topic/spring/pic/reader-2022-09-08-1437.png)  
>注册内置处理器代码片段
``` java
public static Set<BeanDefinitionHolder> registerAnnotationConfigProcessors(
     BeanDefinitionRegistry registry, @Nullable Object source) {

        DefaultListableBeanFactory beanFactory = unwrapDefaultListableBeanFactory(registry);
        if (beanFactory != null) {
            if (!(beanFactory.getDependencyComparator() instanceof AnnotationAwareOrderComparator)) {
                //注册了实现Order接口的排序器
                beanFactory.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE);
            }
            //设置@AutoWired的候选的解析器：ContextAnnotationAutowireCandidateResolver
            // getLazyResolutionProxyIfNecessary方法，它也是唯一实现。
            //如果字段上带有@Lazy注解，表示进行懒加载 Spring不会立即创建注入属性的实例，而是生成代理对象，来代替实例
            if (!(beanFactory.getAutowireCandidateResolver() instanceof ContextAnnotationAutowireCandidateResolver)) {
                beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
            }
        }

        Set<BeanDefinitionHolder> beanDefs = new LinkedHashSet<>(8);

        /**
         * 为我们容器中注册了解析我们配置类的后置处理器ConfigurationClassPostProcessor
         * 名字叫:org.springframework.context.annotation.internalConfigurationAnnotationProcessor
         */
        if (!registry.containsBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(ConfigurationClassPostProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
        }

        /**
         * 为我们容器中注册了处理@Autowired 注解的处理器AutowiredAnnotationBeanPostProcessor
         * 名字叫:org.springframework.context.annotation.internalAutowiredAnnotationProcessor
         */
        if (!registry.containsBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        }

        /**
         * 为我们容器中注册处理@Required属性的注解处理器RequiredAnnotationBeanPostProcessor
         * 名字叫:org.springframework.context.annotation.internalRequiredAnnotationProcessor
         */
        if (!registry.containsBeanDefinition(REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(RequiredAnnotationBeanPostProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        }

        /**
         * 为我们容器注册处理JSR规范的注解处理器CommonAnnotationBeanPostProcessor
         * org.springframework.context.annotation.internalCommonAnnotationProcessor
         */
        if (jsr250Present && !registry.containsBeanDefinition(COMMON_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(CommonAnnotationBeanPostProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
        }

        /**
         * 处理jpa注解的处理器org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor
         */
        if (jpaPresent && !registry.containsBeanDefinition(PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition();
            try {
                def.setBeanClass(ClassUtils.forName(PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME,
                        AnnotationConfigUtils.class.getClassLoader()));
            }
            catch (ClassNotFoundException ex) {
                throw new IllegalStateException(
                        "Cannot load optional framework class: " + PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME, ex);
            }
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME));
        }

        /**
         * 处理监听方法的注解@EventListener解析器EventListenerMethodProcessor
         */
        if (!registry.containsBeanDefinition(EVENT_LISTENER_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(EventListenerMethodProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        }

        /**
         * 注册事件监听器工厂
         */
        if (!registry.containsBeanDefinition(EVENT_LISTENER_FACTORY_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(DefaultEventListenerFactory.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, EVENT_LISTENER_FACTORY_BEAN_NAME));
        }

        return beanDefs;
    }
```
 
#### 注册配置类 
> 将配置类加载成bean定义放入缓存
```java  
register(annotatedClasses);
```
#### 刷新容器 
- 这是容器启动非常重要的一步，基本贯穿了容器的整个生命周期
  - 准备上下文，主要是记录启动时间以及激活状态等。
  - 指定bean工厂的序列化ID
  - 对bean工厂进行属性填充
    - 设置类加载器、表达式解析器、属性资源编辑器等
    - 添加了一个bean的后置处理器ApplicationContextAwareProcessor
    - 忽略了一些接口的自动装配，防止自动注入到容器（这些接口都有setXxx方法），以便于进行手动设置  

``` java  
  beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
  beanFactory.ignoreDependencyInterface(EmbeddedValueResolverAware.class);
  beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);
  beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);
  beanFactory.ignoreDependencyInterface(MessageSourceAware.class);
  beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);
 ```
- 
  - 
     - 注册了一些接口的自动注入
``` java  
beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
beanFactory.registerResolvableDependency(ResourceLoader.class, this);
beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
beanFactory.registerResolvableDependency(ApplicationContext.class, this);
 ```
- 
  - 
       - 注册了事件监听器探测器的后置处理器ApplicationListenerDetector
         - 用于初始化后期后置处理器调用进行监听器注册
       - ……
       - 实例化并调用bean工厂的后置处理器（这个时候主要是调用我们手动添加和内置的），主要是调用ConfigurationClassPostProcessor  
       - postProcessBeanDefinitionRegistry：进行配置类解析
         - 接口直接过滤掉
         - 通过判断是否有configration或者component、componentscan、import、ImportResource注解找到配置类
         - 如果有方法标注了@Bean也符合
         - 设置beanname的生成器
         - 构建配置类的解析器对配置类进行真正解析
           - 如果有@propertySource注解进行处理
           - 解析我们的 @ComponentScan 注解，将指定路径下非接口并且有configration或者component、componentscan、import、  
           ImportResource注解类信息注册成bean定义（会排除掉当前配置类） 如果扫描出来的还有配置类会进行递归解析
           - 处理 @Import annotations
           - 处理 @ImportResource annotations
           - 处理 @Bean methods 获取到我们配置类中所有标注了@Bean的方法
           - 处理配置类接口 默认方法的@Bean
           - 处理配置类的父类的 ，循环再解析
           - 处理完以上逻辑后，会会处理延时加载的DeferredImportSelectors。springboot就是通过这种方式来加载spring.factories中配置的自动装配类
             - 如果实现了ImportSelector，调用相关的aware方法。然后看是否为延时的DeferredImportSelectors，是的华不行行处理；不是的话调用selector  
             的selectImports，递归解析-- 直到成普通组件
             - 如果导入的的组件是ImportBeanDefinitionRegistrar，这里不直接调用，只是解析   
             否则当做配置类再解析，注意这里会标记：importedBy，  表示这是Import的配置的类
> 至此，注解方式配置解析流程梳理完毕。



