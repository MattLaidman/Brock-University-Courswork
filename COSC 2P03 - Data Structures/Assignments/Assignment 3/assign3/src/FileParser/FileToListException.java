package FileParser;

/**
 * FileToListException is thrown by FileToList class if none, or an invalid file is given.
 *
 *
 * @author Matt Laidman (5199807)
 * @version 1.0 (October 21, 2014)
 */

public class FileToListException extends RuntimeException {

    public FileToListException (String arg) {

        super(arg);
    }
}