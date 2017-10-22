package writer

import statement.ClassStatement
import statement.PropertyStatement
import writer.common.AbstractWriter

/**
 * Created by dfds on 3/25/17.
 */
class FactoryWriter extends AbstractWriter {

    public String writeBuildFunction(ClassStatement classStatement) {
        riotBuilder()

        append '/**'
        append "* Build a new instance of class ${classStatement.name} using the basic properties"
        append '* @param dependencies - Define initial value of some properties'
        append "* @return New instance of class ${classStatement.name}"
        append '*/'
        append "public ${classStatement.name.capitalize()} build${classStatement.name.capitalize()}(Map dependencies = [:]) {"
        append "    ${classStatement.name} ${classStatement.instanceName} = new ${classStatement.name}()"

        classStatement.properties.each {

            if (it.isBlank || it.isNullable || it.isTransient || it.isStatic || it.isBelongsTo || it.isHasOne || it.isHasMany) {
                // Do nothing

            } else {
                append "    ${classStatement.instanceName}.${it.name} = ${getInitialValue(it)}"
            }

        }

        if (!classStatement.propertiesBelongsTo.empty || !classStatement.propertiesHasOne.empty) {
            append ''
            append '    // Define the map of dependencies to be propagate'
            append "    Map propagation = ['${classStatement.name}': ${classStatement.instanceName}]"
        }

        if (!classStatement.propertiesBelongsTo.empty) {
            append ''
            append '    // Load dependencies (belongsTo)'
            classStatement.propertiesBelongsTo.each {
                append "    ${classStatement.instanceName}.${it.name} = dependencies['${it.type}'] ?: build${it.type}(propagation)"
            }
        }

        if (!classStatement.propertiesHasOne.empty) {
            append ''
            append '    // Load dependencies (hasOne)'
            classStatement.propertiesHasOne.each {
                // instanceName.propertyHasOne = buildClassOfPropertyHasOne( ['ClassOfInstanceName' : instanceName])
                append "    ${classStatement.instanceName}.${it.name} = build${it.type}(propagation)"
            }
        }

        if (!classStatement.propertiesHasMany.empty) {
            append ''
            append '    // Load dependencies (hasMany)'
            classStatement.propertiesHasMany.each {
                // it.name = 'abcdefgH' -> it.name[0..-2] -> 'abcdefg'
                append "    ${it.type} ${it.name[0..-2]} = build${it.type}()"
                append "    ${classStatement.instanceName}.addTo${it.type}s(${it.name})"
                append ''
            }
        }

        append ''
        append "    return ${classStatement.instanceName}"
        append '}'

        return builder.toString()
    }

    public String writeCreateFunction(ClassStatement statement) {
        riotBuilder()

        append "public ${statement.name.capitalize()} create${statement.name.capitalize()}(String label = SUFFIX) {"
        append "    ${statement.name} ${statement.instanceName} = build${statement.name.capitalize()}()"
        append "    ${statement.instanceName}.save(flush: true)"

        PropertyStatement mainProperty = statement.mainProperty
        if (mainProperty) {
            append ''
            append '   if (label != SUFFIX) {'
            append "        ${statement.instanceName}.${mainProperty.name} += label"
            append '    }'

        } else {
            append '   // Don`t exist a main property to append the label'
        }

        append ''
        append "    return ${statement.instanceName}"
        append '}'

        return builder.toString()
    }


    public static String getInitialValue(PropertyStatement propertyStatement) {
        String value = '// TODO - Define an initial value'
        String prefix

        if (propertyStatement.name.length() > 3) {
            prefix = propertyStatement.name.substring(0, 3)

        } else {
            prefix = propertyStatement.name
        }


        if (propertyStatement.isStringType) {
            value = "'${prefix}-${new Date().time}'"
            // value = (propertyStatement.constraintSize && value.length() > sizeValueMax) ? value.substring(0, sizeValueMax) : value
            // blank, null, email.. don't need be done now
        }

        if (propertyStatement.isDateType) {
            return 'new Date()'
        }

        if (propertyStatement.isHashMapType) {
            return '[:]'
        }

        if (propertyStatement.isListType || propertyStatement.isSetType) {
            return '[]'
        }

        if (propertyStatement.isEnumType) {
            return "${propertyStatement.type}.values().first()"
        }

        if (propertyStatement.isBooleanType) {
            return 'false'
        }

        if (propertyStatement.isObjectType) {
            value = "build${propertyStatement.type}()"
        }

        return value
    }

}
