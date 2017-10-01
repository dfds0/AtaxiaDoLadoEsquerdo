package reader

import operator.Abstract.GenericOperator
import operator.common.AnnotationOperator
import operator.file.CommentOperator
import operator.file.DeclarationOperator
import operator.file.ScopeOperation

class GroovyReader {

    private List<GenericOperator> operators = []

    GenericOperator lastOperator

    GroovyReader() {
        operators.add(new AnnotationOperator())
        operators.add(new CommentOperator())
        operators.add(new DeclarationOperator())
        operators.add(new ScopeOperation())
    }

    public void read(File file) {

        List<GenericOperator> operators = []
        GenericOperator operator

        String line
        file.eachLine { String dirtyLine ->
            line = dirtyLine.trim()

            if (!line.empty) {
                operator = findOperator(line)

                if (operator && !operator.lines.empty) {
                    operators.add(operator)
                }

            }

        }

        DonutOperator donutOperator = new DonutOperator()
        donutOperator.operators = operators
        donutOperator.recap()

        println ''
        println donutOperator.classStatement.name

        donutOperator.classStatement.imports.each {
            println '   import: ' + it
        }

        if (donutOperator.classStatement.extendsClass) {
            println '   extend: ' + donutOperator.classStatement.extendsClass.name
        }

        donutOperator.classStatement.implementsInterfaces.each {
            println '   implements: ' + it.name
        }

        donutOperator.classStatement.internalEnum.each {
            println '   enum: ' + it.name
        }

        donutOperator.classStatement.properties.each {
            println '   ' + it.type + ' ' + it.name + (it.isBelongsTo ? ' (BelongsTo) ' : '') +
                    (it.isHasMany ? ' (HasMany) ' : '') + (it.isHasOne ? ' (HasOne) ' : '') + (it.isTransient ? ' (Transient) ' : '')
        }

        donutOperator.classStatement.functions.each {
            println '   -F: ' + it.type + ' ' + it.name + (it.isStatic ? ' (static) ' : '')
            it.arguments.each { arg ->
                println '       -- ' + arg.name
            }
        }

        donutOperator.classStatement.closures.each {
            if (it.isAnonymous) {
                println '   -C: anonymous closure ' + (it.isStatic ? ' (static) ' : '')
            } else {
                println '   -C: ' + it.type + ' ' + it.name
            }
        }

        println ''
    }

    public GenericOperator findOperator(String line) {

        GenericOperator operatorFound = null
        String context

        if (lastOperator) {
            lastOperator.lines.add(line)

            context = lastOperator.lines.join(GenericOperator.join)

            if (!lastOperator.needBuffer(context)) {
                operatorFound = lastOperator
                lastOperator = null
            }

        } else {

            for (GenericOperator operator in operators) {
                if (operator.isValid(line)) {

                    if (operator.needBuffer(line)) {
                        lastOperator = operator.build()
                        lastOperator.lines.add(line)
                        operatorFound = null

                    } else {
                        operatorFound = operator.build()
                        if (operatorFound) {
                            operatorFound.lines.add(line)
                        }
                        lastOperator = null
                    }
                    break

                }
            }

        }

        return operatorFound
    }

}
