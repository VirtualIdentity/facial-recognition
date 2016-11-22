package fr.nimrod.recognition.kairos.model.api.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.nimrod.recognition.kairos.model.api.enroll.KairosAttribute;
import fr.nimrod.recognition.kairos.model.api.enroll.KairosTransaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)   
public class KairosImage {

   private KairosTransaction transaction;
   
   private KairosAttribute attributes;
}
