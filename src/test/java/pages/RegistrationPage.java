package pages;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegistrationPage {
    Faker faker = new Faker();

    private String firstName = faker.name().firstName();
    private String lastName = faker.name().lastName();
    private String gender = faker.demographic().sex();
    private String email = faker.internet().emailAddress();
    private String phone = faker.number().digits(10);
    private Date dateOfBirth = faker.date().birthday();
    private String day = new SimpleDateFormat("dd").format(dateOfBirth);
    private String month = new SimpleDateFormat("MMMM", Locale.ENGLISH).format(dateOfBirth);
    private String year = new SimpleDateFormat("yyyy").format(dateOfBirth);
    private String subject = "Social Studies";
    private String hobby = "Music";
    private String photoName = "photo.jpeg";
    private String address = faker.address().fullAddress();
    private String state = "Haryana";
    private String city = "Karnal";

    @Step("Open page")
    public RegistrationPage openPage() {
        open("https://demoqa.com/automation-practice-form");
        $(".main-header").shouldHave(text("Practice Form"));
        return this;
    }

    @Step("Fill form")
    public RegistrationPage fillForm() {
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $(byText(gender)).click();
        $("#userNumber").setValue(phone);

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(month);
        $(".react-datepicker__year-select").selectOption(year);
        $(".react-datepicker__day.react-datepicker__day--0" + day).click();   //or $(String.format("[aria-label='Choose Friday, %s %sth, %s']", "May", "11", "1990")).click();

        $("#subjectsInput").setValue("a");
        $(byText(subject)).click();

        $(byText(hobby)).click();

        $("#uploadPicture").uploadFromClasspath(photoName);   //$("#uploadPicture").uploadFile(new File("src/test/resources/photo.jpeg"));

        $("#currentAddress").setValue(address);

        $("#state").scrollTo().click();
        $(byText(state)).click();

        $("#city").click();
        $(byText(city)).click();

        $("#submit").click();
        return this;
    }

    @Step("Check data")
    public RegistrationPage checkData() {
        $(".table-responsive").shouldHave(
                text("Student Name " + firstName + " " + lastName),
                text("Student Email " + email),
                text("Gender " + gender),
                text("Mobile " + phone),
                text("Date of Birth " + day + " " + month + "," + year),
                text("Subjects " + subject),
                text("Hobbies " + hobby),
                text("Picture " + photoName),
                text("Address " + address),
                text("State and City " + state + " " + city)
        );
        return this;
    }
}
