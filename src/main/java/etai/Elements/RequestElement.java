package etai.Elements;

import com.google.auto.value.extension.memoized.Memoized;

import java.io.Serializable;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RequestElement implements Serializable, Comparable<RequestElement>  {

    public abstract Integer id();
    public abstract String immat();
    public abstract String pays();
    public abstract String algori();
    public abstract String resultat();


    public static RequestElement create (Integer id, String immat, String pays, String algori, String resultat) {
        return new AutoValue_RequestElement(id, immat, pays, algori, resultat);
    }


    @Override
    public int compareTo(RequestElement other) {
        return id().compareTo(other.id());
    }

    @Memoized
    @Override
    public abstract String toString();


}
