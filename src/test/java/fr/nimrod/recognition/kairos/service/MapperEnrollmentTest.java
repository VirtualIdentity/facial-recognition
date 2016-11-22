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

import fr.nimrod.recognition.kairos.model.api.KairosEnrollResponse;
import fr.nimrod.recognition.kairos.model.api.common.KairosImage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperEnrollmentTest {

   private String resultCall = "{\"images\":[{\"attributes\":{\"gender\":{\"type\":\"F\"},\"age\":27},\"transaction\":{\"status\":\"success\",\"width\":785,\"topLeftX\":386,\"topLeftY\":711,\"gallery_name\":\"test\",\"timestamp\":\"1479834172812\",\"face_id\":1,\"confidence\":0.99997,\"subject_id\":\"testImage\",\"height\":784}}]}";
   
   @Autowired
   private ObjectMapper objectMapper;
   
   @Test
   public void test_mapping_json_to_response_object() throws JsonParseException, JsonMappingException, IOException{
      KairosEnrollResponse response = objectMapper.readValue(resultCall, KairosEnrollResponse.class);
      
      Assert.assertNotNull("Response should not be null", response);
      Assert.assertEquals("Size of images array should be 1", 1, response.getImages().length);
      
      KairosImage kairosImage = response.getImages()[0];
      
      Assert.assertEquals("Status transaction should be success", "success", kairosImage.getTransaction().getStatus());
      Assert.assertEquals("Width transaction should be 785", 785, kairosImage.getTransaction().getWidth());
      Assert.assertEquals("Height transaction should be 784", 784, kairosImage.getTransaction().getHeight());
      Assert.assertEquals("TopLeftX transaction should be 386", 386, kairosImage.getTransaction().getTopLeftX());
      Assert.assertEquals("TopLeftY transaction should be 711", 711, kairosImage.getTransaction().getTopLeftY());
      Assert.assertEquals("FaceId transaction should be 1", 1, kairosImage.getTransaction().getFaceId());
   }
}
