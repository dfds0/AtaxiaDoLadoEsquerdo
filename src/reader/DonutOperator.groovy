package reader

import operator.Abstract.GenericOperator
import operator.common.AnnotationOperator
import operator.common.ClosureOperator
import operator.common.FunctionOperator
import operator.common.ImportOperator
import operator.common.PackageOperator
import operator.common.PropertyOperator
import operator.domain.HasOneOperator
import operator.domain.TransientOperator
import operator.file.CommentOperator
import operator.file.DeclarationOperator
import operator.domain.BelongsToOperator
import operator.common.ClassOperator
import operator.domain.ConstraintsOperator
import operator.common.EnumOperator
import operator.domain.HasManyOperator
import operator.domain.MappingOperator
import statement.ClassStatement

/**
 * Stand of Donut Operator
 */
class DonutOperator {

    List<GenericOperator> propertyOperations
    List<GenericOperator> scopeOperations
    List<GenericOperator> operators

    GenericOperator lastOperator

    ClassStatement classStatement

    public DonutOperator() {
        this.operators = []

        this.propertyOperations = []
        this.propertyOperations.add(new AnnotationOperator())
        this.propertyOperations.add(new ImportOperator())
        this.propertyOperations.add(new PackageOperator())
        this.propertyOperations.add(new HasOneOperator())
        this.propertyOperations.add(new BelongsToOperator())
        this.propertyOperations.add(new HasManyOperator())
        this.propertyOperations.add(new TransientOperator())
        // The most 'generic' operator is the last
        this.propertyOperations.add(new PropertyOperator())

        this.scopeOperations = []
        this.scopeOperations.add(new AnnotationOperator())
        this.scopeOperations.add(new CommentOperator())
        this.scopeOperations.add(new ClassOperator())
        this.scopeOperations.add(new EnumOperator())
        this.scopeOperations.add(new ConstraintsOperator())
        this.scopeOperations.add(new MappingOperator())
        // The most 'generic' operator is the last
        this.scopeOperations.add(new ClosureOperator())
        this.scopeOperations.add(new FunctionOperator())

        this.classStatement = new ClassStatement()
    }

    public void recap(List<String> lines) {
        GroovyReader groovyReader = new GroovyReader()
        GenericOperator genericOperator

        this.operators = []

        lines.each { String line ->

            line = line.trim()
            if (!line.empty) {
                genericOperator = groovyReader.findOperator(line)

                if (genericOperator && !genericOperator.lines.empty) {
                    operators.add(genericOperator)
                }

            }
        }

        recap()
    }

    public void recap() {

        GenericOperator genericOperator
        List<GenericOperator> operations

        this.operators.each { currentOperator ->

            if (currentOperator instanceof DeclarationOperator) {
                operations = this.propertyOperations
            } else {
                operations = this.scopeOperations
            }

            currentOperator.lines.each { String line ->
                genericOperator = findOperator(line, operations)

                if (genericOperator) {
                    genericOperator.updateStatement(classStatement)
                }
            }

        }
    }

    public GenericOperator findOperator(String line, List<GenericOperator> operations) {

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

            for (GenericOperator operator in operations) {
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
