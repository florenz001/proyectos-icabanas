package org.icabanas.springsecurity;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.icabanas.springsecurity.selenium.WebDriverCliente;
import org.icabanas.springsecurity.selenium.WebDriverParaFireFoxFactory;
import org.icabanas.springsecurity.selenium.WebDriverParaIEFactory;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Clase que contiene m�todos de utilidad para la ejecuci�n de tests.
 * 
 * @author icabanas.ext
 *
 */
public abstract class BaseTest {

	protected WebDriver driver = null;
	
	@Before
	public void antesDeCadaTest(){
		String sSistemaOperativo = System.getProperty("os.name");
		if(sSistemaOperativo.equals("Linux"))
			driver = WebDriverCliente.obtenerWebDriver(new WebDriverParaFireFoxFactory());
		else
			driver = WebDriverCliente.obtenerWebDriver(new WebDriverParaIEFactory());
	}
	
	@After
	public void despuesDeCadaTest(){
		// cierra el navegador firefox que se abri� despu�s de cada test.
		driver.close();
	}
	
	/**
	 * 
	 */
	protected void autenticarUsuarioValido() {
		driver.get("http://localhost:9080/spring-security/login.jsp");
		
		// compruebo que estamos en la p�gina de login de Spring
		assertThat(driver.getTitle(), is("Login: Spring Security Web Application"));
		
		autenticarUsuario("username", "password");
	}
	
	protected void autenticarUsuarioConPasswordIncorrecta() {
		driver.get("http://localhost:9080/spring-security/login.jsp");
		
		// compruebo que estamos en la p�gina de login de Spring
		assertThat(driver.getTitle(), is("Login: Spring Security Web Application"));
		
		autenticarUsuario("username", "passwordErronea");
	}
	
	protected void autenticarUsuarioConUsuarioIncorrecto() {
		driver.get("http://localhost:9080/spring-security/login.jsp");
		
		// compruebo que estamos en la p�gina de login de Spring
		assertThat(driver.getTitle(), is("Login: Spring Security Web Application"));
		
		autenticarUsuario("usernameIncorrecto", "password");
	}
	
	/**
	 * @param usuario
	 * @param password
	 */
	private void autenticarUsuario(String usuario,String password){
		if(driver != null){
			// obtengo los campos username y password del formulario
			final WebElement usernameField = driver.findElement(By.name("j_username"));
			final WebElement passwordField = driver.findElement(By.name("j_password"));
			
			// introduzco valores en los campos
			usernameField.sendKeys(usuario);
			passwordField.sendKeys(password);
			
			// envio el formulario
			passwordField.submit();
		}
	}
	
}
