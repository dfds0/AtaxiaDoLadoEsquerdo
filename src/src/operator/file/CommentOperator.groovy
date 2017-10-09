package operator.file

import operator.Abstract.GenericOperator
import statement.ClassStatement

/**
 * Created by dfds on 9/27/17.
 */
class CommentOperator extends GenericOperator {

    GenericOperator build() {
        return null
    }

    void updateStatement(ClassStatement classStatement) {
        // Do nothing
    }

    boolean isValid(String line) {
        return line.startsWith('//') || line.startsWith('*') || line.startsWith('*/') || line.startsWith('/*')
    }

    boolean needBuffer(String line) {
        return false
    }

}
