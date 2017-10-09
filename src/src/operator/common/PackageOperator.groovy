package operator.common

import operator.Abstract.GenericOperator
import statement.ClassStatement

/**
 * Created by dfds on 9/30/17.
 */
class PackageOperator extends GenericOperator  {

    GenericOperator build() {
        return new PackageOperator()
    }

    void updateStatement(ClassStatement classStatement) {
        String line = this.lines.first()

        // line = "package operator.common" -> operator.common
        classStatement.packageName = line.split(' ').last()
    }

    boolean isValid(String line) {
        return line.startsWith('package')
    }
}
