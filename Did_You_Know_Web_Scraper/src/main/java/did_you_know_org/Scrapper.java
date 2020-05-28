package did_you_know_org;

import com.gargoylesoftware.htmlunit.WebClient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.util.List;

public class Scrapper {
    public static void main(String[] args) {
        String baseUrl = "https://didyouknow.org/facts/";
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setUseInsecureSSL(false);
        try {
            FileWriter fileWriter  = new FileWriter("did_you_know_facts_org_2.txt");
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            WebDriver driver = new ChromeDriver();
            driver.get(baseUrl);
            WebDriverWait wait = new WebDriverWait(driver, 5000);
            List<WebElement> anchors = driver.findElements(By.tagName("p"));
            for (int i = 0; i < anchors.size(); i++) {
                WebElement anchor = anchors.get(i);
                System.out.println(anchor.getText());
                if (anchor.getText().equals("There are MANY more interesting facts and stories around here! Search now:")) {
                    System.out.println("Reached end...breaking");
                    break;
                }
                anchor = anchor.findElement(By.tagName("a"));
                if (!anchor.getAttribute("href").equals("")) {
                    fileWriter.write(anchor.getText()+"\r\n");
//                    System.out.println(anchor.getText()+"\r\n");
                    System.out.println("exists");
                    Thread.sleep(2000);
                    anchor.click();
                    fileWriter.write(driver.findElements(By.xpath("//div[@id='content']")).get(0).getText()+"\r\n\n\n");
//                    System.out.println(driver.findElements(By.xpath("//div[@id='content']")).get(0).getText()+"\r\n\n\n");
                    Thread.sleep(2000);
                    driver.navigate().back();
                    Thread.sleep(2000);
                    anchors = driver.findElements(By.tagName("p"));
                    System.out.println("Total "+(i+1)+" links clicked");
//                    Thread.sleep(3000);
                }
            }
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
