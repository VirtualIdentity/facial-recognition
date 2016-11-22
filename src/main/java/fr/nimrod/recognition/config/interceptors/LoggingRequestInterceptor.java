package fr.nimrod.recognition.config.interceptors;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		ClientHttpResponse response = execution.execute(request, body);

		log(request, body, response);

		return response;
	}

	private void log(HttpRequest request, byte[] body, ClientHttpResponse response) throws IOException {
		log.info("URI call {}", request.getURI().toString());
		
		log.info("verb call {}", request.getMethod().name());

		log.info("headers");
		request.getHeaders().forEach((k, v) -> log.info(k + " : " + v));

		log.info("Body");
		log.info(new String(body));
		
		log.info("Response");
		log.info("headers");
		response.getHeaders().forEach((k, v) -> log.info(k + " : " + v));
		
		log.info("body");
		
		int n = response.getBody().available();
		byte[] bytes = new byte[n];
		response.getBody().read(bytes, 0, n);
		String s = new String(bytes, StandardCharsets.UTF_8);
		
		log.info(s);
	}
}