#项目说明
AOP（Aspect Orient Programming），我们一般称为面向方面（切面）编程，作为面向对象的一种补充，用于处理系统中分布于各个模块的横切关注点，比如事务管理、日志、缓存等等。AOP实现的关键在于AOP框架自动创建的AOP代理，AOP代理主要分为静态代理和动态代理，静态代理的代表为AspectJ；而动态代理则以Spring AOP为代表。  
所谓的动态代理就是说AOP框架不会去修改字节码，而是在内存中临时为方法生成一个AOP对象，这个AOP对象包含了目标对象的全部方法，并且在特定的切点做了增强处理，并回调原对象的方法。  
Spring AOP使用的动态代理，所谓的动态代理就是说AOP框架不会去修改字节码，而是在内存中临时为方法生成一个AOP对象，这个AOP对象包含了目标对象的全部方法，并且在特定的切点做了增强处理，并回调原对象的方法。  
Spring AOP中的动态代理主要有两种方式，JDK动态代理和CGLIB动态代理。JDK动态代理通过反射来接收被代理的类，并且要求被代理的类必须实现一个接口。JDK动态代理的核心是InvocationHandler接口和Proxy类。  
如果目标类没有实现接口，那么Spring AOP会选择使用CGLIB来动态代理目标类。CGLIB（Code Generation Library），是一个代码生成的类库，是利用asm开源包，可以在运行时动态的生成某个类的子类。注意，CGLIB是通过继承的方式做的动态代理，因此如果某个类被标记为final，那么它是无法使用CGLIB做动态代理的。

##术语
1. 横切关注点  
   对哪些方法进行拦截，拦截后怎么处理，这些关注点称之为横切关注点
1. 切面（aspect）  
   类是对物体特征的抽象，切面就是对横切关注点的抽象
1. 连接点（joinpoint）  
   被拦截到的点，因为Spring只支持方法类型的连接点，所以在Spring中连接点指的就是被拦截到的方法，实际上连接点还可以是字段或者构造器
1. 切入点（pointcut）  
   对连接点进行拦截的定义
1. 通知（advice）  
   所谓通知指的就是指拦截到连接点之后要执行的代码，通知分为前置、后置、异常、最终、环绕通知五类
1. 目标对象  
   代理的目标对象
1. 织入（weave）  
   将切面应用到目标对象并导致代理对象创建的过程
1. 引入（introduction）  
   在不修改代码的前提下，引入可以在运行期为类动态地添加一些方法或字段

##切入点表达式 - 切入点函数
- 注解方式：@within、@target、@annotation、@args
    * @within和@target针对类的注解,@annotation是针对方法的注解，@args是针对参数的注解；
- 匹配方式：execution、within、this、args

###一些匹配例子 
- 定义在pointcutexp包和所有子包里的JoinPointObjP2类的任意方法的执行：execution(*com.test.spring.aop.pointcutexp..JoinPointObjP2.*(..))")
- pointcutexp包里的任意类： within(com.test.spring.aop.pointcutexp.*)
- pointcutexp包和所有子包里的任意类：within(com.test.spring.aop.pointcutexp..*)
- 实现了Intf接口的所有类,如果Intf不是接口,限定Intf单个类：this(com.test.spring.aop.pointcutexp.Intf)
- 当一个实现了接口的类被AOP的时候,用getBean方法必须cast为接口类型,不能为该类的类型
- 带有@Transactional标注的所有类的任意方法： @within(org.springframework.transaction.annotation.Transactional) @target(org.springframework.transaction.annotation.Transactional)
- 带有@Transactional标注的任意方法：
- @annotation(org.springframework.transaction.annotation.Transactional)
- @within和@target针对类的注解,@annotation是针对方法的注解
- 参数带有@Transactional标注的方法：@args(org.springframework.transaction.annotation.Transactional)
- 参数为String类型(运行是决定)的方法： args(String)

##AOP中切入点的执行顺序注解及相关说明：
- @Before，前置；
- @After，后置；
- @Around，环绕；
    * 可以决定目标方法在什么时候执行，如何执行，甚至可以完全阻止目标目标方法的执行；
    * 特点是可以改变执行目标方法的参数值，也可以改变执行目标方法之后的返回值；当需要改变目标方法的返回值时，只能使用Around方法；
    * 需要在线程安全的环境下使用；
- @AfterRunning，返回；
- @AfterThrowing，错误；

##关于执行顺序的问题
- 当方法符合切点规则不符合环绕通知的规则时候，执行的顺序如下:
@Before→@After→@AfterRunning(如果有异常→@AfterThrowing)

- 当方法符合切点规则并且符合环绕通知的规则时候，执行的顺序如下:
@Around→@Before→@After→@Around执行 ProceedingJoinPoint.proceed() 之后的操作→@AfterRunning(如果有异常→@AfterThrowing)