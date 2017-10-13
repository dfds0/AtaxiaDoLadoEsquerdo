package simulation
/**
 * Created by dfds on 10/12/17.
 */

@interface SimulationAnnotation {

    public enum State {
        LOW, MEDIUM, HIGH
    }

    State state() default State.MEDIUM

    String name() default "Simulation"

    String[] value() default []
}