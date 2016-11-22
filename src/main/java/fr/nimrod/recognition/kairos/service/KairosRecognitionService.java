package fr.nimrod.recognition.kairos.service;

import java.util.Arrays;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import fr.nimrod.recognition.kairos.model.api.KairosEnrollRequest;
import fr.nimrod.recognition.kairos.model.api.KairosEnrollResponse;
import fr.nimrod.recognition.kairos.model.api.KairosRecognizeRequest;

@Component("kairosRecognitionService")
public class KairosRecognitionService {

	@Value("${kairos.api.id}")
	private String apiId;
	
	@Value("${kairos.api.key}")
	private String apiKey;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Async
	public Future<ResponseEntity<KairosEnrollResponse>> enroll(KairosEnrollRequest request){
		
		HttpHeaders requestHeaders = createHeaders();
		
		HttpEntity<KairosEnrollRequest> entity = new HttpEntity<KairosEnrollRequest>(request, requestHeaders);
		
		ResponseEntity<KairosEnrollResponse> futureEntity = restTemplate.postForEntity("https://api.kairos.com/enroll", entity, KairosEnrollResponse.class);
				
		return new AsyncResult<>(futureEntity);
		
	}

	@Async
	public Future<ResponseEntity<Object>> recognize(KairosRecognizeRequest request){
	   
	   HttpHeaders requestHeaders = createHeaders();
	   
	   HttpEntity<KairosRecognizeRequest> entity = new HttpEntity<KairosRecognizeRequest>(request, requestHeaders);
	   
	   ResponseEntity<Object> futureEntity = restTemplate.postForEntity("https://api.kairos.com/recognize", entity, Object.class);
	   
	   return new AsyncResult<>(futureEntity);
	}

	private HttpHeaders createHeaders() {
      HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("app_id", apiId);
		requestHeaders.add("app_key", apiKey);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
      return requestHeaders;
   }
	
}
