package cl.tenpo.challenge;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.*;

@SpringBootTest
class ChallengeApplicationTests {


	@Test
	void contextLoads() {
	}
	@Test
	void connectBasic() {
		RedisURI uri = RedisURI.Builder
				.redis("localhost", 6379)
				.build();
		RedisClient client = RedisClient.create(uri);
		StatefulRedisConnection<String, String> connection = client.connect();
		RedisCommands<String, String> commands = connection.sync();

		commands.getset("percent1", "15.00");
		String result = commands.get("foo");
		System.out.println(result); // >>> bar

		connection.close();

		client.shutdown();
	}

	@Test
	void test(){

		String date = "2025-01-09T10:37:28.125524300";
		LocalDateTime dateTime = LocalDateTime.parse(date);
		boolean isafter = LocalDateTime.now().isAfter(dateTime.plusMinutes(30));
		System.out.println("isafter = " + isafter);

	}

}
