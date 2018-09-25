package etai.Elements;

import org.apache.beam.sdk.coders.*;

@DefaultCoder(AvroCoder.class)
public class BveElement {
    private Integer Id;
    private String marque;
    private String modele;
    private String modelegen;
    private String generation;
    private String alimentation;
    private Integer capacitecarter;
    private String carrosserie;
    private String chassis;
    private Integer codeconduite;
    private Integer cylindreecm3;
    private float cylindreelit;
    private Integer cylindresnbr;
    private Integer energie;
    private Integer phase;
    private Integer portesnbr;
    private Integer	puissancekw;
    private Integer	puissancecom;
    private String typemoteur;
    private String typeboitevitesses;
    private Integer	vitessesbte;
    private Integer	vitessesnbr;
    private String typevehicule;
    private String injection;

    BveElement(Integer Id, String marque, String modele, String modelegen, String generation, String alimentation, Integer capacitecarter, String carrosserie, String chassis, Integer codeconduite, Integer cylindreecm3, float cylindreelit, Integer cylindresnbr, Integer energie, Integer phase, Integer portesnbr, Integer puissancekw, Integer puissancecom, String typemoteur, String typeboitevitesses, Integer vitessesbte, Integer vitessesnbr, String typevehicule, String injection ) {
        this.Id = Id;
        this.marque = marque;
        this.modele = modele;
        this.modelegen = modelegen;
        this.generation = generation;
        this.alimentation = alimentation;
        this.capacitecarter = capacitecarter;
        this.carrosserie = carrosserie;
        this.chassis = chassis;
        this.codeconduite = codeconduite;
        this.cylindreecm3 = cylindreecm3;
        this.cylindreelit = cylindreelit;
        this.cylindresnbr = cylindresnbr;
        this.energie = energie;
        this.phase = phase;
        this.portesnbr = portesnbr;
        this.puissancekw = puissancekw;
        this.puissancecom = puissancecom;
        this.typemoteur = typemoteur;
        this.typeboitevitesses = typeboitevitesses;
        this.vitessesbte = vitessesbte;
        this.vitessesnbr = vitessesnbr;
        this.typevehicule = typevehicule;
        this.injection = injection;
    }

    @Override
    public String toString() {
        return "BveElement{" +
                "Id=" + Id +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", modelegen='" + modelegen + '\'' +
                ", generation='" + generation + '\'' +
                ", alimentation='" + alimentation + '\'' +
                ", capacitecarter=" + capacitecarter +
                ", carrosserie='" + carrosserie + '\'' +
                ", chassis='" + chassis + '\'' +
                ", codeconduite=" + codeconduite +
                ", cylindreecm3=" + cylindreecm3 +
                ", cylindreelit=" + cylindreelit +
                ", cylindresnbr=" + cylindresnbr +
                ", energie=" + energie +
                ", phase=" + phase +
                ", portesnbr=" + portesnbr +
                ", puissancekw=" + puissancekw +
                ", puissancecom=" + puissancecom +
                ", typemoteur='" + typemoteur + '\'' +
                ", typeboitevitesses='" + typeboitevitesses + '\'' +
                ", vitessesbte=" + vitessesbte +
                ", vitessesnbr=" + vitessesnbr +
                ", typevehicule='" + typevehicule + '\'' +
                ", injection='" + injection + '\'' +
                '}';
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getModelegen() {
        return modelegen;
    }

    public void setModelegen(String modelegen) {
        this.modelegen = modelegen;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getAlimentation() {
        return alimentation;
    }

    public void setAlimentation(String alimentation) {
        this.alimentation = alimentation;
    }

    public Integer getCapacitecarter() {
        return capacitecarter;
    }

    public void setCapacitecarter(Integer capacitecarter) {
        this.capacitecarter = capacitecarter;
    }

    public String getCarrosserie() {
        return carrosserie;
    }

    public void setCarrosserie(String carrosserie) {
        this.carrosserie = carrosserie;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public Integer getCodeconduite() {
        return codeconduite;
    }

    public void setCodeconduite(Integer codeconduite) {
        this.codeconduite = codeconduite;
    }

    public Integer getCylindreecm3() {
        return cylindreecm3;
    }

    public void setCylindreecm3(Integer cylindreecm3) {
        this.cylindreecm3 = cylindreecm3;
    }

    public float getCylindreelit() {
        return cylindreelit;
    }

    public void setCylindreelit(float cylindreelit) {
        this.cylindreelit = cylindreelit;
    }

    public Integer getCylindresnbr() {
        return cylindresnbr;
    }

    public void setCylindresnbr(Integer cylindresnbr) {
        this.cylindresnbr = cylindresnbr;
    }

    public Integer getEnergie() {
        return energie;
    }

    public void setEnergie(Integer energie) {
        this.energie = energie;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public Integer getPortesnbr() {
        return portesnbr;
    }

    public void setPortesnbr(Integer portesnbr) {
        this.portesnbr = portesnbr;
    }

    public Integer getPuissancekw() {
        return puissancekw;
    }

    public void setPuissancekw(Integer puissancekw) {
        this.puissancekw = puissancekw;
    }

    public Integer getPuissancecom() {
        return puissancecom;
    }

    public void setPuissancecom(Integer puissancecom) {
        this.puissancecom = puissancecom;
    }

    public String getTypemoteur() {
        return typemoteur;
    }

    public void setTypemoteur(String typemoteur) {
        this.typemoteur = typemoteur;
    }

    public String getTypeboitevitesses() {
        return typeboitevitesses;
    }

    public void setTypeboitevitesses(String typeboitevitesses) {
        this.typeboitevitesses = typeboitevitesses;
    }

    public Integer getVitessesbte() {
        return vitessesbte;
    }

    public void setVitessesbte(Integer vitessesbte) {
        this.vitessesbte = vitessesbte;
    }

    public Integer getVitessesnbr() {
        return vitessesnbr;
    }

    public void setVitessesnbr(Integer vitessesnbr) {
        this.vitessesnbr = vitessesnbr;
    }

    public String getTypevehicule() {
        return typevehicule;
    }

    public void setTypevehicule(String typevehicule) {
        this.typevehicule = typevehicule;
    }

    public String getInjection() {
        return injection;
    }

    public void setInjection(String injection) {
        this.injection = injection;
    }
}



