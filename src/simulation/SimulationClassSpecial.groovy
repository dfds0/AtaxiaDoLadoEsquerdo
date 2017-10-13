package simulation
/**
 * Created by dfds on 10/12/17.
 */
abstract class SimulationClassSpecial {

    private static final String QUERY = "select " +
            "table.column_a, " +
            "table.column_b, " +
            "from table " +
            "where table.column_a <> null"

    private static final transient def my_property = 'value'

    static {
        1 + 1
        2 + 2
    }

    private groovyArray = [ 'value',  'value', 'value' ]
    private javaArray = new String[3]

    private groovyMap = [ keyA: 1, keyB: 2 ]
    private HashMap<String, Integer> javaMap = new HashMap<String, Integer>()

    private groovyMapComplex = [ keyA: [subKey: 'valueA'], keyB: 'valueB' ]
    private groovyMapComplexLine = [
            keyA: [
                    subKey: 'value'
            ],
            keyB: 'valueB'
    ]

    private groovyMapComplexWithScope = [ keyA: [subKey: 'valueA'], keyB: { value -> return value } ]
    private groovyMapComplexWithScopeLine = [
            keyA: [
                    subKey: 'valueA'
            ],
            keyB: { value ->
                return value
            }
    ]

    private groovyArrayComplex = [ 'value', ['another value'], [] ]
    private groovyArrayComplexLine = [ 'value',
            ['another value'],
            [],
            [
                    keyA: [
                            subKey: 'value'
                    ],
                    keyB: 'valueB'
            ]
    ]

    // Property with dot curve
    String property = 'value';

    // Function abstract
    abstract void functionAbstract();
}
