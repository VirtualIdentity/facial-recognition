package fr.nimrod.recognition.web.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollementDto {

   private String nom;
   
   private MultipartFile file;
   
}
