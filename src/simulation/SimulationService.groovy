package simulation

/**
 * Created by dfds on 10/12/17.
 */
@SimulationAnnotation('as transactional')
class SimulationService {

    SimulationService serviceInjected
    def instanceInjected

    private static final org.apache.commons.logging.Log LOGGER = org.apache.commons.logging.LogFactory.getLog(this)

    void functionVoid() {
        // nothing
    }

    String functionWithReturn() {
        return 'value'
    }

    public functionWithArgument(String argument) {
        return argument
    }


}
