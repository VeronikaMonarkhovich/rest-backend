package com.taory.restbackend;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.with;

public class LibraryTests {

    static {
        RestAssured.baseURI = "http://localhost:8080";
    }

    private final RequestSpecification spec =
            with()
                    .baseUri("http://localhost:8080")
                    .basePath("/");

    Faker faker = new Faker(new Locale("ru"));

    @Test
    @DisplayName("Тест на получение всех книг одного автора")
    void getBooksAuthorTest() {
        String response = spec.get("book/getAllAuthor")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        Assertions.assertEquals(response, "[\"Священная книга оборотня\",\"Чапаев и пустота\",\"Омон Ра\"]");
    }

    @Test
    @DisplayName("Тест на получение всех книг")
    void getAllBooksTest() {
        String response = spec.get("book/getAll")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        Assertions.assertEquals(response, "[[\"Изучаем Java\",\"Паттерны проектирования\"]," +
                "[\"Священная книга оборотня\",\"Чапаев и пустота\",\"Омон Ра\"]," +
                "[\"Краткая история времени\",\"Теория всего\"]]");
    }

    @Test
    @DisplayName("Тест на получение всех авторов")
    void getAllAuthorsTest() {
        String response = spec.get("author/getAll")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        Assertions.assertEquals(response, "[\"К.Сьерра\",\"В.Пелевин\",\"C.Хокинг\"]");
    }

    @Test
    @DisplayName("Тест на добавление книги")
    void addBookTest() {
        String bookName = faker.name().title();
        String author = faker.name().firstName();
        spec.queryParam("author", author)
                .queryParam("bookName", bookName)
                .post("book/add")
                .then()
                .statusCode(200);
        String response = spec.get("book/getAll")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        Assertions.assertTrue(response.contains(bookName));
    }

    @Test
    @DisplayName("Негативный сценарий: Отправка запроса без параметров")
    void addBookTestWithoutParam() {
        spec.post("book/add")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Тест на добавление автора")
    void addAuthorTest() {
        String author = faker.name().firstName();
        spec.queryParam("author", author)
                .post("author/add")
                .then()
                .statusCode(200);
        String response = spec.get("author/getAll")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        Assertions.assertTrue(response.contains(author));
    }
    @Test
    @DisplayName("Негативный сценарий: Отправка запроса без параметров")
    void addAuthorTestWithoutParam() {
        spec.post("author/add")
                .then()
                .statusCode(400);
    }
}