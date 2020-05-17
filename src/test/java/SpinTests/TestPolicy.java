package SpinTests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestPolicy extends WebDriverSettings{
    @Test
    public void authTest() throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(TestPolicy.class);
        logger.info("####################### TEST STARTED #####################");

        driver.get("https://te3-gapps1.spinbackup.com/");
        Utility.captureScreenshot(driver, "Login");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        logger.info("Login STARTED");

        try
        {
            //Login-password
            driver.findElement(By.name("login")).sendKeys("suser@asbru.cloud");
            driver.findElement(By.name("password")).sendKeys("Icq449515376");

            driver.findElement(By.className("signin")).click();

            Utility.captureScreenshot(driver, "Loginerr");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("storage-chart")));
        }
        catch(Exception e)
        {
            logger.info("Login FAILED");
            close();
        }

        WebElement securityPolices = driver.findElement(By.cssSelector("[href=\"/app/security-policy\"]"));
        securityPolices.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("CreateRule-BTN")));

        WebElement createRuleBtn = driver.findElement(By.className("CreateRule-BTN"));
        createRuleBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rule-name")));

        //getting a list of policies

        WebElement policyType = driver.findElement(By.cssSelector("[name=\"policy\"]"));

        Select select = new Select(policyType);

        List<WebElement> policyList = select.getOptions();

        List listPolicyString = new ArrayList<String>();

        for (WebElement webElement : policyList)
        {
            listPolicyString.add(webElement.getText());
        }

        logger.info("Got policies list");

        driver.findElement(By.cssSelector("[href=\"/app/security-policy\"]")).click();

        //policy creation

        for (Object item : listPolicyString)
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("CreateRule-BTN")));

            //System.out.println("loop " + item);
            logger.info("Creation policy " + item);
            driver.findElement(By.className("CreateRule-BTN")).click();

            //step 1

            WebElement inputName = driver.findElement(By.id("rule-name"));
            inputName.sendKeys("Test_Policy_"+ item);

            driver.findElement(By.cssSelector("[name=\"policy\"]")).click();

            Select tmpSelect = new Select(driver.findElement(By.cssSelector("[name=\"policy\"]")));
            tmpSelect.selectByVisibleText(item.toString());


            WebElement btnNext = driver.findElement(By.cssSelector("[data-role=\"btn.next\"]"));
            btnNext.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-role=\"btn.back\"]")));

            //step 2
            Thread.sleep(1000);

            if ((item.toString().equals("Restore Filtration"))) {
                Thread.sleep(500);
                driver.findElement(By.xpath("(//input[@name='subjects[]'])[2]")).sendKeys("Subject " + item);

                driver.findElement(By.xpath("(//input[@name='senders[]'])[2]")).sendKeys("Sender " + item);
            }

            if ((item.toString().equals("Abnormal Download Detection"))) {
                Thread.sleep(500);
                driver.findElement(By.id("abnormalDownloadActivityCount")).sendKeys("100");
            }

            By.cssSelector("[data-role=\"btn.next\"]").findElement(driver).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-role=\"btn.finish\"]")));

            //step 3

            WebElement btnCreate = driver.findElement(By.cssSelector("[data-role=\"btn.finish\"]"));
            btnCreate.click();

            if ((item.toString().equals("Shared Items Control"))) {
                Thread.sleep(1000);
                driver.findElement(By.xpath("//button[text()='No']")).click();
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class=\"mt0 sb-btn sb-collapse fs16\"]")));
            logger.info("Creation " + item + " SUCCESSFUL");
            Utility.captureScreenshot(driver, item.toString());
        }

        //assertions

        driver.findElement(By.xpath("(//a[@href='/app/monitor'])[2]")).click();
        String domainAudit = driver.findElement(By.xpath("//dt[contains(@class,'mt5 ml5')]")).getText();
        Assert.assertEquals(domainAudit,"Domain Audit");
        logger.info("Assertion 1 DONE");


        driver.findElement(By.xpath("//a[@href='/app/security']")).click();
        String securitySettings = driver.findElement(By.tagName("h1")).getText();
        Assert.assertEquals(securitySettings,"Security Settings");
        logger.info("Assertion 2 DONE");

        driver.findElement(By.cssSelector("[href=\"/app/security-policy\"]")).click();

        //deletion

        logger.info("Start policies deletion");

        WebElement checkBox = driver.findElement(By.className("col-xs-1"));
        WebElement box = checkBox.findElement(By.tagName("label"));
        Actions actions = new Actions(driver);
        actions.moveToElement(box).moveByOffset(9, 0).click().build().perform();


        WebElement btnRemove = driver.findElement(By.cssSelector("[data-role=\"sb.custom-policy.rule.remove\"]"));
        btnRemove.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sb-modal-label")));
        WebElement btnYes = driver.findElement(By.cssSelector("[data-role=\"yes\"]"));

        btnYes.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-role='message']")));

        String ruleHasBeen = driver.findElement(By.xpath("//div[@data-role='message']")).getText();
        Assert.assertEquals(ruleHasBeen, "Rule has been successful removed");

        logger.info("Deletion SUCCESSFUL");
        logger.info("TEST STATUS: SUCCESS");
        logger.info("========================== TEST END ==========================");
    }
}


