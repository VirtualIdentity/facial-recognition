package fr.nimrod.recognition.kairos.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import fr.nimrod.recognition.kairos.model.api.KairosEnrollRequest;
import fr.nimrod.recognition.kairos.model.api.KairosEnrollResponse;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EnrollmentServiceIntegrationTest {

	@Autowired
	private KairosRecognitionService kairosRecognitionService;
	
	@Test
	public void enroll_should_be_success() throws InterruptedException, ExecutionException {
		log.info("Launch success test");
		KairosEnrollRequest request = new KairosEnrollRequest();
		
		request.setImage("http://media.kairos.com/kairos-elizabeth.jpg");
		request.setGalleryName("test");
		request.setSubjectId("testImage");
		
		Future<ResponseEntity<KairosEnrollResponse>> result = kairosRecognitionService.enroll(request);
		
		while(!result.isDone()){
			log.debug("stand loop");
			Thread.sleep(100);
		}
		
		log.info("End calling method");
		
//		Assert.assertNotNull(result);
//		ResponseEntity<Object> responseResult = result.get();
		Assert.assertEquals("Response should've status code 200", 200, result.get().getStatusCodeValue());
	}
}
