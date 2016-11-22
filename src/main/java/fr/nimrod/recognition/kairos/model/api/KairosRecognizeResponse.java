package fr.nimrod.recognition.kairos.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.nimrod.recognition.kairos.model.api.common.KairosImage;
import fr.nimrod.recognition.kairos.model.api.recognize.KairosCandidate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class KairosRecognizeResponse {

   private KairosImage[] images;
   
   private KairosCandidate candidates;
   
}
