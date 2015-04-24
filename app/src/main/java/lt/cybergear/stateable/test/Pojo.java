package lt.cybergear.stateable.test;

/**
 * Created by Marius Kavoliūnas on 15.3.11.
 */
public class Pojo {

    private String one = "one";
    private Integer two = 254;
    private String three;

    public Pojo() {
    }

    public Pojo(String one, Integer two, String three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public Integer getTwo() {
        return two;
    }

    public void setTwo(Integer two) {
        this.two = two;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    @Override
    public String toString() {
        return one+";"+two+";"+three;
    }
}
