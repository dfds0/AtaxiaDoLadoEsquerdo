package writer

import statement.ClassStatement

/**
 * Created by dfds on 10/8/17.
 */
class PrintClassStatement {

    public static void print(ClassStatement classStatement) {

        println ''
        println classStatement.name

        classStatement.imports.each {
            println '   import: ' + it
        }

        if (classStatement.extendsClass) {
            println '   extend: ' + classStatement.extendsClass.name
        }

        classStatement.implementsInterfaces.each {
            println '   implements: ' + it.name
        }

        classStatement.internalEnumerations.each {
            println '   enum: ' + it.name
        }

        classStatement.properties.each {
            println '   ' + it.type + ' ' + it.name + (it.isBelongsTo ? ' (BelongsTo) ' : '') +
                    (it.isHasMany ? ' (HasMany) ' : '') + (it.isHasOne ? ' (HasOne) ' : '') + (it.isTransient ? ' (Transient) ' : '')
        }

        classStatement.functions.each {
            println '   -F: ' + it.type + ' ' + it.name + (it.isStatic ? ' (static) ' : '')
            it.arguments.each { arg ->
                println '       -- ' + arg.name + ' = ' + arg.defaultValue
            }
        }

        classStatement.closures.each {
            if (it.isAnonymous) {
                println '   -C: anonymous closure ' + (it.isStatic ? ' (static) ' : '')
            } else {
                println '   -C: ' + it.type + ' ' + it.name
            }
        }

        println ''

    }

}
