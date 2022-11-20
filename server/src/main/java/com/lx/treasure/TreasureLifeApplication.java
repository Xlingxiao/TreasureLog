package com.lx.treasure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author LX2
 */
@SpringBootApplication()
@ServletComponentScan("com.lx.treasure.config.filter")
public class TreasureLifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreasureLifeApplication.class, args);
	}

}
