package operator.common

import operator.Abstract.GenericOperator
import statement.ClassStatement

/**
 * Created by dfds on 9/30/17.
 */
class AnnotationOperator extends GenericOperator  {

    GenericOperator build() {
        return new AnnotationOperator()
    }

    void updateStatement(ClassStatement classStatement) {
        // Do nothing
    }

    boolean isValid(String line) {
        return line.startsWith('@')
    }
}
