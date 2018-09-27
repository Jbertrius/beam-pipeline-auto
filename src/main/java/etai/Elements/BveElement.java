package etai.Elements;

import com.google.auto.value.extension.memoized.Memoized;

import java.io.Serializable;
import com.google.auto.value.AutoValue;


@AutoValue
public abstract class BveElement implements Serializable, Comparable<BveElement> {

    public abstract Integer Id();
    public abstract String marque();
    public abstract String modele();
    public abstract String modelegen();
    public abstract String generation();
    public abstract String alimentation(); 
    public abstract float capacitecarter();
    public abstract String carrosserie(); 
    public abstract String chassis(); 
    public abstract String codeconduite();
    public abstract Integer cylindreecm3(); 
    public abstract float cylindreelit(); 
    public abstract Integer cylindresnbr(); 
    public abstract String energie();
    public abstract Integer phase(); 
    public abstract Integer portesnbr(); 
    public abstract Integer	puissancekw(); 
    public abstract Integer	puissancecom(); 
    public abstract String typemoteur(); 
    public abstract String typeboitevitesses(); 
    public abstract String	vitessesbte();
    public abstract Integer	vitessesnbr();
    public abstract String typevehicule(); 
    public abstract String injection(); 

    public static BveElement create(
            Integer Id,
            String marque,
            String modele,
            String modelegen,
            String generation,
            String alimentation,
            float capacitecarter,
            String carrosserie,
            String chassis,
            String codeconduite,
            Integer cylindreecm3,
            float cylindreelit,
            Integer cylindresnbr,
            String energie,
            Integer phase,
            Integer portesnbr,
            Integer puissancecom,
            Integer puissancekw,
            String typemoteur,
            String typeboitevitesses,
            String vitessesbte,
            Integer vitessesnbr,
            String typevehicule,
            String injection ) {
        return new AutoValue_BveElement(Id, marque, 
                modele, modelegen, 
                generation, alimentation,
                capacitecarter, carrosserie,
                chassis, codeconduite, cylindreecm3,
                cylindreelit, cylindresnbr, energie,
                phase, portesnbr, puissancecom, puissancekw,
                typemoteur, typeboitevitesses, vitessesbte, 
                vitessesnbr, typevehicule, injection );
    }

    @Override
    public int compareTo(BveElement other) {
        return Id().compareTo(other.Id());
    }

    @Memoized
    @Override
    public abstract String toString();
}



