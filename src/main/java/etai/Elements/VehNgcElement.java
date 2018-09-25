package etai.Elements;

import com.google.auto.value.extension.memoized.Memoized;

import java.io.Serializable;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VehNgcElement implements Serializable, Comparable<VehNgcElement>   {

    public abstract  Integer id();
    public abstract  String marque();
    public abstract  String vin();
    public abstract  String immat();
    public abstract  String modele();
    public abstract  String version();
    public abstract  String typevarversprf();
    public abstract  String cnit_mines();
    public abstract  String energie();
    public abstract  String codemoteur();
    public abstract  String tpboitevit();
    public abstract  String nbvitesse();
    public abstract  String cylindree();
    public abstract  String nbportes();
    public abstract  String carrosserie();
    public abstract  String carrosseriecg();
    public abstract  String genrev();
    public abstract  String genrevcg();
    public abstract  String puisfisc();
    public abstract  String puiskw();
    public abstract  String nbcylind();
    public abstract  String nbplass();
    public abstract  String empat();
    public abstract  String largeur();
    public abstract  String longueur();
    public abstract  String hauteur();

    public static VehNgcElement create (
            Integer id,
            String marque,
            String vin,
            String immat,
            String modele,
            String version,
            String typevarversprf,
            String cnit_mines,
            String energie,
            String codemoteur,
            String tpboitevit,
            String nbvitesse,
            String cylindree,
            String nbportes,
            String carrosserie,
            String carrosseriecg,
            String genrev,
            String genrevcg,
            String puisfisc,
            String puiskw,
            String nbcylind,
            String nbplass,
            String empat,
            String largeur,
            String longueur,
            String hauteur
    ){
        return new AutoValue_VehNgcElement(
                id,
                marque,
                vin,
                immat,
                modele,
                version,
                typevarversprf,
                cnit_mines,
                energie,
                codemoteur,
                tpboitevit,
                nbvitesse,
                cylindree,
                nbportes,
                carrosserie,
                carrosseriecg,
                genrev,
                genrevcg,
                puisfisc,
                puiskw,
                nbcylind,
                nbplass,
                empat,
                largeur,
                longueur,
                hauteur );
    }

    @Override
    public int compareTo(VehNgcElement other) {
        return id().compareTo(other.id());
    }

    @Memoized
    @Override
    public abstract String toString();


}
