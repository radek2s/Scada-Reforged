package org.reggsoft.srfcore;

import org.reggsoft.srfcore.persistance.dao.DataSourceVirtualRepository;
import org.reggsoft.srfcore.persistance.entity.DataSourceVirtual;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration
public class ScadaRuntime  {

    private ApplicationContext applicationContext;
    private List<DataSourceVirtual> dataSources;
    private List<DataSourceVirtualRuntime> dataSourceRT;
    CycleLogger x;

    @Autowired
    DataSourceVirtualRepository dsRepository;

    public ScadaRuntime() {
        dataSourceRT = new ArrayList<>();
    }

    @Bean
    @Scope("singleton")
    public ScadaRuntime getScadaRuntime() {
        return this;
    }

    public void initDataSources() {
        dataSources = dsRepository.findAll();

    }

    public List<DataSourceVirtual> getRunningDataSources() {
        initDataSources();
        return dataSources;
    }

    public void startDataSources() {
        dataSources.forEach(ds -> {
            dataSourceRT.add(new DataSourceVirtualRuntime(ds));
        });
        dataSourceRT.forEach(Thread::start);
    }

    public void stopDataSources() {
        dataSourceRT.forEach(DataSourceVirtualRuntime::stopDataSource);
        dataSourceRT.clear();
    }

    public void runLogger() {
        System.out.println(dataSources.size());
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
