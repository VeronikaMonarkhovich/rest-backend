package com.taory.restbackend.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class LibraryController {

    private Map<String, List<String>> books = new HashMap<>();

    {
        books.put("В.Пелевин", new ArrayList<>() {
            {
                add("Священная книга оборотня");
                add("Чапаев и пустота");
                add("Омон Ра");
            }
        });

        books.put("C.Хокинг", new ArrayList<>() {
            {
                add("Краткая история времени");
                add("Теория всего");
            }
        });
        books.put("К.Сьерра", new ArrayList<>() {
            {
                add("Изучаем Java");
                add("Паттерны проектирования");
            }
        });
    }

    @PostMapping("author/add")
    @ApiOperation("Добавление автора")
    public void addAuthor(@RequestParam String author) {
        books.put(author, new ArrayList<>() {
        });
    }

    @PostMapping("book/add")
    @ApiOperation("Добавление книги")
    public void addBook(@RequestParam String author,@RequestParam String bookName) {
        books.computeIfAbsent(author, k -> new ArrayList<>()).add(bookName);
    }


    @GetMapping("book/getAllAuthor")
    @ApiOperation("Получение всех книг одного автора")
    public List<String> getBooksAuthor() {
        return books.get("В.Пелевин");
    }

    @GetMapping("book/getAll")
    @ApiOperation("Получение всех книг")
    public List<List<String>> getBooks() {
        return books.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @GetMapping("author/getAll")
    @ApiOperation("Получение всех авторов")
    public List<String> getAuthors() {

        return books.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}