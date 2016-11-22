package fr.nimrod.recognition.config;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import fr.nimrod.recognition.config.interceptors.LoggingRequestInterceptor;

@Configuration
public class RestConfig {

	@Value("${proxy.enable}")
	private boolean proxyEnable;

	@Bean
	public RestTemplate restTemplate() {
		
		ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
		
		RestTemplate restTemplate = new RestTemplate();
		
		if (proxyEnable) {
			SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
			Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("172.25.4.55", 3128));
			simpleClientHttpRequestFactory.setProxy(proxy);

			requestFactory = new BufferingClientHttpRequestFactory(simpleClientHttpRequestFactory);
			
		}
		
		ClientHttpRequestInterceptor loggingRequestInterceptor = new LoggingRequestInterceptor();
		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
		ris.add(loggingRequestInterceptor);
		restTemplate.setInterceptors(Arrays.asList(loggingRequestInterceptor));
		restTemplate.setRequestFactory(requestFactory);
		
		return restTemplate;
	}
	
}
