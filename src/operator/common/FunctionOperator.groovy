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

        String[] result = loadScopeDeclaration()
        String line = result[0]

        FunctionStatement functionStatement = new FunctionStatement()

        ['public ', 'private ', 'protected '].each { String token ->
            if (line.contains(token)) {
                functionStatement.visibility = token.trim()
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

        if (line.contains('final ')) {
            line = line.replace('final ', '').trim()
            functionStatement.isFinal = true
        }

        if (line.contains('transient ')) {
            line = line.replace('transient ', '').trim()
            // DO nothing
        }

        if (result[2] != null && result[2].contains('throws')) {
            tokens = result[2].split('throws ')
            tokens[1].split(',').each { String exception ->
                functionStatement.exceptions.add(exception.replace('{', '').trim())
            }
        }

        String arguments = result[1]
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

        String compressedLine = LoadOperator.compressLine(line)

        if (!compressedLine.contains('(')) {
            return false
        }

        compressedLine = compressedLine.split('\\(', 2)[0]
        if (compressedLine.contains('new ')) {
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

        return true
    }
}
