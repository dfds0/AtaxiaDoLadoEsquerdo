package operator.common

import operator.Abstract.GenericOperator
import statement.ClassStatement
import statement.EnumStatement

/**
 * Created by dfds on 9/30/17.
 */
class EnumOperator extends GenericOperator  {

    GenericOperator build() {
        return new EnumOperator()
    }

    void updateStatement(ClassStatement classStatement) {
        String line = loadScopeDeclaration()

        line = line.replace('public ', '').replace('private ', '').replace('enum ', '')
        line = line.trim()

        EnumStatement enumStatement = new EnumStatement()
        enumStatement.name = line.split(' ').first()

        // TODO read the scope of Enum
        classStatement.internalEnum.add(enumStatement)
    }

    boolean isValid(String line) {
        return line.startsWith('enum') || line.startsWith('public enum') || line.startsWith('private enum')
    }
}
