package reader

import operator.Abstract.GenericOperator
import operator.common.AnnotationOperator
import operator.common.LoadOperator
import operator.file.CommentOperator
import operator.file.DeclarationOperator
import operator.file.ScopeOperation
import statement.ClassStatement

class GroovyReader {

    List<ClassStatement> classStatements = []
    private List<GenericOperator> operators = []

    GenericOperator lastOperator

    GroovyReader() {
        operators.add(new AnnotationOperator())
        operators.add(new CommentOperator())
        operators.add(new LoadOperator())
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

        this.classStatements.add(donutOperator.classStatement)
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

            println 'Current line : ' + line
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
