package operator.domain

import operator.Abstract.GenericOperator
import operator.common.ClosureOperator
import statement.ClassStatement

/**
 * Created by dfds on 9/30/17.
 */
class MappingOperator extends ClosureOperator {

    GenericOperator build() {
        return new MappingOperator()
    }

    void updateStatement(ClassStatement classStatement) {
        // For now, do nothing..
    }

    boolean isValid(String line) {
        return line.startsWith('static mapping =') || line.startsWith('static mapping=')
    }
}
