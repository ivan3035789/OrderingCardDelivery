package ru.netology.web;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.sun.tools.javac.util.Constants.format;
import static java.time.LocalDate.now;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class OrderingCardDeliveryTest {
    private final int day = 3;
    private final int month = 1;
    private final int year = 1;


    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void theOrderOfCardMustBeSuccessfulDeliveryOfTheCardIsNotEarlierThanThreeDaysFromTheCurrentDate() {
        String data = LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(data);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").$(withText("Успешно!"))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").$(withText("Встреча успешно забронирована"))
                .shouldHave(visible, Duration.ofSeconds(15));
    }

    @Test
    void theCardOrderMustBeSuccessfulWithDoubleSurname() {
        String data = LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(data);
        $("[data-test-id=name] input").setValue("Иванов-Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").$(withText("Успешно!"))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").$(withText("Встреча успешно забронирована"))
                .shouldHave(visible, Duration.ofSeconds(15));
    }

    @Test
    void TheOrderOnTheCardMustBeSuccessfullyCompletedDeliveryInThirtyDays() {
        String data = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(data);
        $("[data-test-id=name] input").setValue("Иванов-Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").$(withText("Успешно!"))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").$(withText("Встреча успешно забронирована"))
                .shouldHave(visible, Duration.ofSeconds(15));
    }

    @Test
    void TheOrderOnTheCardMustBeSuccessfullyDeliveredInMonth() {
        String data = LocalDate.now().plusMonths(month).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(data);
        $("[data-test-id=name] input").setValue("Иванов-Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").$(withText("Успешно!"))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").$(withText("Встреча успешно забронирована"))
                .shouldHave(visible, Duration.ofSeconds(15));
    }

    @Test
    void TheOrderOnTheCardMustBeSuccessfullyDeliveredInYear() {
        String data = LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(data);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").$(withText("Успешно!"))
                .shouldHave(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").$(withText("Встреча успешно забронирована"))
                .shouldHave(visible, Duration.ofSeconds(15));
    }

    @Test
    void itShouldDisplayRedWarninglabelWhenEnteringNonAdministrativeCity() {
        String data = LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        $("[data-test-id=city] input").setValue("Чехов");
        $("[data-test-id=date] input").doubleClick().append(data);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void itShouldDisplayRedWarningLabelWhenEnteringDateEarlierThanThreeDaysFromTheCurrentDate() {
        String data = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(data);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Заказ на выбранную дату невозможен"));
    }
    @Test
    void itShouldDisplayRedWarningLabelWhenEnteringPhoneNumberWithoutThPlusSymbolInTheFirstPlace() {
        String data = LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(data);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void itShouldDisplayRedWarningLabelWhenEnteringPhoneNumberWithNumberDigitsGreaterThanEleven() {
        String data = LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(data);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+792509876541");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void itShouldDisplayRedWarningLabelWhenEnteringAnInvalidCharacter() {
        String data = LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(data);
        $("[data-test-id=name] input").setValue("Иванов_Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void itShouldDisplayRedWarningLabelWhenTheConsentCheckMarkIsNotSet() {
        String data = LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(data);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid")
                .shouldBe(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
