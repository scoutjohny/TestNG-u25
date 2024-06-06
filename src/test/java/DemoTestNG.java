import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class DemoTestNG {

    WebDriver driver;

    @BeforeMethod
    @Parameters({"browser"})
    public void setup(String browser){
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver_125.exe");
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")){
            System.setProperty("webdriver.gecko.driver","src/main/resources/geckodriver0322.exe");
            driver = new FirefoxDriver();
        }

        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    @Parameters({"username","password","url","errorMessage","testType"})
    public void loginToSauceDemo(String username, String password, String url, @Optional String errorMessage, String testType){
        if(!username.equals("empty")){
            driver.findElement(By.id("user-name")).sendKeys(username);
        }
        if(!password.equals("empty")){
            driver.findElement(By.id("password")).sendKeys(password);
        }
        driver.findElement(By.cssSelector("#login-button")).click();

        Assert.assertEquals(driver.getCurrentUrl(),url);
        if(testType.equals("negative")){
            Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),errorMessage);
        }

    }

//    @Test
//    @Parameters()
//    public void loginToSauceDemo(){
//        driver.findElement(By.id("user-name")).sendKeys("standard_user");
//        driver.findElement(By.id("password")).sendKeys("secret_sauce");
//        driver.findElement(By.cssSelector("#login-button")).click();
//
//        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");
//        Assert.assertEquals(driver.findElement(By.cssSelector(".title")).getText(),"Products");
//    }
//
//    @Test
//    public void loginWithNoUsername(){
//        driver.findElement(By.id("password")).sendKeys("secret_sauce");
//        driver.findElement(By.cssSelector("#login-button")).click();
//
//        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username is required");
//    }
//
//    @Test
//    public void loginWithNoPassword(){
//        driver.findElement(By.id("user-name")).sendKeys("standard_user");
//        driver.findElement(By.cssSelector("#login-button")).click();
//
//        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Password is required");
//    }
//
//    @Test
//    public void loginWithWrongUsername(){
//        driver.findElement(By.id("user-name")).sendKeys("standard_user123");
//        driver.findElement(By.id("password")).sendKeys("secret_sauce");
//        driver.findElement(By.cssSelector("#login-button")).click();
//
//        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
//
//    }
//
//    @Test
//    public void loginWithWrongPassword(){
//        driver.findElement(By.id("user-name")).sendKeys("standard_user");
//        driver.findElement(By.id("password")).sendKeys("secret_sauce98745612");
//        driver.findElement(By.cssSelector("#login-button")).click();
//
//        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username and password do not match any user in this service");
//    }
//
//    @Test
//    public void loginWithoutCredentials(){
//        driver.findElement(By.cssSelector("#login-button")).click();
//
//        Assert.assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Epic sadface: Username is required");
//    }


}
