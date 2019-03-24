package example.micronaut

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import org.junit.Assume
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class AcceptanceSpec extends Specification implements MicroserviceHealth {

    @Shared
    @AutoCleanup
    HttpClient httpClient = HttpClient.create(new URL('http://localhost:8080'))

    BlockingHttpClient getClient() {
        httpClient.toBlocking()
    }

    void 'verifies three microservices collaborate together using RabbitMQ'() {
        given:
        given:
        [
                'http://localhost:8080',
                'http://localhost:8081',
                'http://localhost:8082',
        ].each { String url ->
            if (!isUp(url)) {
                println "$url is not up"
            }
            Assume.assumeTrue(isUp(url))
        }

        when:
        List<BookRecommendation> books = client.retrieve(HttpRequest.GET('/books'), Argument.of(List, BookRecommendation))

        then:
        books != null
        books.size() == 1
        books.first().name == 'Building Microservices'
    }
}
