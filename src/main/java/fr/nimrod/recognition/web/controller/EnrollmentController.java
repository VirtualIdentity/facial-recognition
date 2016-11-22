package fr.nimrod.recognition.web.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.nimrod.recognition.kairos.model.api.KairosEnrollRequest;
import fr.nimrod.recognition.kairos.model.api.KairosEnrollResponse;
import fr.nimrod.recognition.kairos.repositories.KairosRecognitionRepository;
import fr.nimrod.recognition.storage.StorageFileNotFoundException;
import fr.nimrod.recognition.storage.StorageService;
import fr.nimrod.recognition.web.dto.EnrollementDto;

@Controller
public class EnrollmentController {

   private final StorageService storageService;

   @Autowired
   private KairosRecognitionRepository repository;
   
   @Autowired
   public EnrollmentController(StorageService storageService) {
      this.storageService = storageService;
   }

   @GetMapping("/enrollement")
   public String listUploadedFiles(EnrollementDto enrollement, Model model) throws IOException {

      model.addAttribute("files",
            storageService.loadAll()
                  .map(path -> MvcUriComponentsBuilder
                        .fromMethodName(EnrollmentController.class, "serveFile", path.getFileName().toString()).build()
                        .toString())
                  .collect(Collectors.toList()));

      return "enrollement";
   }

   @GetMapping("/files/{filename:.+}")
   @ResponseBody
   public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

      Resource file = storageService.loadAsResource(filename);
      return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
   }

   @PostMapping("/enrollement")
   public String handleFileUpload(EnrollementDto enrollement, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException, InterruptedException, ExecutionException {

      storageService.store(file);
      redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

      //On y va a la sauvage
      KairosEnrollRequest kairoEnrollRequest = new KairosEnrollRequest();
      
      kairoEnrollRequest.setGalleryName("demo");
      kairoEnrollRequest.setSubjectId(enrollement.getNom());
      String fileName = enrollement.getNom() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
      
      storageService.storeAsFile(file, fileName);
      
      storageService.delete(file.getOriginalFilename());
      
      Path fileStored = storageService.load(fileName);
      
      kairoEnrollRequest.setImage(Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(fileStored.toFile())));
            
      Future<ResponseEntity<KairosEnrollResponse>> result = repository.enroll(kairoEnrollRequest);
      
      while(!result.isDone()){
         Thread.sleep(100);
      }
      
      String message = "";
      
      if(result.get().getStatusCode().is2xxSuccessful())
         message = "That's good " + enrollement.getNom() + " is enroll";
      else {
         message = "That's too bad ";
      }
      redirectAttributes.addFlashAttribute("message", message);
      return "redirect:/enrollement";
   }

   @ExceptionHandler(StorageFileNotFoundException.class)
   public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
      return ResponseEntity.notFound().build();
   }

}
