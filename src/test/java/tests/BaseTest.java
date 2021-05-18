package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentHelper.attachAsText;
import static helpers.AttachmentHelper.attachPageSource;
import static helpers.AttachmentHelper.attachScreenshot;
import static helpers.AttachmentHelper.getConsoleLogs;

public class BaseTest {

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @AfterEach
    void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
    }
}