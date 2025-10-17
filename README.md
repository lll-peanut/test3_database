# 西二在线第三轮考核作业

## 项目结构

~~~ tree
|   pom.xml
|   README.md
+---src
|   +---main
|   |   +---java
|   |   |   \---com
|   |   |       \---peanut
|   |   |           +---constant
|   |   |           |       DatebaseConstant.java
|   |   |           |
|   |   |           +---expection
|   |   |           |       BaseExpection.java
|   |   |           |
|   |   |           +---mapper
|   |   |           |       GoodMapper.java
|   |   |           |       OrderMapper.java
|   |   |           |
|   |   |           +---pojo
|   |   |           |       Good.java
|   |   |           |       Order.java
|   |   |           |       OrderVO.java
|   |   |           |
|   |   |           +---service
|   |   |           |   |   GoodService.java
|   |   |           |   |   OrderService.java
|   |   |           |   |
|   |   |           |   \---ServiceImp
|   |   |           |           GoodServiceImp.java
|   |   |           |           OrderServiceImp.java
|   |   |           |
|   |   |           \---tool
|   |   |                   JdbcUtils.java
|   |   |                   TransactionUtils.java
|   |   |
|   |   \---resources
|   |           dbcpconfig.properties
|   |           druid.properties
|   |
|   \---test
|       \---java
|           \---com
|               \---peanut
|                       GoodTest.java
|                       OrderTest.java
~~~

## 模块接口设计思路

### constant包

DatebaseConstant类： 用于存储需要使用的字符串常量和double常量。

### expection包

BaseExpection类： 异常类

### pojo包

包内包含了Good,Order类以及Order类查询返回的OrderVO。

### mapper包

由于用的是jbdc连接，所以GoodMapper和OrderMapper是用类实现而不是用接口实现。

GoodMapper类与OrderMapper类 : 将sql语句放在类的私有字符串常量。实现了增删改查。

GoodMapper类：还有一个私有的方法来判断名字是否重复。

### service包

这里也是发现用的是jdbc连接，所以最后测试直接new出Imp实例，而不是new出Service。

GoodServiceImp与OrderServiceImp类： 实现了商品的数据检查并调用mapper进行增删改查与一个私有的检查方法检查数据。

OrderServiceImp类： 实现了对于订单与价格的升序排序。 实现了对于OrderVO数据的封装。

### tool

#### JdbcUtils类： 

首先用静态方法块将DataSource初始化来获取resources里的druid.properties配置进行连接。 

实现了getConnection()与getClose()方法。

实现了判断价格是否合理与更新页数的方法。

将增删改的操作进行了封装。update方法返回int表示是否插入成功，参数为sql语句与一个可变数组来实现增删改。

excuteQuery： 参数用一个函数式接口返回映射的实体类，分别定义orderQuery(...)与goodQuery(...)方法来实现各自的查询。

#### TransactionUtils类

定义了一个ThreadLocal<Connection>来存储Connection，作用是当本地线程的Connection存储起来，可以实现在增删改多个数据时的事务功能。

（这个类是不知道怎么实现多个数据增删改在csdn上复制的类，里面只有update是自己写的...）

## 单元测试

GoodTest和OrderTest对增删改查进行了测试。
