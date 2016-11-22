package fr.nimrod.recognition.kairos.model.api.recognize;

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
public class KairosCandidate {

   @JsonProperty("subject_id")
   private String subjectId;
  
   private double confidence;
   
   @JsonProperty("enrollment_timestamp")
   private long enrollmentTimeStamp;
   
}
