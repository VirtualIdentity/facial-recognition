package fr.nimrod.recognition.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

   @Autowired
   public TemplateResolver templateResolver;
   
   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/home").setViewName("home");
      registry.addViewController("/").setViewName("home");
      
      registry.addViewController("/enrollement.html").setViewName("enrollement");
      registry.addViewController("/recognize.html").setViewName("recognize");
     
   }
   
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(localeChangeInterceptor());
   }
   
   @Override
   public void addResourceHandlers(final ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/resources/**").addResourceLocations("/", "/resources/");
   }
   
   @Bean
   public LocaleChangeInterceptor localeChangeInterceptor() {
           LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
           localeChangeInterceptor.setParamName("language");
           return localeChangeInterceptor;
   }

   @Bean(name = "localeResolver")
   public CookieLocaleResolver localeResolver() {
           CookieLocaleResolver localeResolver = new CookieLocaleResolver();
           Locale defaultLocale = new Locale("fr");
           localeResolver.setDefaultLocale(defaultLocale);
           return localeResolver;
   }

   @Bean
   public ReloadableResourceBundleMessageSource messageSource() {
           ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
           messageSource.setBasename("classpath:lang/messages");
           messageSource.setCacheSeconds(10); //reload messages every 10 seconds
           return messageSource;
   }
   
   @Bean
   public UrlTemplateResolver urlTemplateResolver() {
       return new UrlTemplateResolver();
   }
   
   @Bean
   public SpringTemplateEngine templateEngine(){
      SpringTemplateEngine templateEngine = new SpringTemplateEngine();
      templateEngine.addTemplateResolver(templateResolver);
      templateEngine.addTemplateResolver(urlTemplateResolver());
      templateEngine.addDialect(new LayoutDialect());
      return templateEngine;
   }
}
