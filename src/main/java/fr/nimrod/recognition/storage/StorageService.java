package fr.nimrod.recognition.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

   void init();
   
   void store(MultipartFile file);
   
   void storeAsFile(MultipartFile multipartFile, String fileName) throws IOException;
   
   Stream<Path> loadAll();
   
   Path load(String filename);
   
   Resource loadAsResource(String filename);
   
   void deleteAll();
   
   void delete(String filename) throws IOException;
}
