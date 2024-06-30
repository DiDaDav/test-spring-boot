package com.didadav.testspringboot.cucumber;

import com.didadav.testspringboot.TestSpringBootApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(classes = {TestSpringBootApplication.class},  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration(classes = TestSpringBootApplication.class, loader = SpringBootContextLoader.class)
public class SpringIntegrationTest {

}
