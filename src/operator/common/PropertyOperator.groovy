package operator.common

import operator.Abstract.GenericOperator
import statement.ClassStatement
import statement.PropertyStatement

/**
 * Created by dfds on 9/30/17.
 */
class PropertyOperator extends GenericOperator {

    GenericOperator build() {
        return new PropertyOperator()
    }

    void updateStatement(ClassStatement classStatement) {

        PropertyStatement propertyStatement = new PropertyStatement()
        PropertyStatement originalProperty
        List<String> tokens
        String line = this.lines.first()

        ['public ', 'private ', 'protected '].each { String token ->
            if (line.contains(token)) {
                propertyStatement.visibility = token
                line = line.replace(token, '')
            }
        }

        if (line.contains('static ')) {
            line = line.replace('static ', '').trim()
            propertyStatement.isStatic = true
        }

        if (line.contains('final ')) {
            line = line.replace('final ', '').trim()
            propertyStatement.isFinal = true
        }

        if (line.contains('=')) {
            // TODO need work with Map<Object, Object>
            tokens = line.split('=')
            line = tokens[0]
            propertyStatement.defaultValue = tokens[1]
        }

        tokens = line.trim().split(' ')
        propertyStatement.type = tokens[0]
        propertyStatement.name = tokens[1]

        originalProperty = classStatement.propertiesAsMap[propertyStatement.name]
        if (originalProperty) {
            originalProperty.type = propertyStatement.type
            originalProperty.visibility = propertyStatement.visibility
            originalProperty.defaultValue = propertyStatement.defaultValue
            originalProperty.isStatic = propertyStatement.isStatic
            originalProperty.isFinal = propertyStatement.isFinal

        } else {
            classStatement.properties.add(propertyStatement)
            classStatement.propertiesAsMap.put(propertyStatement.name, propertyStatement)
        }


    }

    boolean isValid(String line) {
        // TODO
        return true
    }
}
