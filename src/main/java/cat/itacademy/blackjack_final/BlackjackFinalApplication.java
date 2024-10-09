package cat.itacademy.blackjack_final;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "cat.itacademy.blackjack_final.persistence.repositories.mongo")
@EnableR2dbcRepositories(basePackages = "cat.itacademy.blackjack_final.persistence.repositories.r2dbc")
public class BlackjackFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlackjackFinalApplication.class, args);
	}

}
