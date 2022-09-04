package com.primeAspect.countryflags;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class CountryflagsApplicationTests {

	@Test
	void contextLoads() {
		List<String> list = new ArrayList<>();
		Collections.addAll(list,"gb","pe","col");
		FlagService flagService = new FlagService();
		try {
			flagService.getURLFlags(list,"D:/OmSU/countryflags","png");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
