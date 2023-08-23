package utilities;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	private static WebDriver driver;
	public final static int TIMEOUT = 2;

	public DriverManager(String userType) {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		if (userType.equalsIgnoreCase("Authenticated")) {
			options.addArguments(
					"user-data-dir=" + System.getProperty("user.dir") + "/AppData/Local/Google/Chrome/User Data/");
			options.addArguments("--profile-directory=Profile 2");
		}
		driver = new ChromeDriver(options);
		new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
		driver.manage().window().maximize();
	}

	public static void openPage(String url) {
		driver.get(url);
	}

	public static String getTitle() {
		return driver.getTitle();
	}

	public static void NavBack() {
		driver.navigate().back();
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void tearDown() {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
	}

	public static void executeJavascript(String script) {
		try {
			((JavascriptExecutor) driver).executeAsyncScript(script);
		} catch (org.openqa.selenium.ScriptTimeoutException e) {
		}

	}

}