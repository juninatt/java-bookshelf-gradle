package bookstore;

import java.util.ArrayList;
import java.util.List;

public class BookShelf {

    private final List<String> books = new ArrayList<>();

    public void add(String book) {
        books.add(book);
    }
    public List<String> getBooks() {
        return books;
    }
}
