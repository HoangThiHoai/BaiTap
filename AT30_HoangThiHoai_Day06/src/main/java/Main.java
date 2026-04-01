import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    static void main(String[] args) {
        ChromeDriver driver = new ChromeDriver();
        try{
            driver.get("https://serenity-bdd.github.io/");
            Thread.sleep(5000);

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            driver.quit();
        }
    }
}
