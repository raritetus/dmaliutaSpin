package SpinTests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class Utility {
    public static void captureScreenshot(WebDriver driver, String screenshotName)
    {
        try
        {
            TakesScreenshot ts = (TakesScreenshot)driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File("/Users/dmalyuta/IdeaProjects/SpinBackup/src/test/java/screenshots/" + screenshotName + ".png"));
            System.out.println("Screenshot captured");
        }
        catch (Exception e)
        {
            System.out.println("Exception while taken screenshot" + e.getMessage());
        }


    }
}
