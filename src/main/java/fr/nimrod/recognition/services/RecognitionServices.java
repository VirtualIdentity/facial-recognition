package fr.nimrod.recognition.services;

public interface RecognitionServices {

   /**
    * Method that allow to enroll an image as a refential
    */
   void enroll();
   
   /**
    * Method that allow to recognize a facial image.
    */
   void recognize();
   
}
