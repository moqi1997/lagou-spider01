import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class LagouSearcher {
    public static void main(String[] args) {
        //设置 wevdriver 路径
        System.setProperty("webdriver.chrome.driver",LagouSearcher.class.getClassLoader().getResource("chromedriver.exe").getPath());
        //创建 webdriver
        WebDriver chromeDriver = new ChromeDriver();
        //跳转页面
        chromeDriver.get("https://www.lagou.com/zhaopin/Java/?labelWords=label");

        clickOption(chromeDriver, "工作经验", "不限");
        clickOption(chromeDriver, "学历要求", "不限");
        clickOption(chromeDriver, "融资阶段", "不限");
        clickOption(chromeDriver, "公司规模", "不限");
        clickOption(chromeDriver, "行业领域", "移动互联网");

//        clickOption(chromeDriver, "工作经验", "应届毕业生");
//        clickOption(chromeDriver, "学历要求", "本科");
//        clickOption(chromeDriver, "融资阶段", "天使轮");
//        clickOption(chromeDriver, "公司规模", "15-50人");
//        clickOption(chromeDriver, "行业领域", "移动互联网");

        //解析页面元素
        extractJob(chromeDriver);
    }

    private static void extractJob(WebDriver chromeDriver) {
        List<WebElement> jobElements = chromeDriver.findElements(By.className("con_list_item"));
        for (WebElement jobElement : jobElements) {
            WebElement moneyElement = jobElement.findElement(By.className("position")).findElement(By.className("money"));
            // System.out.println(moneyElement.getText());
            String companyName = jobElement.findElement(By.className("company_name")).getText();
            System.out.println(companyName + ":" + moneyElement.getText());
        }
        WebElement pagerNext = chromeDriver.findElement(By.className("pager_next"));
        if (!pagerNext.getAttribute("class").contains("pager_next_disabled")) {
            pagerNext.click();
            System.out.println("解析下一页！！！！");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            extractJob(chromeDriver);
        }
    }

    private static void clickOption(WebDriver chromeDriver, String choseTitle, String optionTitle) {
        WebElement chosenelement = chromeDriver.findElement(By.xpath("//li[@class='multi-chosen']//span[contains(text(), '" + choseTitle + "')]"));
        WebElement optionelement = chosenelement.findElement(By.xpath("../a[contains(text(),'" + optionTitle + "')]"));
        optionelement.click();
    }
}
