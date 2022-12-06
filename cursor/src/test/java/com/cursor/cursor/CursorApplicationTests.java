package com.cursor.cursor;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest(httpPort = 8080)
@TestPropertySource(locations = "/classpath:catService.properties")
@SpringBootTest
class CursorApplicationTests {

	@Autowired
	protected Environment environment;

	@Test
	void contextLoads() throws IOException, InterruptedException {

		stubFor(get("/catfact.ninja/fact")
				.withHost(equalTo("localhost"))
				.willReturn(ok("some fact about cat")));


		var httpClient = HttpClient.newHttpClient();
		///var request = HttpRequest.newBuilder(URI.create("http://localhost:8080/catfact.ninja/fact")).build();
		///var request = HttpRequest.newBuilder(URI.create("https://catfact.ninja/fact")).build();
		var request = HttpRequest.newBuilder(URI.create(environment.getProperty("catService.url"))).build();
		var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		///Assertions.assertFalse(response.body().isEmpty());


		System.out.println(response.body());
	}

}
