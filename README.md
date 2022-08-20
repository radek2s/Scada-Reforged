# Scada-Reforged
Build by @radek2s and based on [Scada-LTS](http://scada-lts.org) version 2.7v.


## 📖 Project overview
Goal of the project is to ged rid of old Scada-BR code. This will be a brand-new application 
that is inspired on Scada-LTS app. As a contributor of Scada-LTS I had a overall knowledge
how this application was working and I know also the good and bas sides. Because there 
are many Scada-LTS installations that are also based on the Scada-BR workflow in the 
main Scada-LTS application we can't change too much to provide a LTS support.

So this project can be a solution for modern user requirements. Where there is a need of performance
and a stability. This application should be minified to be able to work also on the 
RaspberryPI devices. So this can be a nice occasion to create a modern application that 
meets the modern world requirements.

Two major differences between classic Scada-LTS and Scada-Reforged are:
1. TimeSeriesDB to collect data and retrieve them for further calculations
2. Microservice architecture approach where every datasource will be a different microservice. 

All the features from classic Scada-LTS version is still accessible but the 
latest from mainstream will be stopped on the Scada-LTS version 2.7. This project
will be evolving in completely different way.


## ✨ Getting started

To start **Scada Reforged** perform following steps: 

1. Download all repository  
(or just `srf-environment` directory and `scada-reforged-environment.yml` file from root folder)
2. Start all services:
    ```shell
    docker-compose -f scada-reforged-environment.yml up
    ```
3. Open your browser on page: `localhost:8086` and configure InfluxDB service
   1. Create user `scadareforged` with password `scadareforged` on company `scadareforged`
   2. Create default bucket with name: `datasources` and finish initial configuration
   3. Using navigation menu open "Buckets" and move to "Api Tokens" tab.
   4. Find `scadareforged` token and copy it value
   5. Paste this to your `scada-reforged-environment.yml`  
   as `services.srf-core.environment.INFLUXDB_TOKEN` value
4. Stop `srf-core` container and start it again with following command:  
   ```shell
   docker-compose -f scada-reforged-environment.yml up srf-core
    ```
5. Open your browser on page: `localhost:4200`
6. Login as "admin"/"admin" and check out the pre-alpha version! 🎉 



