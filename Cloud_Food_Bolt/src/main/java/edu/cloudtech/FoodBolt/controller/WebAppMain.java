package edu.cloudtech.FoodBolt.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class WebAppMain {

	/*public static void main(String[] args) {
	    SpringApplication app = new SpringApplication(WebAppMain.class);
	    System.out.print("Starting app with System Args: [" );
	    for (String s : args) {
	      System.out.print(s + " ");
	    }
	    System.out.println("]");
	    app.run(args);
	  }*/


	  @Bean
	  public ViewResolver viewResolver() {
	    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	    templateResolver.setTemplateMode("XHTML");
	    templateResolver.setPrefix("templates/");
	    templateResolver.setSuffix(".html");

	    SpringTemplateEngine engine = new SpringTemplateEngine();
	    engine.setTemplateResolver(templateResolver);

	    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	    viewResolver.setTemplateEngine(engine);
	    return viewResolver;
	  }
}
