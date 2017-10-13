package operator.Abstract

import statement.ClassStatement

/**
 * RBS as great daddy
 */
abstract class GenericOperator {

    public static String join = ' ##NEW LINE## '

    public List<String> lines = []

    abstract GenericOperator build()

    abstract void updateStatement(ClassStatement classStatement)

    abstract boolean isValid(String line)

    boolean needBuffer(String line) {
        // line.count('<') != (line.count('>') - line.count('->'))
        boolean closureTest = line.count('{') != line.count('}')
        boolean functionTest = line.count('(') != line.count(')')
        boolean arrayTest = line.count('[') != line.count(']')
        boolean mapTest = false // line.count('<') != (line.count('>') - line.count('->'))

        return closureTest || functionTest || arrayTest || mapTest
    }

    public String loadScopeDeclaration() {
        String line = ''
        String lastLine
        int linesToRemove = 0

        for (String bufferLine in this.lines) {

            line += ' ' + bufferLine
            linesToRemove++

            // name() { == true
            // name( arg1 = new Obj(), arg2 = new Obje() ) { == true
            // Declaration as: 'name() {' - valid
            // Declaration as: 'name( arg1, arg2 ) {' - valid
            // Declaration as: 'name( {}, {} ) {' - valid
            // Declaration as: 'name() { return 1 }' - valid

            if (bufferLine.count('(') == bufferLine.count(')') && bufferLine.count('{') == (bufferLine.count('}') + 1)) {
                break
            }

        }

        for (int number = 0; number < linesToRemove; number ++) {
            this.lines.remove(number)
        }

        // Remove the last '}'
        if (!this.lines.empty) {
            lastLine = this.lines.last().trim()
            this.lines.remove(this.lines.size() -1)
            if (lastLine != '}') {
                // The last line is not only '}' so
                int index = lastLine.lastIndexOf('}')
                if (index == lastLine.size() -1) {
                    lastLine = lastLine.substring(0, index -1)
                    this.lines.add(lastLine)
                } else {
                    // Error the last char of a scope need be }
                    println '----->'
                }
            }

        }

        // Preserve the closure declaration
        List<String> tokens = line.split('\\{', 2)
        if (!tokens.last().trim().empty) {
            this.lines.add(0, tokens.last().trim())
            line = tokens[0].trim()
        } else {
            line = line.replace('{', '')
        }

        return line.trim()
    }

    public static List<String> splitByComma(String line) {

        List<String> statements = []
        List<String> tokens = line.split(',')

        String statement = ''
        tokens.each { String token ->

            statement += token
            if (statement.count('<') == statement.count('>')) {
                statements.add(statement)
                statement = ''
            }

        }

        if (statements.size() == 1 && statements.first().empty) {
            return []
        }

        return statements
    }

    public List<String> loadStatements() {

        List<String> statements = []
        String statement = ''

        this.lines.each { String line ->

            statement += join + line

            boolean functionTest = statement.count('(') == statement.count(')')
            boolean scopeTest = statement.count('{') == statement.count('}')
            boolean mapTest = statement.count('<') == (statement.count('>') - statement.count('->'))
            boolean arrayTest = statement.count('[') == statement.count(']')

            // TODO test if has some statement after '}'
            if (functionTest && scopeTest && mapTest && arrayTest) {
                statements.add(statement)
                statement = ''
            }
        }

        return statements
    }
}
