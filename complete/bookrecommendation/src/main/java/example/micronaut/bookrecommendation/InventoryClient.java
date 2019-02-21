package example.micronaut.bookrecommendation;

import io.micronaut.configuration.rabbitmq.annotation.Binding;
import io.micronaut.configuration.rabbitmq.annotation.RabbitClient;
import io.micronaut.configuration.rabbitmq.annotation.RabbitProperty;
import io.reactivex.Maybe;

@RabbitClient("micronaut")
@RabbitProperty(name = "replyTo", value = "amq.rabbitmq.reply-to")
public interface InventoryClient {

    @Binding("books.inventory")
    Maybe<Boolean> stock(String isbn);

}
