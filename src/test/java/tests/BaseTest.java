package tests;

import com.codeborne.selenide.Configuration;
import config.DriverConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentHelper.attachAsText;
import static helpers.AttachmentHelper.attachPageSource;
import static helpers.AttachmentHelper.attachScreenshot;
import static helpers.AttachmentHelper.attachVideo;
import static helpers.AttachmentHelper.getConsoleLogs;

public class BaseTest {

    static DriverConfig driverConfig = ConfigFactory.create(DriverConfig.class);

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        Configuration.browser = System.getProperty("web.browser", "chrome");

        String remoteWebDriver = System.getProperty("remote.web.driver");
        if (remoteWebDriver != null) {
            String user = driverConfig.getRemoteWebUser();
            String password = driverConfig.getRemoteWebPassword();
            Configuration.remote = String.format(remoteWebDriver, user, password);
        }
    }

    @AfterEach
    void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        if (System.getProperty("video.storage") != null) {
            attachVideo();
        }
        closeWebDriver();
    }
}