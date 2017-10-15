package operator.common

import operator.Abstract.GenericOperator
import statement.ClassStatement
import statement.EnumStatement

/**
 * Created by dfds on 9/30/17.
 */
class LoadOperator extends GenericOperator  {

    GenericOperator build() {
        return new LoadOperator()
    }

    void reload() {

        List<String> newLines = []
        String currentLine
        String bufferLine = ''

        for (int index = 0; index < this.lines.size(); index++) {

            currentLine = bufferLine + ' ' + this.lines[index]

            if (compressLine(currentLine).empty) {
                bufferLine = currentLine
            } else {
                bufferLine = ''
                newLines.add(currentLine)
            }

        }

        this.lines = newLines
    }

    boolean needBuffer(String line) {
        // line.count('<') != (line.count('>') - line.count('->'))
        boolean closureTest = line.count('{') != line.count('}')
        boolean functionTest = line.count('(') != line.count(')')
        boolean arrayTest = line.count('[') != line.count(']')
        boolean mapTest = false // line.count('<') != (line.count('>') - line.count('->'))

        return closureTest || functionTest || arrayTest || mapTest || compressLine(line).empty
    }

    void updateStatement(ClassStatement classStatement) {
        String line = loadScopeDeclaration()[0]

        line = line.replace('public ', '').replace('private ', '').replace('enum ', '')
        line = line.trim()

        EnumStatement enumStatement = new EnumStatement()
        enumStatement.name = line.split(' ').first()

        // TODO read the scope of Enum
        classStatement.internalEnumerations.add(enumStatement)
    }

    boolean isValid(String line) {
        return compressLine(line).empty
    }

    public static String compressLine(String line) {
        String copy = new String(line)
        ['public', 'private', 'protected', 'static', 'transient', 'final', '##NEW LINE##'].each { String token ->
            copy = copy.replaceAll(token, '')
        }

        return copy.trim()
    }


}
