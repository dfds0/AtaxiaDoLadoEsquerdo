package operator.common

import operator.Abstract.GenericOperator
import statement.ArgumentStatement
import statement.ClassStatement
import statement.ClosureStatement
import statement.StaticScopeStatement

/**
 * Created by dfds on 9/30/17.
 */
class StaticScopeOperator extends GenericOperator {

    GenericOperator build() {
        return new StaticScopeOperator()
    }

    void updateStatement(ClassStatement classStatement) {
        StaticScopeStatement staticScopeStatement = new StaticScopeStatement()
        staticScopeStatement.content = this.lines.join(' ')
        classStatement.staticScopes.add(staticScopeStatement)
    }

    boolean isValid(String line) {
        line = line.trim()
        return line == 'static {' || line == 'static{'
    }
}
