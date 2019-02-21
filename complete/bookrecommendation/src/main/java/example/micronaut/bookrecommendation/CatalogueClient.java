package example.micronaut.bookrecommendation;

import io.micronaut.configuration.rabbitmq.annotation.Binding;
import io.micronaut.configuration.rabbitmq.annotation.RabbitClient;
import io.micronaut.configuration.rabbitmq.annotation.RabbitProperty;
import io.reactivex.Flowable;

import java.util.List;

@RabbitClient("micronaut") // <1>
@RabbitProperty(name = "replyTo", value = "amq.rabbitmq.reply-to") // <2>
public interface CatalogueClient {

    @Binding("books.catalogue") // <3>
    Flowable<List<Book>> findAll(byte[] data); // <4>

}
