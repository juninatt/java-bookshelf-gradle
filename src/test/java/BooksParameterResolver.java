import bookstore.Book;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class BooksParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext,
                                     final ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return Objects.equals(parameter.getParameterizedType().
                getTypeName(), "java.util.Map<java.lang.String, bookstore.Book>");
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext) throws ParameterResolutionException {
        Map<String, Book> books = new HashMap<>();
        books.put("Spring in Action", new Book("Spring in Action, 6th Edition", "Craig Walls", LocalDate.of(2022, Month.JANUARY, 1)));
        books.put("Effective Java", new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8)));
        books.put("Half a War", new Book("Half a War", "Joe Abercrombie", LocalDate.of(2016, Month.MARCH, 8)));
        books.put("Spring Security in Action", new Book("Spring Security in Action", "Laurentiu Spilca", LocalDate.of(2020, Month.JANUARY, 1)));
        books.put("Spring Boot in Action", new Book("Spring Boot in Action", "Craig Walls", LocalDate.of(2016, Month.JANUARY, 1)));
        return books;
    }
}