package Selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumSample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "D:\\SSP\\SSP\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//driver.findElement(By.name("")).getText().trim().replaceAll("\\s", "").equalsIgnoreCase("");
		String d= driver.findElement(By.name("")).getText().trim().replaceAll("\\s", "");
		
		driver.get("https://www.trujet.com/ebooking/home/");

		   driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		   /*List<WebElement> list = driver.findElements(By.xpath(".//*[@id='drpOrigin']/option"));
		   System.out.println(list.size());
		   

		   for (int i = 2; i < list.size(); i++) {
		          String x = driver.findElement(By.xpath(".//*[@id='drpOrigin']/option[" + i + "]")).getText();
		          System.out.println(x);
		   }*/
		   
		  Select options = new Select(driver.findElement(By.xpath(".//*[@id='drpOrigin']")));
		  
		  List<WebElement> list = options.getOptions();
		  
		  for (WebElement eachElement : list) {
			  
			  System.out.println(eachElement.getText());
			
		}


	}

}
  		