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

        String trimLine = line.trim()
        boolean endWithDot = trimLine.endsWith('.')
        boolean endWithPlus = trimLine.endsWith('+')
        boolean endWithComma = trimLine.endsWith(',')

        return closureTest || functionTest || arrayTest || mapTest || endWithComma || endWithPlus || endWithDot
    }

    public String[] loadScopeDeclaration() {
        String line = ''
        String lastLine
        int linesToRemove = 0

        String[] result = new String[3]
        boolean match = false

        for (String bufferLine in this.lines) {

            line += ' ' + bufferLine
            line = line.trim()
            line = line.replaceAll('->', '~~')
            linesToRemove++

            int indexOfFirstOpenFunction = -1
            int indexOfLastOpenFunction = -1

            char[] charArray = line.trim().toCharArray()

            Map<String, Integer> tokens = [:]
            // Tokens of functions
            tokens.put('(', 0)
            tokens.put(')', 0)
            // Tokens of scope
            tokens.put('{', 0)
            tokens.put('}', 0)
            // Tokens of array
            tokens.put('[', 0)
            tokens.put(']', 0)
            // Tokens of map
            tokens.put('<', 0)
            tokens.put('>', 0)

            String lastChar = ''
            String currentChar
            int counter

            for (int index = 0; index < charArray.length; index++) {

                currentChar = charArray[index].toString()

                if (tokens.containsKey(currentChar)) {

                    counter = tokens.get(currentChar)
                    counter++
                    tokens.put(currentChar, counter)

                    // Check if the token '>' was used as '->'
                    if (currentChar == '>' && lastChar == '-') {
                        counter = tokens.get('>')
                        counter--
                        tokens.put('>', counter)
                    }

                    // Search by the point where the arguments start
                    if (currentChar == '(' && indexOfFirstOpenFunction == -1) {
                        indexOfFirstOpenFunction = index
                    }

                    // Search by the point where the arguments end
                    if (currentChar == ')') {
                        indexOfLastOpenFunction = index
                    }
                }

                // Abstract function do not use "{}"
                boolean testOfScope
                if (line.contains('abstract ') && indexOfFirstOpenFunction != -1 && indexOfFirstOpenFunction != -1 &&
                        !line.contains('{') && !line.contains('}')) {

                    testOfScope = true
                } else {
                    testOfScope = (tokens.get('{') == (tokens.get('}')+1))
                }

                if (tokens.get('(') == tokens.get(')') && testOfScope &&
                        tokens.get('[') == tokens.get(']') && tokens.get('<') == tokens.get('>')) {
                    match = true
                    break
                }

                lastChar = currentChar
            }

            if (match) {
                if (indexOfFirstOpenFunction != -1) {
                    line = line.replaceAll('~~', '->')
                    result[0] = line.substring(0, indexOfFirstOpenFunction)
                    result[1] = line.substring(indexOfFirstOpenFunction+1, indexOfLastOpenFunction)
                    result[2] = line.substring(indexOfLastOpenFunction+1).trim()
                } else {
                    if (line.contains('{')) {
                        result[0] = line.split("\\{")[0]
                    } else {
                        result[0] = line
                    }
                }

                break
            }
        }

        this.lines = this.lines.subList(linesToRemove, this.lines.size())

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

        return result
    }

    public static List<String> splitByComma(String line) {

        List<String> statements = []
        List<String> tokens = line.replaceAll('->', '##SET##').split(',')

        String statement = ''
        tokens.each { String token ->

            statement += token
            if (statement.count('<') == statement.count('>') &&
                    statement.count('(') == statement.count(')') &&
                    statement.count('[') == statement.count(']') &&
                    statement.count('{') == statement.count('}')) {

                statements.add(statement.replace('##SET##', '->'))
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
