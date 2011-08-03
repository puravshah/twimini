package twimini.services;

/**
 * Created by IntelliJ IDEA.
 * User: purav.s
 * Date: 8/3/11
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONError extends Exception {

    public JSONError(String errorMsg) {
        super(errorMsg);
    }
}
