package com.example.abmaro;

import com.example.abmaro.futuretransaction.FutureTransactionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AbmaroApplicationTests {

	@Autowired
	FutureTransactionController futureTransactionController;

	@Test
	void contextLoads() {
		assertThat(futureTransactionController).isNotNull();
	}

}
