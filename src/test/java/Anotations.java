import org.testng.annotations.*;

public class Anotations {

    @Test
    public void test1() {
        System.out.println("Prvi test");
    }

    @Test
    public void test2() {
        System.out.println("Drugi test");
    }

    @BeforeClass //Pokreće se jednom pre SVIH testova
    public void beforeClass() {
        System.out.println("Before Class");
    }

    @BeforeMethod //Pokreće se jednom pre SVAKOG testa
    public void beforeMethod() {
        System.out.println("Before Method");
    }

    @AfterMethod //Pokreće se jednom posle SVAKOG tesa
    public void afterMethod() {
        System.out.println("After Method");
    }

    @AfterClass //Pokreće se jednom posle SVIH testova
    public void afterClass() {
        System.out.println("After Class");
    }



}
