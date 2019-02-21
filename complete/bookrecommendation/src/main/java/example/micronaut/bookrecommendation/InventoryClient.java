package example.micronaut.bookrecommendation;

import io.micronaut.configuration.rabbitmq.annotation.Binding;
import io.micronaut.configuration.rabbitmq.annotation.RabbitClient;
import io.micronaut.configuration.rabbitmq.annotation.RabbitProperty;
import io.reactivex.Maybe;

@RabbitClient("micronaut") // <1>
@RabbitProperty(name = "replyTo", value = "amq.rabbitmq.reply-to") // <2>
public interface InventoryClient {

    @Binding("books.inventory") // <3>
    Maybe<Boolean> stock(String isbn); // <4>

}
