# Scada-Reforged
Build by @radek2s and based on [Scada-LTS](http://scada-lts.org) version 2.7v.


## Project overview
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


## Getting started

Start all required services:
```shell
docker-compose -f  scada-reforged-environment.yml up
```

Then create a InfluxDB user, organization and bucket.  
After that copy the `API_TOKEN` value to **Scada-Reforged-core environment** variables



