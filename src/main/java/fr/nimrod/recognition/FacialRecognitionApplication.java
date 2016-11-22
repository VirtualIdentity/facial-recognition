package fr.nimrod.recognition;

import java.util.concurrent.Executor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import fr.nimrod.recognition.storage.StorageProperties;
import fr.nimrod.recognition.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableAsync
public class FacialRecognitionApplication extends AsyncConfigurerSupport {

   public static void main(String[] args) {
      SpringApplication.run(FacialRecognitionApplication.class, args);
   }

   @Override
   public Executor getAsyncExecutor() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(2);
      executor.setMaxPoolSize(2);
      executor.setQueueCapacity(500);
      executor.setThreadNamePrefix("facialRecognition-");
      executor.initialize();
      return executor;
   }

   @Bean
   CommandLineRunner init(StorageService storageService) {
      return (args) -> {
         storageService.deleteAll();
         storageService.init();
      };
   }
}
