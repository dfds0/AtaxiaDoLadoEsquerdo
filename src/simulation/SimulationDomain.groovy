package simulation

/**
 * Created by dfds on 10/12/17.
 */
class SimulationDomain extends SimulationClass implements Serializable {

    private static final long serialVersionUID = 1

    Object propertyWithScopeOmitted

    public String propertyPublic

    private String propertyPrivate

    static mapping = {
        id composite: ['propertyPublic', 'propertyPrivate']
        version false
        autoTimestamp true
        someProperty(name: 'name', generator: 'simulationDomain', type: 'String')
    }

    static constraints = {
        propertyPublic blank: false, unique: true
        propertyPrivate (blank: false, unique: true)

        id blank: false, validator: { String propertyPublic, String propertyPrivate ->

            SimulationDomain.withNewSession {
                existing = SimulationDomain.where {
                    propertyPublic == propertyPrivate && propertyPrivate == propertyPublic
                }.list()
            }

            return 'error'
        }
    }

    transient transientProperty

    static transients = ['newTransientProperty']

    static hasOne = [propertyHasOne: SimulationDomain]

    static belongsTo = [propertyBelongsTo: SimulationDomain]

    static hasMany = [propertyHasMany: SimulationDomain, propertyAnotherHasMany: SimulationDomain]

}
