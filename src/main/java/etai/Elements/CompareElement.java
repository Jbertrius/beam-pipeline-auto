package etai.Elements;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import org.apache.avro.reflect.Nullable;

import java.io.Serializable;

@AutoValue
public abstract class CompareElement implements Serializable, Comparable<CompareElement> {

    public abstract String marque();
    public abstract String immat();
    public abstract Integer idvariante();
    public abstract String modele();
    public abstract Integer cylindreecm3();
    public abstract Integer vitessesnbr();
    public abstract Integer portesnbr();
    public abstract Integer puissancecom();
    public abstract String vin ();

    public static CompareElement create ( String marque, String immat, Integer idvariante, String model, Integer cylindr, Integer nbVit, Integer nbport, Integer puiss, String vin) {
        return new AutoValue_CompareElement(
                marque,
                immat,
                idvariante,
                model,
                cylindr,
                nbVit,
                nbport,
                puiss,
                vin );
    }

    @Override
    public int compareTo(CompareElement other) {
        return immat().compareTo(other.immat());
    }

    @Memoized
    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(@Nullable Object x);
}
