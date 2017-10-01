package operator.common

import operator.Abstract.GenericOperator
import statement.ClassStatement

/**
 * Created by dfds on 9/30/17.
 */
class ImportOperator extends GenericOperator  {

    GenericOperator build() {
        return new ImportOperator()
    }

    void updateStatement(ClassStatement classStatement) {

        String statement
        this.lines.each { String line ->
            // line = "import statement.ClassStatement" - >
            statement = line.split(' ').last()
            classStatement.imports.add(statement)
        }

    }

    boolean isValid(String line) {
        return line.startsWith('import')
    }
}
