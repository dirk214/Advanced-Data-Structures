import java.io.Serializable;

/**
 * This is a record object used to simplify the data.
 * @author Andrew Herbert
 * @version Jul 7, 2017
 */
public class Record implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is the xCoordinate;
     */
    int x;
    
    /**
     * This is the yCoordinate.
     */
    int y;
    
    /**
     * This is the city name of the Record.
     */
    String cityname;
        
    /**
     * This is the constructor for the record.
     * @param x Sets the xCo for the record.
     * @param y Sets the yCo for the record.
     * @param cityname Sets the name for the record.
     */
    public Record(int x, int y, String cityname) {
        this.x = x;
        this.y = y;
        this.cityname = cityname;
    }
    
}
