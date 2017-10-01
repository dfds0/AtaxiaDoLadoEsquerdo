package statement

/**
 * Created by dfds on 9/30/17.
 */
class ClassStatement {

    List<String> imports
    String packageName

    String visibility
    String name

    ClassStatement extendsClass
    List<InterfaceStatement> implementsInterfaces
    
    List<ClassStatement> internalClass
    List<EnumStatement> internalEnum

    List<FunctionStatement> constructors
    List<PropertyStatement> properties
    List<FunctionStatement> functions
    List<ClosureStatement> closures

    boolean isAbstract
    boolean isInternal

    // Shortcuts
    Map<String, PropertyStatement> propertiesAsMap

    public ClassStatement() {
        this.imports = []

        this.extendsClass = null
        this.implementsInterfaces = []
        this.internalClass = []
        this.internalEnum = []

        this.constructors = []
        this.properties = []
        this.functions = []
        this.closures = []

        this.propertiesAsMap = [:]
    }

    public PropertyStatement loadPropertyStatement(String name, String type) {
        PropertyStatement propertyStatement = this.propertiesAsMap[name]
        if (propertyStatement == null) {
            propertyStatement = new PropertyStatement()
            propertyStatement.name = name
            propertyStatement.type = type

            this.propertiesAsMap[name] = propertyStatement
            this.properties.add(propertyStatement)
        }

        return propertyStatement
    }
}
