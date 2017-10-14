package simulation

import javax.servlet.http.HttpServletResponse

/**
 * Created by dfds on 10/12/17.
 */

@SimulationAnnotation(state = SimulationAnnotation.State.HIGH)
// Comment
@SimulationAnnotation(['Value'])
class SimulationController extends SimulationClass implements Serializable {

    SimulationService simulationService

    SimulationClass iAmNotAServiceButIWasInjected

    private static final org.apache.commons.logging.Log LOGGER = org.apache.commons.logging.LogFactory.getLog(this)

    static allowedMethods = [iAmAAction: 'GET']

    def functionAsAction() {
        Object object = new Object()

        render(contentType: 'json', status: HttpServletResponse.SC_OK, text: object)
    }

    def closureAsAction = {
        redirect(url: 'new url')
    }

    @SimulationAnnotation("lock")
    def closureAsActionWithAnnotation = {
        redirect url: 'new url'
    }

    def functionAsActionOfFlash() {
        flash.message = 'mesage'
    }


    def functionNotAction() {
        return 'value'
    }

    def functionNotActionWithException() {
        throw new Exception('message')
    }

    def renderJsonAs(Object object, String api) {
        JSON.use(api) {
            render(contentType: 'json', status: 200, text: object as Object)
        }
    }

    def renderError(int status, String message) {
        respond message, formats: ['json'], status: status
    }


}
