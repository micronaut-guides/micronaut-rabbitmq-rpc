package example.micronaut

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class AcceptanceSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationContext applicationContext = ApplicationContext.run()

    @Shared
    @AutoCleanup
    RxHttpClient client = applicationContext.createBean(RxHttpClient, 'http://localhost:8080')

    void 'verifies three microservices collaborate together using RabbitMQ'() {
        when:
        List<BookRecommendation> books = client.toBlocking().retrieve(HttpRequest.GET('/books'), Argument.of(List, BookRecommendation))

        then:
        books != null
        books.size() == 1
        books.first().name == 'Building Microservices'
    }
}
