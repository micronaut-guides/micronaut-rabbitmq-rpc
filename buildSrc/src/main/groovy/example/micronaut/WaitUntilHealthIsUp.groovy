package example.micronaut

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

@CompileStatic
class WaitUntilHealthIsUp extends DefaultTask {

    public static final String UP_STATUS = 'UP'
    public static final String STATUS_KEY = 'status'

    @Input
    String url

    @Input
    String path = "/health"

    @Input
    Integer delay = 500

    @Input
    Integer attempts = 30

    @TaskAction
    def waitUntilUp() {

        boolean success = false
        int count = 0
        while (count < attempts && !success) {
            logger.quiet("Trying to connect to ${url}: Attempt number=$count")
            try {
                count += 1
                JsonSlurper jsonSlurper = new JsonSlurper()
                String responceBody = "${url}${path}".toURL().text
                Object result = jsonSlurper.parseText(responceBody)
                Map jsonResult = (Map) result
                String status = (String) jsonResult.get(STATUS_KEY)
                success = (status == UP_STATUS)
            } catch (all) {
                sleep(delay)
            }
        }
        if (success) {
            logger.quiet("SUCCEFULLY Connected to ${url}")
        } else {
            throw new GradleException("FAILED to Connected to ${url}")
        }
    }
}
