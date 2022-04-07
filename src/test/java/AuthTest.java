
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test
    public void shouldAuthoriseWithRegisteredUser() {
        var validUser = Registration.getRegisteredUser("active");
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='login'] input").val(validUser.getLogin());
        $("[data-test-id='password'] input").val(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldBe(Condition.visible)
                .shouldHave(text("Личный кабинет"));
    }

    @Test
    public void shouldGiveErrorWithUnregisteredUser() {
        var validUser = Registration.getUser("active");
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='login'] input").val(validUser.getLogin());
        $("[data-test-id='password'] input").val(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible)
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGiveErrorWithBlockedUser() {
        var validUser = Registration.getRegisteredUser("blocked");
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='login'] input").val(validUser.getLogin());
        $("[data-test-id='password'] input").val(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible)
                .shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    public void shouldGiveErrorWithInvalidLogin() {
        var validUser = Registration.getRegisteredUser("active");
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='login'] input").val("invalid.login");
        $("[data-test-id='password'] input").val(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible)
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    public void shouldGiveErrorWithInvalidPassword() {
        var validUser = Registration.getRegisteredUser("active");
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id='login'] input").val(validUser.getLogin());
        $("[data-test-id='password'] input").val("invalid.password");
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

}
