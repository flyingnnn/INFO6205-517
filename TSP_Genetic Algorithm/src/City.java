/**City Class defines the cityNode which forms the route
 * @author Nenghui Fang
 *
 */
public class City {
    //coordinator for each City
    private int x;
    private int y;

    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public City(){
        this.x = (int)(Math.random() * TSP.MAX_EUCLID);
        this.y = (int)(Math.random() * TSP.MAX_EUCLID);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
