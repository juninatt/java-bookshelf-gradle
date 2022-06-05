package bookstore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookShelf {

    private final List<Book> books = new ArrayList<>();

    public void add(Book... booksToAdd) {
        books.addAll(Arrays.asList(booksToAdd));
    }
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }
    public List<Book> arrangeByTitle() {
        return books.stream().sorted().collect(Collectors.toList());
    }
}
