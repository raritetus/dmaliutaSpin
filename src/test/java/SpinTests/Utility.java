package SpinTests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static void captureScreenshot(WebDriver driver, String screenshotName)
    {
        try
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            TakesScreenshot ts = (TakesScreenshot)driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File("/Users/dmalyuta/IdeaProjects/SpinBackup/src/test/java/screenshots/" + screenshotName + " " + dateFormat.format(date) + ".png"));
            //System.out.println("Screenshot captured");
            Logger logger = LoggerFactory.getLogger(TestPolicy.class);
            logger.info("Screenshot captured");
        }
        catch (Exception e)
        {

            System.out.println("Exception while taken screenshot " + e.getMessage() );
        }


    }
}
