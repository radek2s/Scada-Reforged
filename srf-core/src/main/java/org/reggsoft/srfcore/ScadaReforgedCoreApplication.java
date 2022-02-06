package org.reggsoft.srfcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScadaReforgedCoreApplication {

	public static void main(String[] args) {
		Logger log = LoggerFactory.getLogger(ScadaReforgedCoreApplication.class);
		SpringApplication.run(ScadaReforgedCoreApplication.class, args);
		log.info("Scada-Reforged is ruunning...");
	}

}
