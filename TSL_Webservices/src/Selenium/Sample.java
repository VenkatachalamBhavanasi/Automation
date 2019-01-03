package Selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Sample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.setProperty("webdriver.chrome.driver", "D:\\SSP\\SSP\\chromedriver.exe");
   WebDriver driver = new ChromeDriver();
   driver.manage().window().maximize();

   driver.get("https://www.trujet.com/ebooking/home/");

   driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
   /*List<WebElement> list = driver.findElements(By.xpath(".//*[@id='drpOrigin']/option"));
   System.out.println(list.size());
   

   for (int i = 2; i < list.size(); i++) {
          String x = driver.findElement(By.xpath(".//*[@id='drpOrigin']/option[" + i + "]")).getText();
          System.out.println(x);
   }*/
   
   boolean isAdvanced = driver.findElement(By.xpath(".//*[@id='drpOrigin']/option")).getAttribute("src").contains("advanced_button.jpg");



	}

}
