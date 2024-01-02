package com.practice.mikin.spring6lifecylce;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class LifeCycleBean implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware, ApplicationContextAware, BeanPostProcessor {
    private String javaVersion;
    public LifeCycleBean() {
        System.out.println("1 Constructor " +
                "\n   Denendency Injection Can be done from here" +
                "\n   when spring create components it calls contructor");

    }

    @Value("${java.specification.version}")
    public void setJavaVersion(String javaVersion) {
         this.javaVersion = javaVersion;
         System.out.println("2 Property Set Process. current java version is:"+ this.javaVersion);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("3 BeanNameAware. Bean name is"+ name);
    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("4 BeanFactoryAware. BeanFactory has been set"+ beanFactory.containsBean("LifeCycleBean"));
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("5 ApplicationContextAware. ApplicationContext has been set"+ applicationContext.containsBean("LifeCycleBean"));
    }

    @PostConstruct
    public void postContruct(){
        System.out.println("6 PostContruct. Post contruct of Bean method called");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("7 InitializingBean. Populate Properties The LifeCycleBean has its properties set!");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("8. postProcessBeforeInitialization. called just before intialization start for specific bean!" + beanName);
        if (bean instanceof SampleBean) {
            SampleBean sampleBean = (SampleBean) bean;
            System.out.println("8__ PostProcessBeforeInitialization Calling Before Init for SampleBean");
        }

        if (bean instanceof LifeCycleBean) {
            LifeCycleBean lifeCycleBean = (LifeCycleBean) bean;
            System.out.println("8#### PostProcessBeforeInitialization Calling Before Init for lifeCycleBean");
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("9. postProcessAfterInitialization. once bean initialized  start for specific bean!" + beanName);
        //we can do this only for different bean (like here: except LifeCycleBean)
        if (bean instanceof SampleBean) {
            SampleBean sampleBean = (SampleBean) bean;
            System.out.println("9__ PostProcessAfterInitialization Calling After Init for SampleBean");
        }

        if (bean instanceof LifeCycleBean) {
            LifeCycleBean lifeCycleBean = (LifeCycleBean) bean;
            System.out.println("9#### PostProcessAfterInitialization Calling After Init for lifeCycleBean");
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);

    }


    @PreDestroy
    public void preDestroy() {
        System.out.println("10 Pre-destroy is called just beofre destroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("11 Destroy method. Once destroy method called");
    }



}
