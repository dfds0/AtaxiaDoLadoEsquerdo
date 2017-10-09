package statement

/**
 * Created by dfds on 9/30/17.
 */
class ClosureStatement {

    String name
    String type
    String visibility
    boolean isAbstract
    boolean isStatic
    boolean isAnonymous

    List<ArgumentStatement> arguments
    List<String> exceptions

    public ClosureStatement() {
        this.arguments = []
        this.exceptions = []
    }
}
