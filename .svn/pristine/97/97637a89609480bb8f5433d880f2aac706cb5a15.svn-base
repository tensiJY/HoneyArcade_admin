package honeyarcade.admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BCryptTest {
  
	@Test
	public void init() {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		log.info(bc.encode("zhfldk"));
	}
}
