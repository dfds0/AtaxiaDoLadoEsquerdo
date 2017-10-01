package operator.common

import operator.Abstract.GenericOperator
import statement.ArgumentStatement
import statement.ClassStatement
import statement.FunctionStatement

/**
 * Created by dfds on 9/30/17.
 */
class FunctionOperator extends GenericOperator  {

    GenericOperator build() {
        return new FunctionOperator()
    }

    void updateStatement(ClassStatement classStatement) {
        List<String> tokens
        String line = loadScopeDeclaration()

        FunctionStatement functionStatement = new FunctionStatement()

        ['public ', 'private ', 'protected '].each { String token ->
            if (line.contains(token)) {
                functionStatement.visibility = token
                line = line.replace(token, '')
            }
        }

        if (line.contains('abstract ')) {
            line = line.replace('abstract ', '').trim()
            functionStatement.isAbstract = true
        }

        if (line.contains('static ')) {
            line = line.replace('static ', '').trim()
            functionStatement.isStatic = true
        }

        // 'def function(arg)' -> 'def function(arg'
        int lastClose = line.lastIndexOf(')')
        int firstOpen = line.indexOf('(')
        String arguments = line.substring(firstOpen + 1, lastClose)

        // 'def function(arg)' -> 'def function'
        line = line.substring(0, firstOpen)

        ArgumentStatement argumentStatement

        tokens = splitByComma(arguments)
        tokens.each { String argument ->
            argumentStatement = ArgumentStatement.build(argument)
            functionStatement.arguments.add(argumentStatement)
        }

        // TODO - WRONG this will not true every time
        int lastSpace = line.lastIndexOf(' ')
        // 'def function' -> 'function'
        functionStatement.name = line.substring(lastSpace).trim()
        // 'def function' -> 'def'
        functionStatement.type = line.substring(0, lastSpace).trim()
        
        // functionStatement.lines = this.lines -> recall
        classStatement.functions.add(functionStatement)
    }

    boolean isValid(String line) {

        // Clone the line
        String closureLine = new String(line)
        closureLine = closureLine.replaceAll(' ', '')
        int closureIndex = closureLine.indexOf('{')

        if (closureIndex == -1) {
            return false
        }

        char previousChar = closureLine.charAt(closureIndex -1)

        // Closure as: 'name: {...}' - invalid
        // Closure as: 'name = {...}' - invalid
        // Closure as: 'name ={...}' - invalid
        // Closure as: 'name {...}' - invalid
        // Closure as: 'name{...}' - invalid
        // Closure as: 'name, {...}' - invalid
        // Closure as: 'name,{...}' - invalid
        // Closure as: 'name(...){...}' - valid
        // Closure as: 'name(...) {...}' - valid
        if (previousChar != ')') {
            return false
        }

        return true
    }
}
