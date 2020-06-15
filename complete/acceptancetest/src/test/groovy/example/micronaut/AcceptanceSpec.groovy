package example.micronaut

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientException
import org.codehaus.groovy.runtime.ScriptTestAdapter
import org.junit.Assume
import spock.lang.AutoCleanup
import spock.lang.Requires
import spock.lang.Shared
import spock.lang.Specification

class AcceptanceSpec extends Specification implements MicroserviceHealth {

    static final String BOOK_CATALOGUE_URL = 'http://localhost:8081'
    static final String BOOK_INVENTORY_URL = 'http://localhost:8082'
    static final String BOOK_RECOMMENDATION_URL = 'http://localhost:8080'

    @Shared
    @AutoCleanup
    HttpClient httpClient = HttpClient.create(new URL('http://localhost:8080'))

    BlockingHttpClient getClient() {
        httpClient.toBlocking()
    }

    @Requires( {
        Closure isUp = { client, url ->
            String microservicesUrl = url.endsWith('/health') ? url : "${url}/health"
            try {
                StatusResponse statusResponse = client.retrieve(HttpRequest.GET(microservicesUrl), StatusResponse)
                if ( statusResponse.status == 'UP' ) {
                    return true
                }
            } catch (HttpClientException e) {
                println "HTTP Client exception for $microservicesUrl $e.message"
            }
            return false
        }
        BlockingHttpClient catalogueClient = HttpClient.create(new URL(BOOK_CATALOGUE_URL)).toBlocking()
        BlockingHttpClient inventoryClient = HttpClient.create(new URL(BOOK_INVENTORY_URL)).toBlocking()
        BlockingHttpClient recommendationClient = HttpClient.create(new URL(BOOK_RECOMMENDATION_URL)).toBlocking()
        return isUp(catalogueClient, BOOK_CATALOGUE_URL) &&
                isUp(inventoryClient, BOOK_INVENTORY_URL) &&
                isUp(recommendationClient, BOOK_RECOMMENDATION_URL)
    })
    void 'verifies three microservices collaborate together using RabbitMQ'() {
        when:
        List<BookRecommendation> books = client.retrieve(HttpRequest.GET('/books'), Argument.of(List, BookRecommendation))

        then:
        books != null
        books.size() == 1
        books.first().name == 'Building Microservices'
    }
}
