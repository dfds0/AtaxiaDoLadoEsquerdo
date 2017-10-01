package operator.common

import operator.Abstract.GenericOperator
import statement.ArgumentStatement
import statement.ClassStatement
import statement.ClosureStatement

/**
 * Created by dfds on 9/30/17.
 */
class ClosureOperator extends GenericOperator {

    GenericOperator build() {
        return new ClosureOperator()
    }

    void updateStatement(ClassStatement classStatement) {
        List<String> tokens
        String line = loadScopeDeclaration()

        ClosureStatement closureStatement = new ClosureStatement()

        ['public ', 'private ', 'protected '].each { String token ->
            if (line.contains(token)) {
                closureStatement.visibility = token
                line = line.replace(token, '')
            }
        }

        if (line.contains('abstract ')) {
            line = line.replace('abstract ', '').trim()
            closureStatement.isAbstract = true
        }

        if (line.contains('static ') || line == 'static') {
            line = line.replace('static ', '').trim()
            closureStatement.isStatic = true
        }

        // 'static {' will check by the second validation
        // 'static { ... }' is a valid scope
        if (line == 'static') {
            line = null
            closureStatement.isStatic = true
        }

        String arguments = this.lines[0]
        this.lines.remove(0)
        // Expected: 'Object a, Object b ->'
        arguments = arguments.replace('->', '')

        ArgumentStatement argumentStatement

        println loadStatements()
        tokens = splitByComma(arguments)
        tokens.each { String argument ->
            argumentStatement = ArgumentStatement.build(argument)
            closureStatement.arguments.add(argumentStatement)
        }

        if (line) {
            // 'Closure closure = ' - > 'Closure closure'
            line = line.replace('=', '').trim()

            int lastSpace = line.lastIndexOf(' ')
            // 'def function' -> 'function'
            closureStatement.name = line.substring(lastSpace).trim()
            // 'def function' -> 'def'
            closureStatement.type = line.substring(0, lastSpace).trim()


        } else {
            closureStatement.isAnonymous = true
        }

        // functionStatement.lines = this.lines -> recall
        classStatement.closures.add(closureStatement)
    }

    boolean isValid(String line) {

        // Clone the line
        String closureLine = new String(line)
        closureLine = closureLine.replaceAll(' ', '')
        int closureIndex = closureLine.indexOf('{')

        if (closureIndex == -1) {
            return false
        }

        char previousChar = closureLine.charAt(closureIndex - 1)

        // Closure as: 'name: {...}' - valid
        // Closure as: 'name = {...}' - valid
        // Closure as: 'name ={...}' - valid
        // Closure as: 'name {...}' - valid
        // Closure as: 'name{...}' - valid
        // Closure as: 'name, {...}' - valid
        // Closure as: 'name,{...}' - valid
        // Closure as: 'name(...){...}' - invalid
        // Closure as: 'name(...) {...}' - invalid
        if (previousChar == ')') {
            return false
        }

        return true
    }
}
