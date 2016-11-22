package fr.nimrod.recognition.kairos.service;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.nimrod.recognition.kairos.model.api.KairosRecognizeResponse;
import fr.nimrod.recognition.kairos.model.api.common.KairosImage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperRecognizeTest {

   private String resultCall = "{\"images\": [{\"transaction\": {\"status\": \"success\",\"width\": 128,\"topLeftX\": 129,\"topLeftY\": 183,\"gallery_name\": \"KYC\",\"face_id\": 1,\"confidence\": 0.88029,\"subject_id\": \"Primael\",\"height\": 128},\"candidates\": [{\"subject_id\": \"Primael\",\"confidence\": 0.88029,\"enrollment_timestamp\": \"1479825333269\"}]}]}";
   
   @Autowired
   private ObjectMapper objectMapper;
   
   @Test
   public void test_mapping_json_to_response_object() throws JsonParseException, JsonMappingException, IOException{
      KairosRecognizeResponse response = objectMapper.readValue(resultCall, KairosRecognizeResponse.class);
      
      Assert.assertNotNull("Response should not be null", response);
      Assert.assertEquals("Size of images array should be 1", 1, response.getImages().length);
      
      KairosImage kairosImage = response.getImages()[0];
      
      Assert.assertEquals("Status transaction should be success", "success", kairosImage.getTransaction().getStatus());
      Assert.assertEquals("Width transaction should be 128", 128, kairosImage.getTransaction().getWidth());
      Assert.assertEquals("Height transaction should be 128", 128, kairosImage.getTransaction().getHeight());
      Assert.assertEquals("TopLeftX transaction should be 129", 129, kairosImage.getTransaction().getTopLeftX());
      Assert.assertEquals("TopLeftY transaction should be 183", 183, kairosImage.getTransaction().getTopLeftY());
      Assert.assertEquals("FaceId transaction should be 1", 1, kairosImage.getTransaction().getFaceId());
   }
}
