package statement
/**
 * Created by dfds on 9/30/17.
 */
class PropertyStatement {

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
}
