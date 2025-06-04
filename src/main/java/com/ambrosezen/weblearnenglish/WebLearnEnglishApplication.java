package com.ambrosezen.weblearnenglish;

import com.ambrosezen.weblearnenglish.config.ConfigEnv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebLearnEnglishApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebLearnEnglishApplication.class, args);
  }

//  public static void main(String[] args) {
//    SpringApplication app = new SpringApplication(WebLearnEnglishApplication.class);
//    app.addInitializers(new ConfigEnv());
//    app.run(args);
//  }

}
