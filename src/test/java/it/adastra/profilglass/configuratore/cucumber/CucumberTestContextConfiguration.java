package it.adastra.profilglass.configuratore.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import it.adastra.profilglass.configuratore.ProfilglassApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = ProfilglassApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
