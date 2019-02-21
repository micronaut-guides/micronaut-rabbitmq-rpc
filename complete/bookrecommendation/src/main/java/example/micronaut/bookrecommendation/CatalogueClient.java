package example.micronaut.bookrecommendation;

import io.micronaut.configuration.rabbitmq.annotation.Binding;
import io.micronaut.configuration.rabbitmq.annotation.RabbitClient;
import io.micronaut.configuration.rabbitmq.annotation.RabbitProperty;
import io.reactivex.Flowable;

import java.util.List;

@RabbitClient("micronaut")
@RabbitProperty(name = "replyTo", value = "amq.rabbitmq.reply-to")
public interface CatalogueClient {

    @Binding("books.catalogue")
    Flowable<List<Book>> findAll(byte[] data);

}
