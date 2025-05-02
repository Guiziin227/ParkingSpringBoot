package com.guiweber.estacionamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/test/resources/sql/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test/resources/sql/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient testClient;
}
