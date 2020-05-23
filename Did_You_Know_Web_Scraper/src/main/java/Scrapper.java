import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.util.List;

public class Scrapper {
    /**
     * refer : https://github.com/ksahin/introWebScraping/blob/master/src/main/java/blog/article1/WebScraper.java
     * @param args
     */
    public static void main(String[] args) {
        String baseUrl = "https://www.did-you-knows.com/";
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setUseInsecureSSL(false);
        try {
            FileWriter fileWriter  = new FileWriter("did_you_know_facts.txt");
            System.setProperty("webdriver.chrome.driver","/usr/local/bin/chromedriver");
            WebDriver driver = new ChromeDriver();
            driver.get(baseUrl);
            List<WebElement> l = ((ChromeDriver) driver).findElementsByClassName("dykText");
            for(WebElement we :l) {
                fileWriter.write(we.getText()+"\r\n");
            }
//            List<WebElement> pagination =driver.findElements(By.xpath("//div[@class='pagePagintionLinks']//a"));
//            if(pagination .size()>0) {
//                System.out.println(("pagination exists"));
//            }
            boolean result = true;
            int pages = 1;
            while(result) {
                try {
                    System.out.println(("pagination exists"));
                    System.out.println("Total "+pages +" pages scraped");
                    driver.findElement(By.xpath("//div[@class='pagePagintionLinks']//a[@class='next']")).click();
                    List<WebElement> ls = ((ChromeDriver) driver).findElementsByClassName("dykText");
                    for(WebElement we :ls) {
//                        System.out.println(we.getText());
                        fileWriter.write(we.getText()+"\r\n");
                    }
                    Thread.sleep(5000);
                    pages++;
                } catch (Exception e) {
                    result = false;
                    e.printStackTrace();
                }
            }
            fileWriter.close();
            } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
