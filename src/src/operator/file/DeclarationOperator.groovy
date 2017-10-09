package operator.file

import operator.Abstract.GenericOperator
import statement.ClassStatement

/**
 * Created by dfds on 9/27/17.
 */
class DeclarationOperator extends GenericOperator {

    GenericOperator build() {
        return new DeclarationOperator()
    }

    void updateStatement(ClassStatement classStatement) {
        // Do nothing
    }

    boolean isValid(String line) {

        int equalsIndex = line.indexOf('=')
        int closureIndex = line.indexOf('{')
        int functionIndex = line.indexOf('(')

        if (equalsIndex == -1 && closureIndex == -1 && functionIndex == -1) {
            return true
        }

        if (equalsIndex == -1 && (closureIndex != -1 || functionIndex != -1)) {
            return false
        }

        if (closureIndex != -1 && line.contains('={') || line.contains('= {')) {
            return false
        }

        boolean closureTest = closureIndex != -1 ? (equalsIndex < closureIndex) : true
        boolean functionTest = functionIndex != -1 ? (equalsIndex < functionIndex) : true

        return closureTest && functionTest
    }

}
