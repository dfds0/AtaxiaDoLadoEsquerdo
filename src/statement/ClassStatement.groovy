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
    
    List<ClassStatement> internalClasses
    List<EnumStatement> internalEnumerations

    List<FunctionStatement> constructors
    List<PropertyStatement> properties
    List<FunctionStatement> functions
    List<ClosureStatement> closures
    List<StaticScopeStatement> staticScopes

    boolean isAbstract
    boolean isInternal

    // Shortcuts
    Map<String, PropertyStatement> propertiesAsMap

    public ClassStatement() {
        this.imports = []

        this.extendsClass = null
        this.implementsInterfaces = []
        this.internalClasses = []
        this.internalEnumerations = []

        this.constructors = []
        this.properties = []
        this.functions = []
        this.closures = []
        this.staticScopes = []

        this.propertiesAsMap = [:]
    }

    public PropertyStatement loadPropertyStatement(String name, String type) {
        PropertyStatement propertyStatement = this.propertiesAsMap[name]
        if (propertyStatement == null) {
            propertyStatement = new PropertyStatement()
            propertyStatement.name = name
            propertyStatement.type = type

            this.propertiesAsMap[name] = propertyStatement

            // Link entities
            this.properties.add(propertyStatement)
            propertyStatement.classStatement = this
        }

        return propertyStatement
    }

    /**
     * Return the instance name of class
     * @return String value that represent the instance name of class
     */
    public String getInstanceName() {
        return name[0]?.toLowerCase() + name[1..-1]
    }

    /**
     * Return a property that can work as ID of entity
     * @return PropertyStatement instance case exist a Main Property
     */
    public PropertyStatement getMainProperty() {
        PropertyStatement mainProperty = null

        for (PropertyStatement propertyStatement : properties) {
            if (propertyStatement.name == 'name') {
                mainProperty = propertyStatement
                break

            } else if (propertyStatement.name == 'username') {
                mainProperty = propertyStatement
                break

            } else if (propertyStatement.isUnique && propertyStatement.isStringType) {
                mainProperty = propertyStatement
                break
            }
        }

        return mainProperty
    }

}