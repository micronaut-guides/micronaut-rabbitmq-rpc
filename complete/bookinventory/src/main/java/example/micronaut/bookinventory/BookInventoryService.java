package example.micronaut.bookinventory;

import io.micronaut.configuration.rabbitmq.annotation.Queue;
import io.micronaut.configuration.rabbitmq.annotation.RabbitListener;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RabbitListener
public class BookInventoryService {

    @Queue("inventory")
    public Boolean stock(@NotBlank String isbn) {
        return bookInventoryByIsbn(isbn).map(bi -> bi.getStock() > 0).orElse(null);
    }

    private Optional<BookInventory> bookInventoryByIsbn(String isbn) {
        if (isbn.equals("1491950358")) {
            return Optional.of(new BookInventory(isbn, 4));
        } else if (isbn.equals("1680502395")) {
            return Optional.of(new BookInventory(isbn, 0));
        }
        return Optional.empty();
    }
}
