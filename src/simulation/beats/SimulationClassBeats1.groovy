package simulation.beats

import simulation.SimulationAnnotation

/**
 * Created by dfds on 10/12/17.
 */
class SimulationClassBeats1 {


    public functionArgumentWithDefaultValueAsClosure(argument = { property -> return property }) {
        return argument('value')
    }

    // Function in multiple lines

    transient
    final
    static
    private
    String functionMultipleLine() {
        return 'value'
    }

    static private
    functionMultipleLines() {
        return 'value'
    }

    public functionMultipleArguments(String argument1, String argument2,
                                     String argument3, String argument4,
                                     String argument5) {
        return 'value'
    }


    // Function with Annotation
    @Override
    public String toString() {
        return 'value'
    }

    @SimulationAnnotation(state = SimulationAnnotation.State.HIGH, name = 'name')
    public String functionWithAnotation() {
        return 'value'
    }

    // Function with return omitted
    int hashCode() {
        123
    }

    // Function with exception

    public void functionWithException() throws Exception {
        this.propertyDef = 'value'
    }

    public void functionWithExceptions() throws Exception,
            NullPointerException {
        this.propertyDef = 'value'
    }

//    static public
//    def functionComplex(String argument1, Object argument2 = new Object(),
//                        int agument3 = 1, argument4 = true,
//                        argumetn5 = { property ->
//                            return property
//                        }) throws Exception, NullPointerException,
//            IndexOutOfBoundsException {
//
//        'return this last line'
//    }

}
