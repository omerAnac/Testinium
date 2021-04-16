import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Test {

    private static Logger log = Logger.getLogger(Test.class.toString());
    public  WebDriver chromeDriver;
    public Actions action;

    @Before //driver kurulumu
    public void setupingDriver()  {


        System.setProperty("webdriver.chrome.driver","Drivers/chromedriver.exe");
        chromeDriver=new ChromeDriver();
        chromeDriver.get("https://www.gittigidiyor.com/");
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        log.info("-----Page was opened:");

    }

    @org.junit.Test
    public  void testing() throws InterruptedException {
        enterence("omeranac12@gmail.com","18021995");
        Thread.sleep(2000);
        searching("bilgisayar");
        Thread.sleep(2000);
        String price1= secendPage(); //2.sayfaya gider ve stirng olarak price geri dönderir
        Thread.sleep(2000);
        String price2= clickBasket(); // Sepetteki fiyatı string olarak geri dönderir
        Thread.sleep(2000);
        priceComp(price1,price2);
        Thread.sleep(2000);
        twoProduct();
        Thread.sleep(2000);
        deleteProducts();
        Thread.sleep(2000);
        quit();

    }

    // giriş2e tıklanma ve email & pasword girişi
    public void enterence(String email,String psw) throws InterruptedException {

        action = new Actions(chromeDriver);
        var buttonSgn= (chromeDriver.findElement(By.className("gekhq4-6")));
        action.moveToElement(buttonSgn).build().perform(); //mosue curse ,giriş yap buttonu üstünde durur

        Thread.sleep(2000);

        var buttonSgnIn= chromeDriver.findElement(new By.ByCssSelector(".qjixn8-0[data-cy='header-login-button']"));
        buttonSgnIn.click();//Sign in yapar

        //String email="omeranac12@gmail.com";
        //String psw="18021995";

        chromeDriver.findElement(By.id("L-UserNameField")).sendKeys(email);
        Thread.sleep(2000);
        chromeDriver.findElement(By.id("L-PasswordField")).sendKeys(psw);
        Thread.sleep(2000);
        chromeDriver.findElement(By.id("gg-login-enter")).click(); //email & pasword yazdıktan sonra giriş yapar

    }

    //girilen kelimeyi araması için
    public void searching(String srchWord){

        chromeDriver.findElement(By.className("sc-4995aq-0")).sendKeys(srchWord);//girilen kelimeyi arama kısmına yazar

        var buttonFind=chromeDriver.findElement(new By.ByCssSelector(".qjixn8-0[data-cy='search-find-button']"));
        buttonFind.click();//girelen kelimeyi "bul" buttonuna basarak arar

    }
    //2.sayfaya gider ve rastgele ürün seçip sepete ekler
    public  String secendPage() throws InterruptedException{
        var secondPage=chromeDriver.findElement(By.xpath("//div[@class='pager pt30 hidden-m gg-d-24']/ul/li[2]/a"));


        JavascriptExecutor jse = (JavascriptExecutor)chromeDriver; //element click intercepted hatası verdi onun için kullanıldı link:http://makeseleniumeasy.com/2020/05/25/elementclickinterceptedexception-element-click-intercepted-not-clickable-at-point-other-element-would-receive-the-click/
        jse.executeScript("arguments[0].click()", secondPage);//2.sayfaya gider
        log.info("-----Second page was opened");
        Thread.sleep(3000);
        var randomProduct= chromeDriver.findElement(By.xpath("/html/body/div[5]/div[2]/div/div[2]/div[3]/div[2]/ul/li[2]/a/div/p"));
        randomProduct.click();//rasgele seçilen ürün tıklandı
        Thread.sleep(3000);
        var addBasket=chromeDriver.findElement(By.xpath("//button[@id='add-to-basket']"));

        jse.executeScript("arguments[0].click()", addBasket);//ürün sepete eklendi(element click intercepted hatası verdi)
        Thread.sleep(3000);
        var productPrice1=chromeDriver.findElement(By.xpath("//div/div[@id='sp-price-container']/div[2]")).getText();//ürün sayfasındaki fiyat
        System.out.println("price on the product page");
        System.out.println(productPrice1);


        return  productPrice1;

    }

    //sepet buttonuna basar
    public String clickBasket(){
        //chromeDriver.get("https://www.gittigidiyor.com/sepetim");
        var basketButton=chromeDriver.findElement(By.xpath("//div/div[4]/div[3]/a[@class='dIB']"));
        basketButton.click(); //sepete tıklar
        chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        var productPrice2=chromeDriver.findElement(By.xpath("//*[@class='real-discounted-price']")).getText();//ürün sayfasındaki fiyat
        System.out.println("Product price in the basket");
        System.out.println(productPrice2);
        return  productPrice2;
    }
    //fiyat karşılaştırması
    public  void priceComp(String price1,String price2){
        if (price1.equals(price2)) System.out.println("Prices equal");
        else System.out.println("Prices not equal");
    }
    //sepet ürün adetini 2 yapma
    public  void twoProduct(){
        var listBox=chromeDriver.findElement(By.xpath("//div[4]/div/div[2]/select"));
        new Select(listBox).selectByVisibleText("2");
        log.info("-----Product in the basket has been made '2'");
    }
    // sepettiki adedi "2" olan ürünü silme
    public  void  deleteProducts(){
        var deleteProduct=chromeDriver.findElement(By.xpath("//div[1]/div[3]/div/div[2]/div/a[1]/i"));
        chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        deleteProduct.click();
        log.info("-----Product in the basket the was deleted");
    }

    public void  quit(){
        chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        chromeDriver.quit();
        log.info("-----Chrome was closed");
    }



}
