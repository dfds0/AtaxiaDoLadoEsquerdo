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

        if (line.contains('default ')) {
            line = line.replace('default ', '').trim()
            functionStatement.isDefault = true
        }

        if (line.contains('throws')) {
            tokens = line.split('throws ')
            line = tokens[0]
            tokens[1].split(',').each {String exception
                functionStatement.exceptions.add(exception.trim())
            }
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
            argumentStatement = ArgumentStatement.build(argument.trim())
            functionStatement.arguments.add(argumentStatement)
        }

        int lastSpace = line.lastIndexOf(' ')
        if (lastSpace == -1) {
            // function without return type defined
            functionStatement.name = line.trim()
        } else {
            // 'def function' -> 'function'
            functionStatement.name = line.substring(lastSpace).trim()
            // 'def function' -> 'def'
            functionStatement.type = line.substring(0, lastSpace).trim()
        }

        // functionStatement.lines = this.lines -> recall

        if (functionStatement.name == classStatement.name) {
            classStatement.constructors.add(functionStatement)

        } else {
            classStatement.functions.add(functionStatement)
        }

    }

    boolean isValid(String line) {

        // Clone the line
        String closureLine = new String(line)
        closureLine = closureLine.replaceAll(' ', '')
        int closureIndex = closureLine.indexOf('{')

        if (closureIndex == -1) {
            return false
        }

        // Closure as: 'name: {...}' - invalid
        // Closure as: 'name = {...}' - invalid
        // Closure as: 'name ={...}' - invalid
        // Closure as: 'name {...}' - invalid
        // Closure as: 'name{...}' - invalid
        // Closure as: 'name, {...}' - invalid
        // Closure as: 'name,{...}' - invalid
        // Closure as: 'name(...){...}' - valid
        // Closure as: 'name(...) {...}' - valid
        // Closure as: 'void name ...' - valid
        // Closure as: 'name ... throws {' - valid

        if ((closureLine.indexOf('(') > closureLine.indexOf('{'))) {
            return false
        }

        return true
    }
}
