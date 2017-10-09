package operator.domain

import operator.Abstract.GenericOperator
import operator.common.PropertyOperator
import statement.ClassStatement
import statement.PropertyStatement

/**
 * Created by dfds on 9/30/17.
 */
class BelongsToOperator extends PropertyOperator {

    GenericOperator build() {
        return new BelongsToOperator()
    }

    void updateStatement(ClassStatement classStatement) {
        String context = this.lines.join('')

        // static belongsTo = [a: ClassA, b: ClassB] -> static belongsTo = a: ClassA, b: ClassB
        context = context.replace('[', '').replace(']', '')

        // static belongsTo = a: ClassA, b: ClassB -> ['static belongsTo =', 'a: ClassA, b: ClassB']
        context = context.split('=').last()

        PropertyStatement propertyStatement
        String propertyName
        String propertyType

        // 'a: ClassA, b: ClassB' -> ['a: ClassA', 'b: ClassB']
        context.split(',').each { String statement ->

            if (statement.contains(':')) {
                propertyName = statement.split(':').first().trim()
                propertyType = statement.split(':').last().trim()
            } else {
                // constraint = [ 'property' ] - is Valid
                propertyName = statement.replaceAll('\'', '').trim()
                propertyType = null
            }

            propertyStatement = classStatement.loadPropertyStatement(propertyName, propertyType)
            propertyStatement.isBelongsTo = true

        }

    }

    boolean isValid(String line) {
        return line.startsWith('static belongsTo =') || line.startsWith('static belongsTo=')
    }
}
