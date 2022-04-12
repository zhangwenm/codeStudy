## springboot  

#### 优势
- Spring Boot 的最大的优势是“约定优于配置“。“约定优于配置“是一种软件设计范式，开发人员按照约定的方式来进行编程，  
可以减少软件开发人员需做决定的数量，获得简单的好处，而又不失灵活性。

####（自动配置原理）
- spring的核心注解SpringBootApplication，@SpringBootApplication看作是 @Configuration、@EnableAutoConfiguration、@ComponentScan   
注解的集合
  - @EnableAutoConfiguration：启用 SpringBoot 的自动配置机制
  - @Configuration：允许在上下文中注册额外的 bean 或导入其他配置类
  - @ComponentScan： 扫描被@Component (@Service,@Controller)注解的 bean，注解默认会扫描启动类所在的包下所有的类 ，可以自定义不扫描某些 bean
- @EnableAutoConfiguration:实现自动装配的核心注解
  - EnableAutoConfiguration 只是一个简单地注解，自动装配核心功能的实现实际是通过 import注解引入的AutoConfigurationImportSelector类。
  - AutoConfigurationImportSelector:加载自动装配类
  - AutoConfigurationImportSelector 类实现了DeferredImportSelector ，有延时加载的功能
  - 内部在解析@Import注解引入的AutoConfigurationImportSelector类时会调用getAutoConfigurationEntry方法
  - getAutoConfigurationEntry方法进行扫描具有META-INF/spring.factories文件的jar包（spi机制）
  - 从META‐INF/spring.factories中获得候选的自动配置类
  - 然后筛选出以 EnableAutoConfiguration 为 key 的数据
  - 加载，AutoConfigurationImportFilter，经历了一遍筛选，@ConditionalOnXXX 中的所有条件都满足
  - 将这些Bean加载到 IOC 容器中，实现自动配置功能
#### Spring Boot Starter 的工作原理是什么？
- ① Spring Boot 在启动时会去依赖的 Starter 包中寻找 resources/META-INF/spring.factories 文件，  
然后根据文件中配置的 Jar 包去扫描项目所依赖的 Jar 包。
- ② 根据 spring.factories 配置加载 AutoConfigure 类
- ③ 根据 @Conditional 注解的条件，进行自动配置并将 Bean 注入 Spring Context
总结一下，其实就是 Spring Boot 在启动的时候，按照约定去读取 Spring Boot Starter  
的配置信息，再根据配置信息对资源进行初始化，并注入到 Spring 容器中。这样 Spring Boot  
启动完毕后，就已经准备好了一切资源，使用过程中直接注入对应 Bean 资源即可。  
  