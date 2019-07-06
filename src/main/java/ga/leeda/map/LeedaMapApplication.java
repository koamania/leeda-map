package ga.leeda.map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LeedaMapApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeedaMapApplication.class, args);
	}
}
