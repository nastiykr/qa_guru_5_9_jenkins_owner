package tests;

import org.junit.jupiter.api.Test;
import pages.RegistrationPage;

public class StudentRegistrationFormTests extends BaseTest {
    RegistrationPage registrationPage = new RegistrationPage();

    @Test
    void successfulFillFormPage() {
        registrationPage
                .openPage()
                .fillForm()
                .checkData();
    }
}