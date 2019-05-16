# 问题
Spring中，只配置了接口类型的bean，在装配其他类的时候，需要专配的属性能否为接口的子类？

比如下面这样,Cat是Animal的实现类，并且配置了Animal这个bean

接口Animal：
```java
public interface Animal {
    public String getName();
}


```

实现类Cat：
```java
public class Cat implements Animal {
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

装配的Animal：
```java
public class Config {
    @Bean
    public Animal cat(){
        return new Cat("Hello Kitty");
    }

}
```

然后，我们想要在一个动物商店里，自动专配一只猫，想下面这样：
```java
@Component
public class AnimalStore implements Store {
    /**
     * 虽然Cat是Animal的子类，但是我们只只配置了Animal的bean，所以，这里的自动装配会失败。
     */
    @Autowired
    private Cat cat;

    @Override
    public Animal getAnimal() {
        return cat;
    }
}
```
# 答案
## 第一次实验，不可以，会提示如下异常：

```
Exception in thread "main" org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'animalStore': Unsatisfied dependency expressed through field 'cat'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'beans.Cat' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:588)
	at org.springframework.beans.factory.annotation.InjectionMetadata.inject(InjectionMetadata.java:88)
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessPropertyValues(AutowiredAnnotationBeanPostProcessor.java:366)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1272)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:553)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:483)
	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:312)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:230)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:308)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:761)
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:867)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:543)
	at Main.main(Main.java:11)
Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'beans.Cat' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.raiseNoMatchingBeanFound(DefaultListableBeanFactory.java:1493)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1104)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1066)
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:585)
	... 13 more
```

## 第二次实验，可以
第二次实验，我换了一下Store的装配方式，不采用自动装配，而是把Store也用java代码的方式装配：
```java
public class Config {
    @Bean
    public Cat c4at3(){
        return new Cat("Hello Kitty");
    }
    @Bean
    public Store store(){
        return new AnimalStore();
    }

}
```
这样，居然就ok了，顺利通过。

另外我还发现，如果采用这种java代码的方式装配store，那么装配Cat的返回值和store中cat字段的类型之间就会没有关系，
只要保证对象本身的类型是字段类型的子类就可以。也就是说，只要Cat这个对象在那，那么无论什么类型，都可以。
比如我返回Cat，但是字段类型是Animal，也是ok的。


# 结论
Spring的@Autowired真的不可靠，以不同的方式装配Bean，依赖注入的效果居然不一样，坑爹啊。但是，一般业务当中很难出现这个问题，因为不会有人蛋疼到装配Animal，注入的确实Cat。
