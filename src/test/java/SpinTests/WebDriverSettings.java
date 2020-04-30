package SpinTests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverSettings {
    public WebDriver driver;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "/Users/dmalyuta/IdeaProjects/SpinBackup/src/test/resources/drivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        //options.setHeadless(true);
        options.addArguments("window-size=1280,800");
        driver = new ChromeDriver(options);
    }


    @After
    public void close()
    {
        driver.quit();
    }
}
