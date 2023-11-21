package ru.netology.testmode.test;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.test.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.test.DataGenerator.Registration.getUser;
import static ru.netology.testmode.test.DataGenerator.getRandomLogin;
import static ru.netology.testmode.test.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:7777");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        Selenide.$("[data-test-id='login'] input").setValue(registeredUser.getLogin()  );
        Selenide.$("[data-test-id='password'] input").setValue(registeredUser.getPassword()  );
        Selenide.$(".button").click( );
        Selenide.$("[id='root']").shouldHave( Condition.text("Личный кабинет")).shouldBe(Condition.visible  );


    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        Selenide.$("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin()  );
        Selenide.$("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword()  );
        Selenide.$(".button").click( );
        Selenide.$(".notification__content").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));



    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        Selenide.$("[data-test-id='login'] input").setValue(blockedUser.getLogin()  );
        Selenide.$("[data-test-id='password'] input").setValue(blockedUser.getPassword()  );
        Selenide.$(".button").click( );
        Selenide.$("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));


    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        Selenide.$("[data-test-id='login'] input").setValue(wrongLogin );
        Selenide.$("[data-test-id='password'] input").setValue(registeredUser.getPassword()  );
        Selenide.$(".button").click( );
        Selenide.$("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));


    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        var blockedUser = getRegisteredUser("blocked");
        Selenide.$("[data-test-id='login'] input").setValue(registeredUser.getLogin()  );
        Selenide.$("[data-test-id='password'] input").setValue(wrongPassword  );
        Selenide.$(".button").click( );
        Selenide.$("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));


    }
}
