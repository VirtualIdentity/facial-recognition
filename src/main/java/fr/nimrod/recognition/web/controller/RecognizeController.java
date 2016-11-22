package fr.nimrod.recognition.web.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.nimrod.recognition.kairos.model.api.KairosRecognizeRequest;
import fr.nimrod.recognition.kairos.model.api.KairosRecognizeResponse;
import fr.nimrod.recognition.kairos.model.api.common.KairosImage;
import fr.nimrod.recognition.kairos.repositories.KairosRecognitionRepository;
import fr.nimrod.recognition.storage.StorageService;
import fr.nimrod.recognition.web.dto.EnrollementDto;
import fr.nimrod.recognition.web.dto.RecognizeDto;

@Controller
public class RecognizeController {

   private final StorageService storageService;

   @Autowired
   public RecognizeController(StorageService storageService) {
      this.storageService = storageService;
   }

   @Autowired
   private KairosRecognitionRepository repository;

   @GetMapping("/recognize")
   public String showMe(RecognizeDto recognizeDto, Model model) throws IOException {
      return "recognize";
   }
   
   @PostMapping("/recognize")
   public String handleFileUpload(RecognizeDto recognizeDto, @RequestParam("file") MultipartFile file,
         RedirectAttributes redirectAttributes)
         throws IllegalStateException, IOException, InterruptedException, ExecutionException {

      String fileName = file.getOriginalFilename();

      storageService.storeAsFile(file, fileName);

      // On y va a la sauvage
      KairosRecognizeRequest kairoRecognizeRequest = new KairosRecognizeRequest();
      kairoRecognizeRequest.setGalleryName("demo");

      Path fileStored = storageService.load(fileName);

      kairoRecognizeRequest
            .setImage(Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(fileStored.toFile())));

      Future<ResponseEntity<KairosRecognizeResponse>> result = repository.recognize(kairoRecognizeRequest);

      while (!result.isDone()) {
         Thread.sleep(100);
      }

      storageService.delete(fileName);
      
      String message = "";

      if (result.get().getStatusCode().is2xxSuccessful()) {
         message = "Found ";
         for (KairosImage image : result.get().getBody().getImages()) {
            message += image.getTransaction().getSubjectId() + " - ";
         }
      } else {
         message = "Nobody ";
      }
      redirectAttributes.addFlashAttribute("message", message);
      return "redirect:/recognize";
   }
}
