package statement
/**
 * Created by dfds on 9/30/17.
 */
class PropertyStatement {

    ClassStatement classStatement

    String name
    String type
    String visibility
    String defaultValue

    boolean isHasOne = false
    boolean isBelongsTo = false
    boolean isHasMany = false
    boolean isTransient = false
    boolean isStatic = false
    boolean isFinal = false
    boolean isNullable = false
    boolean isUnique = false
    boolean isBlank = false

    String size
    String uniqueReference
    ClosureStatement validator

    public void loadConstraints(String line, String validatorCode) {
        List<String> tokens = line.split(',')
        List<String> subTokens
        String statementName
        String statementValue

        // name unique: true, nullable: false -> ['name unique: true', 'nullable: false']
        tokens.each { String subToken ->
            subTokens = subToken.split(':')
            statementName = subTokens[0].trim()
            statementValue = subTokens[1].trim()

            switch (statementName) {

                case 'nullable':
                    this.isNullable = Boolean.parseBoolean(statementValue)
                    break

                case 'blank':
                    this.isBlank = Boolean.parseBoolean(statementValue)
                    break

                case 'size':
                    this.size = statementValue
                    break

                case 'unique':
                    this.isUnique = true
                    // TODO
                    this.uniqueReference = statementValue
                    break

                case 'validator':
                    this.validator = null
                    // TODO validatorCode
                    break
            }

        }
    }

    /**
     * Check if the type of property is String
     * @return True if the type is String
     */
    public boolean getIsStringType() {
        return this.type == 'String'
    }

    public boolean getIsObjectType() {
        String firstChar = this.type.substring(0, 1)

        // Object don't star with a lower char
        if (firstChar == firstChar.toLowerCase()) {
            return false
        }

        List<String> primitiveObject = ['Long', 'long', 'Integer', 'int', 'Double', 'double', 'Boolean', 'boolean', 'String']
        if (primitiveObject.contains(this.type)) {
            return false
        }

        // Array of object is primitive
        if (this.type.contains('[]')) {
            return false
        }

        return true
    }

    public boolean getIsDateType() {
        return this.type == 'Date'
    }

    public boolean getIsHashMapType() {
        String radical = this.type.split('<')[0]
        return radical == 'Map' || radical == 'HashMap'
    }

    public boolean getIsListType() {
        String radical = this.type.split('<')[0]
        return radical == 'List' || radical == 'ArrayList'
    }

    public boolean getIsSetType() {
        String radical = this.type.split('<')[0]
        return radical == 'Set' || radical == 'HashSet'
    }

    public boolean getIsBooleanType() {
        return this.type == 'boolean' || this.type == 'Boolean'
    }

    public boolean getIsEnumType() {
        return this.classStatement.internalEnumerations.find {it.name == this.type } != null
    }

    public boolean getIsDataSource() {
        return this.name == 'dataSource'
    }

    public boolean getIsGrailsApplication() {
        return this.name == 'grailsApplication'
    }

}
