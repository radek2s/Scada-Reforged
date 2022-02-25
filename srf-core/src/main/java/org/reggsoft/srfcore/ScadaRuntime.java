package org.reggsoft.srfcore;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configuration
@EnableAutoConfiguration
public class ScadaRuntime  {

    private ApplicationContext applicationContext;
    CycleLogger x;

    public ScadaRuntime() {}

    @Bean
    @Scope("singleton")
    public ScadaRuntime getScadaRuntime() {
        return this;
    }

    public void runLogger() {
        if(x == null) {
            x = new CycleLogger();
            System.out.println("Starting logger");
            x.start();
        } else {
            x = new CycleLogger();
            System.out.println("Starting a new logger");
            x.start();
        }
    }

    public void stopLogger() {
        System.out.println("Stopping logger");
        if(x != null) {
            x.doStop();
            x.interrupt();
        }
    }

}
