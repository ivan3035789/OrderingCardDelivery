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
import java.util.concurrent.ThreadLocalRandom;


public class OrderingCardDeliveryTest {
    public static String generator() {
        LocalDate startDate = LocalDate.now().plusDays(3);
        long start = startDate.getDayOfMonth();
        LocalDate endDate = LocalDate.now().plusYears(1);
        long end = endDate.lengthOfYear();
        long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
        return startDate.plusDays(randomEpochDay).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void theOrderOfCardMustBeSuccessfulDeliveryOfTheCardIsNotEarlierThanThreeDaysFromTheCurrentDate() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(generator());
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

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(generator());
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

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(generator());
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

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(generator());
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
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(generator());
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
        $("[data-test-id=city] input").setValue("Чехов");
        $("[data-test-id=date] input").doubleClick().append(generator());
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void itShouldDisplayRedWarningLabelWhenEnteringDateEarlierThanThreeDaysFromTheCurrentDate() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Заказ на выбранную дату невозможен"));
        // подправить завтра
    }
    @Test
    void itShouldDisplayRedWarningLabelWhenEnteringPhoneNumberWithoutThPlusSymbolInTheFirstPlace() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(generator());
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void itShouldDisplayRedWarningLabelWhenEnteringPhoneNumberWithNumberDigitsGreaterThanEleven() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(generator());
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+792509876541");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void itShouldDisplayRedWarningLabelWhenEnteringAnInvalidCharacter() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(generator());
        $("[data-test-id=name] input").setValue("Иванов_Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void itShouldDisplayRedWarningLabelWhenTheConsentCheckMarkIsNotSet() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().append(generator());
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79250987654");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid")
                .shouldBe(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
