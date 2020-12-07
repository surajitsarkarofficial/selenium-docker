package flights;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class FlightsTests  {

    WebDriver driver;
    WebDriverWait wait;
    DesiredCapabilities dc;
    Properties prop;
    List<WebElement> elements;
    String hostname;

    By ele = By.xpath(
            "//nav[contains(@class,'rlGvde')]//span[@jsname='iSelEd']");

    @BeforeTest
    public void beforeTest() throws IOException {
        //If browser value is passed from system variable then browser will be set as per the variable
        // else browser value will be fetched from properties file.
        if(System.getProperty("BROWSER")!=null )
        {
            if(System.getProperty("BROWSER").equalsIgnoreCase("Chrome"))
            dc= DesiredCapabilities.chrome();
            else{
                dc = DesiredCapabilities.firefox();
            }
        }


            if(System.getProperty("HUB_HOST")!=null )
            {
                hostname = System.getProperty("HUB_HOST");
            }else{
                hostname="localhost";
            }

        String url = "http://" + hostname + ":4444/wd/hub";
        System.out.println("*************      "+ url + "           ***********************");
        driver = new RemoteWebDriver(new URL(url),dc);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver,20);
    }

    public boolean isAt() {

        return wait.until((d)-> ! this.elements.isEmpty());
    }

    public List<String> getLabels()
    {
        LinkedList<String> labelList = new LinkedList<>();
        for(WebElement ele : elements)
        {
            labelList.add(ele.getText());
        }
        return labelList;
    }

    public List<String> expectedLables()
    {
        List<String> data = new LinkedList<>();
        Collections.addAll(data,"Trips", "Things to do", "Flights", "Hotels", "Vacation rentals");
        return data;
    }

    @Test
    public void verifyLables()
    {

        driver.get("http://www.google.com/travel/flights");
        elements=driver.findElements(ele);
        Assert.assertTrue(isAt());
        System.out.println("Actual :: " +getLabels());
        System.out.println("Expected :: " +expectedLables());
        Assert.assertEquals(getLabels(),expectedLables());
    }
    @Test
    public void verifyLabelCounts()
    {
        driver.get("http://www.google.com/travel/flights");
        elements=driver.findElements(ele);
        Assert.assertTrue(isAt());
        System.out.println("Actual :: " +getLabels().size());
        System.out.println("Expected :: " +expectedLables().size());
        Assert.assertEquals(getLabels().size(),expectedLables().size());
    }


    @AfterTest
    public void afterTest()
    {
        driver.quit();
    }

    public void readProperty() throws IOException {
        String file = "my.properties";
        InputStream inputstream = getClass().getClassLoader().getResourceAsStream(file);
        prop= new Properties();
        prop.load(inputstream);

    }
}
