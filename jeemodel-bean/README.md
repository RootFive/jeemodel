# jeemodel-bean 传输对象
# JavaBean  
　- JavaBean: JavaBean更多的是一种类的规格编写规范，也即包含一组含有setXxx()或者getXxx()方法的类都可以称之为javaBean。
- 通常，以entity，domain，vo，dto，pojo命名的包，包中的类都满足javaBean的规范，都是java对象，只不过用于不同场合罢了。

　- 按着Sun公司的定义，JavaBean是一个可重复使用的软件组件。但是实际上JavaBean就是一种Java类，通过封装属性和方法成为具有某种功能或者处理某个业务的对象，简称bean。
- JavaBean 是一种JAVA语言写成的可重用组件。按着Sun公司的定义，它的方法命名，构造及行为必须符合特定的约定：

> 1. 这个类必须有一个公共的缺省（默认无参）构造函数。
> 2. 这个类的属性使用getter和setter来访问，其他方法遵从标准命名规范。
> 3. 这个类应是可序列化的，要实现 Serializable接口用于实现 Bean 的持久性。

# POJO
- POJO全称是Plain Ordinary Java Object / Pure Old Java Object，是MartinFowler等发明的一个术语，中文可以翻译成：普通Java类/简单的Java类，具有一部分getter/setter方法的那种类就可以称作POJO。
- POJO特征如下：

> 1. POJO不包含业务逻辑或持久逻辑，因此不允许有业务方法，不能携带有connection之类的方法，也不继承、不实现任何其它特殊的Java框架的类或接口，如，EJB，JDBC等等。
> 2. 一般在web应用程序中,POJO这个名字用来强调它是一个普通java对象，而不是一个特殊的对象，其主要用来**指代那些没有遵从**特定的Java对象模型、约定或框架（如EJB）的Java对象。
> 3. 对于程序员来说，POJO实际意义就是普通的JavaBeans（简单的实体类），作用是可以方便的将POJO类当作对象进行使用，调用其get，set方法，是支持业务逻辑的协助类。
> 4. 当一个Pojo可序列化，有一个无参的构造函数，使用getter和setter方法来访问属性时，它就是一个JavaBean。
> 5. 理想地讲，一个POJO是一个不受任何限制的Java对象（除了Java语言规范）。 
> 6. 按照应用场合划分：pojo包下的类 , 可以是Domain，可以是VO，可以是DTO，可以Model，也可以是Entity。 


## POJO按照应用场合划分说明：domain、VO、DTO、Model、Entity
###  Entity:  
　- entity包下的类中的所有属性与数据库表格中的字段名逐一对应，Entity接近原始数据，是专用于EF的对数据库表的操作。

###  domain:  
　- domain包下的类中的属性除了包含数据库表格中的字段, 还可以包括其他属性。这个包国外很多项目经常用到。

###  PO（Persistent Object持久化对象）  
　- POJO在Dao层使用的持久化对象，它跟数据表形成一一对应的映射关系。和Entity的功能类似。

###  BO（Business Object）业务对象
　- POJO在业务层的体现，对于业务操作来说，更多的是从业务上来包装对象，可以由 Service 层输出的封装业务逻辑的对象。如一个User的BO，可能包括name, age, sex, privilege, group等，这些属性在数据库中可能会在多张表中，因为每一张表对应一个PO，而我们的BO需要这些PO组合起来(或说重新拼装)才能成为业务上的一个完整对象。

###  DTO(Data Transfer Object):  
　- POJO在系统间传递时。当我们需要在两个系统间传递数据时，一种方式就是将POJO序列化后传递，这个传递状态的POJO就是DTO。

###  VO(Value Object/View Object)  
　- POJO在表现层的体现，当我们处理完数据时，需要展现时，这时传递到表现层的POJO就成了VO。它就是为了展现数据时用的。vo包下的类中的属性是服务器与页面交互的视图数据，与数据库表格数据没有直接关系。

###  Model
　- Model接近业务对象，是为页面提供数据和数据校验的，所以与BO两者可以并存 



# 其他：
本项目的bean实际就是普通JavaBeans,也就是POJO（Plain Ordinary Java Object）简单的Java对象，
特殊的地方就是这些bean都是公用的，甚至是抽象的可重用组件。不为任何单个项目存在，主要有以下几类：

## DO（Data Object）基类：
　- 指数据库表一一对应的 POJO 类。此对象的子类与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。

## DTO（Data Transfer Object）基类：
数据传输对象，Service 或 Manager 向外传输的对象。

## BO（Business Object）：
业务对象，可以由 Service 层输出的封装业务逻辑的对象。

## Query：
数据查询对象，各层接收上层的查询请求。注意超过 2 个参数的查询封装，禁止使用Map 类来传输。

## VO（View Object）：
显示层对象，通常是 Web 向模板渲染引擎层传输的对象。

## Req（Request Object）：
与客户端交互，收集客户端的Form、Cookies、超链接的对象。

## Resp（Response Object）：
与客户端交互，用于动态响应客户端请示，控制发送给用户的信息，并将动态生成响应的对象。






