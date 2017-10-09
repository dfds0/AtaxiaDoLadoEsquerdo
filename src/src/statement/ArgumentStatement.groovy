package statement

/**
 * Created by dfds on 9/30/17.
 */
class ArgumentStatement {

    String name
    String type
    String visibility
    String defaultValue
    boolean isFinal

    public static ArgumentStatement build(String line) {
        ArgumentStatement argumentStatement = new ArgumentStatement()
        List<String> tokens

        if (line.contains('final ')) {
            argumentStatement.isFinal = true
            line = line.replace('final ', '')
        }

        if (line.contains('=')) {
            tokens = line.split('=')
            line = tokens[0].trim()

            argumentStatement.defaultValue = tokens[1].trim()
        }

        if (line.contains(' ')) {
            tokens = line.split(' ')
            line = tokens[1].trim()

            argumentStatement.type = tokens[0].trim()
        }

        argumentStatement.name = line

        return argumentStatement
    }
}
