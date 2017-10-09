package operator.file

import operator.Abstract.GenericOperator
import statement.ClassStatement

/**
 * Created by dfds on 9/27/17.
 */
class ScopeOperation extends GenericOperator{

    GenericOperator build() {
        return new ScopeOperation()
    }

    void updateStatement(ClassStatement classStatement) {
        // Do nothing
    }

    boolean isValid(String line) {
        String statement = line.replaceAll(' ', '')

        return !statement.contains('if(')
    }

}
