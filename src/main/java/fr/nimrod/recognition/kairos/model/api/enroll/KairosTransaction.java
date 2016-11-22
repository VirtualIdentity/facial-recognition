package fr.nimrod.recognition.kairos.model.api.enroll;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)   
public class KairosTransaction {

   private String status;
   
   @JsonProperty("face_id")
   private int faceId;
   
   @JsonProperty("subject_id")
   private String subjectId;
   
   private int width;
   
   private int height;
   
   private int topLeftX;
   
   private int topLeftY;
   
   private double confidence;
}
