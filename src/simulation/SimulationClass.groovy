package simulation
/**
 * Created by dfds on 10/12/17.
 */
class SimulationClass {

//    def propertyDef
//    String propertyString
//
//    public publicNotTypeDef
//    private privateNotTypeDef
//
//    public String publicString
//    private String privateString
//
//    // Constructor
//
//    public SimulationClass() {
//        this.propertyDef = 'value'
//    }
//
//    private SimulationClass(String argument) {
//        this.propertyDef = 'value'
//    }
//
//    // Functions
//
//    public void publicVoidFunction() {
//        this.propertyDef = 'value'
//    }
//
//    private void privateVoidFunction() {
//        this.propertyDef = 'value'
//    }
//
//    // Functions as Set/Get
//
//    public void setFakeProperty(String value) {
//        this.privateString = value
//    }
//
//    public String getFakeProperty() {
//        return this.privateString
//    }
//
//    // Functions with term omitted
//
//    public functionWithoutReturn() {
//        return this.propertyDef
//    }
//
//    def functionWithoutScope() {
//        return this.propertyDef
//    }
//
//    // Function static
//
//    static functionStatic() {
//        return 'value'
//    }
//
//    // Function with multiple arguments
//
//    public functionMultipleArguments(String argument1, String argument2) {
//        return 'value'
//    }
//
//    // Function with arguments type omitted
//
//    public functionMultipleArgumentsOmitted(argument1, argument2) {
//        return 'value'
//    }
//
//    // Function with arguments using default value
//
//    public functionArgumentWithDefaultValue(String argument1, String argument2 = 'value') {
//        return 'value'
//    }
//
//    // Function with arguments using default value as object
//
//    public functionArgumentWithDefaultValueAsObject(argument = new Object()) {
//        return 'value'
//    }

    // Function with arguments using default value as closure

    public functionArgumentWithDefaultValueAsClosure(argument = { property -> return property }) {
        return argument('value')
    }

    // Function in multiple lines

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

    // Function complex

    static public
    def functionComplex(String argument1, Object argument2 = new Object(),
                        int agument3 = 1, argument4 = true,
                        argumetn5 = { property ->
                            return property
                        }) throws Exception, NullPointerException,
                            IndexOutOfBoundsException {

        'return this last line'
    }

}
