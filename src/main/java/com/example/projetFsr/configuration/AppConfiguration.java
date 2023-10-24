package com.example.projetFsr.configuration;

//si je veux intégrer une config XML venant de mon legacy, j'utilise l'@ImportResource
//@ImportResource(locations = {"classpath:spring/app-context-xml.xml"})

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//@ImportResource(locations = {"classpath:applicationContext.xml"})
@ComponentScan(basePackages = {"com.example.projetFsr.*"})
@Configuration
public class AppConfiguration {

    //plus rien dans la classe Java, juste une indication ou scanner les beans
}