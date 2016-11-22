package fr.nimrod.recognition.kairos.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)   
public class KairosEnrollRequest {

	private String image;
	
	@JsonProperty("subject_id")
	private String subjectId;
	
	@JsonProperty("gallery_name")
	private String galleryName;
	
	private String selector;
	
	@JsonProperty("multiple_faces")
	private boolean multipleFaces;
	
	private double minHeadScale = 0.125;
	
}
