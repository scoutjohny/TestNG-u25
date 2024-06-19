import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.plaf.TableHeaderUI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Wait {

    WebDriver driver;

    @BeforeMethod
    public void setup(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver_125.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //Implicitno čekanje - čekanje dinamički da se svaki element učita određeni broj sekundi
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://demoqa.com/select-menu");
    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test(enabled = false)
    public void demoQaWaitTest() throws InterruptedException {
        //Grubo čekanje - zamrzava izvršavanje programa na određeni broj milisekundi
        //Thread.sleep(3000);

        //Eksplicitno čekanje - čekanje dinamički određeni broj sekundi da se određeni element učita
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#oldSelectMenu")));

        //SELECT - Padajući meni
        Select select = new Select(driver.findElement(By.cssSelector("#oldSelectMenu")));
        select.selectByVisibleText("White");

        //Radio dugmići - mogu da se čekiraju ali ne i da se odčekiraju
        driver.get("https://demoqa.com/radio-button");
        if (!driver.findElement(By.cssSelector("#yesRadio")).isSelected()) {
            driver.findElement(By.cssSelector("[for='yesRadio']")).click();
        }

        //Checkbox - Suštinski se ponaša kao radio button ali može i da se odčekira
        driver.get("https://demoqa.com/checkbox");
        if (!driver.findElement(By.cssSelector(".rct-checkbox")).isDisplayed()) {
            driver.findElement(By.cssSelector(".rct-checkbox")).click();
        }


        //Otvaranje novog tab-a koristeći JavaScript
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open()");

        //Menjanje tab-ova preko liste otvorenih tabova
        js.executeScript("window.open()");
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandles.get(1));
        driver.get("https://www.google.rs");
        Thread.sleep(2000);
        js.executeScript("window.open()");
        List<String> windowHandles1 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandles1.get(2));
        driver.get("https://www.tehnomanija.rs");
        Thread.sleep(2000);
        driver.switchTo().window(windowHandles1.get(1));
        driver.close();
        Thread.sleep(2000);
        List<String> windowHandles2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windowHandles2.get(2));

        //logika za otvaranje više tab-ova i odlazak na URL na svakom od njih
        for (int i = 0; i < 10; i++) {
            js.executeScript("window.open()");
            List<String> windowHandles3 = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(windowHandles3.get(i + 1));
            driver.get("https://www.gigatron.rs/");
        }
    }

    @Test
    public void alert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        Thread.sleep(1000);
        //Prebacivanje na popup prozor:
        driver.switchTo().alert().accept();

        driver.findElement(By.id("confirmButton")).click();
        Thread.sleep(1000);
        String alertText = driver.switchTo().alert().getText();
        Assert.assertEquals(alertText,"Do you confirm action?");
        driver.switchTo().alert().accept();

        driver.findElement(By.id("promtButton")).click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.switchTo().alert().getText(),"Please enter your name");
        driver.switchTo().alert().sendKeys("Test");
        driver.switchTo().alert().accept();



    }

    @Test
    public void iFrameSellection() throws InterruptedException {
        //iFrame - sajt u okviru sajta
        //Prvo hvatamo ukupan broj iFrame-ova prisutnih na nekoj stranici
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        Thread.sleep(2000);
        int size = driver.findElements(By.tagName("iFrame")).size();

        driver.switchTo().frame(0); // preko index-a
        //driver.switchTo().frame("iframe-name"); //preko imena iFrame-a
        //driver.switchTo().frame("courses-iframe"); //preko ID-a iFrame-a
//        WebElement nekiElement = driver.findElement(By.cssSelector(""));
//        driver.switchTo().frame(nekiElement); //preko veb elementa koji se nalazi u iFrame-u

        driver.switchTo().defaultContent();

        //Kako naći index iFrame-a ako on nema ni ime ni id?
        int size1 = driver.findElements(By.tagName("iFrame")).size();

        int iFrame = 0;
        for(int i=0;i<size1;i++){
            driver.switchTo().frame(i);
            if(driver.findElements(By.cssSelector(".login-btn")).size()==1){
                System.out.println("Here it is: "+i);
                iFrame = i;
                driver.switchTo().defaultContent();
                break;
            }
            driver.switchTo().defaultContent();
        }
        Thread.sleep(2000);
        driver.switchTo().frame(iFrame);
        driver.findElement(By.cssSelector(".login-btn")).click();
        Thread.sleep(2000);

    }


}
