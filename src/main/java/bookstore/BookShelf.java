package bookstore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookShelf {

    private final List<String> books = new ArrayList<>();

    public void add(String... booksToAdd) {
        books.addAll(Arrays.asList(booksToAdd));
    }
    public List<String> getBooks() {
        return books;
    }
}
