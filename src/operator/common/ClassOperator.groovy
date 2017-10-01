package operator.common

import operator.Abstract.GenericOperator
import reader.DonutOperator
import statement.ClassStatement
import statement.InterfaceStatement

/**
 * Created by dfds on 9/30/17.
 */
class ClassOperator extends GenericOperator  {

    GenericOperator build() {
        return new ClassOperator()
    }

    void updateStatement(ClassStatement classStatement) {
        if (classStatement.name) {
            // Internal class
        } else {

            List<String> tokens
            String line = loadScopeDeclaration()

            if (line.contains('implements ')) {
                tokens = line.split('implements ')

                tokens[1].split(',').each { String interfaceName ->
                    InterfaceStatement interfaceStatement = new InterfaceStatement()
                    interfaceStatement.name = interfaceName.trim()

                    classStatement.implementsInterfaces.add(interfaceStatement)
                }

                line = tokens[0]
            }

            if (line.contains('extends ')) {
                tokens = line.split('extends ')

                tokens[1].split(',').each { String className ->
                    ClassStatement extendsClass = new ClassStatement()
                    extendsClass.name = className.trim()
                    classStatement.extendsClass = extendsClass
                }

                line = tokens[0]
            }

            ['public ', 'private ', 'protected '].each { String token ->
                if (line.contains(token)) {
                    classStatement.visibility = token
                    line = line.replace(token, '')
                }
            }

            if (line.contains('abstract ')) {
                line = line.replace('abstract ', '').trim()
                classStatement.isAbstract = true
            }

            line = line.replace('class ', '')
            classStatement.name = line

            DonutOperator donutOperator = new DonutOperator()
            donutOperator.classStatement = classStatement
            donutOperator.recap(this.lines)

        }
    }

    boolean isValid(String line) {
        return line.startsWith('class') || line.startsWith('public class') || line.startsWith('private class')
    }


}
