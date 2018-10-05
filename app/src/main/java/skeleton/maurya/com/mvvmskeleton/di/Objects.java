package skeleton.maurya.com.mvvmskeleton.di;

/**
 * Objects class is used to compare two object
 *
 * @Version1.0
 */
public class Objects {

    public static boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        if (o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }
}
