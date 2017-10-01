package operator.domain

import operator.Abstract.GenericOperator
import operator.common.ClosureOperator
import statement.ClassStatement
import statement.PropertyStatement

/**
 * Created by dfds on 9/30/17.
 */
class ConstraintsOperator extends ClosureOperator {

    GenericOperator build() {
        return new ConstraintsOperator()
    }

    void updateStatement(ClassStatement classStatement) {
        List<String> tokens
        PropertyStatement propertyStatement
        String statement

        // Removed first and last line of scope
        loadScopeDeclaration()

        List<String> rules = loadStatements()
        String scopeCode

        rules.each { String line ->

            // name(unique: true, nullable: false) -> name unique: true, nullable: false
            if (line.endsWith(')')) {
                // -1 or -2 ?
                line = line.substring(0, line.length() - 1)
                line = line.replace('(', ' ')
            }

            // '##NEW LINE## name validator: { ... }' -> 'name validator: { ... }'
            line = line.replace(join, '').trim()

            // 'name validator: { ... }' -> ['name', 'validator: { ... }']
            tokens = line.split(' ', 2)
            statement = tokens[0].trim()
            line = line.replace(statement, '').trim()

            // 'name validator: { ... }' -> 'name validator:{ ... }'
            line = line.replace(': {', ':{')

            int firstScopeOpen = line.indexOf(':{')
            if (firstScopeOpen != -1) {
                scopeCode = line.substring(firstScopeOpen+1)
                line = line.substring(0, firstScopeOpen+1) + '##SCOPE##'
            } else {
                scopeCode = null
            }

            // name() is a valid rule constraint, in this case, we will ignore..
            if (!line.empty) {
                propertyStatement = classStatement.loadPropertyStatement(statement, null)
                propertyStatement.loadConstraints(line, scopeCode)
            }

        }

    }

    boolean isValid(String line) {
        return line.startsWith('static constraints =') || line.startsWith('static constraints=')
    }
}
