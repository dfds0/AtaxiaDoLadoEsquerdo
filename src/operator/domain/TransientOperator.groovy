package operator.domain

import operator.Abstract.GenericOperator
import operator.common.PropertyOperator
import statement.ClassStatement
import statement.PropertyStatement

/**
 * Created by dfds on 9/30/17.
 */
class TransientOperator extends PropertyOperator {

    GenericOperator build() {
        return new TransientOperator()
    }

    void updateStatement(ClassStatement classStatement) {
        String context = this.lines.join('')

        // static transients = [a: ClassA, b: ClassB] -> static transients = a: ClassA, b: ClassB
        context = context.replace('[', '').replace(']', '')

        // static transients = a: ClassA, b: ClassB -> ['static transients =', 'a: ClassA, b: ClassB']
        context = context.split('=').last()

        PropertyStatement originalProperty
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

            propertyStatement = classStatement.propertiesAsMap[propertyName]

            if (propertyStatement == null) {
                propertyStatement = new PropertyStatement()
                propertyStatement.name = propertyName
                propertyStatement.type = propertyType
            }

            propertyStatement.isTransient = true
        }

        originalProperty = classStatement.propertiesAsMap[propertyStatement.name]
        if (originalProperty) {
            originalProperty.isTransient = propertyStatement.isTransient

        } else {
            // Link entities
            classStatement.properties.add(propertyStatement)
            propertyStatement.classStatement = classStatement

            classStatement.propertiesAsMap.put(propertyStatement.name, propertyStatement)
        }

    }

    boolean isValid(String line) {
        return line.startsWith('static transients =') || line.startsWith('static transients=')
    }
}
