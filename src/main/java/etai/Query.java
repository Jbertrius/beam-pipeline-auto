package etai;

public enum Query {

    queryReqRemote("select a.id, a.immat, a.pays, a.algori, replace(b.resultat, ' ', ',') from requetes a join resultats b on a.resident_fk = b.id where length(b.resultat) > 0"),

    queryReqLocal("select a.id, a.immat, a.pays, a.algori,  a.resultat from resultats_brut a"),

    queryBVERemote("Select \n" +
            "      c.id, \n" +
            "      a.nom,  \n" +
            "      b.gamme,  \n" +
            "      b.nom, \n" +
            "      b.serie, \n" +
            "      e.description as alimentation,  \n" +
            "      c.capacitecarter, \n" +
            "      f.description as carrosserie,  \n" +
            "      c.chassis,  \n" +
            "      c.coteconduite,  \n" +
            "      c.cylindreecm3,  \n" +
            "      c.cylindreelit,  \n" +
            "      c.cylindresnbr,  \n" +
            "      c.energie,  \n" +
            "      c.phase,  \n" +
            "      c.portesnbr,  \n" +
            "      c.puissancecom,  \n" +
            "      c.puissancekw, \n" +
            "      c.typemoteur, \n" +
            "      c.typeboitevitesses, \n" +
            "      c.vitessesbte, c.vitessesnbr, d.code, \n" +
            "      c.injection  \n" +
            "from \n" +
            "      bve_marque a  \n" +
            "INNER JOIN  \n" +
            "      bve_modele b on a.id= b.idmarque \n" +
            "INNER JOIN  \n" +
            "      bve_variante c on b.id= c.idmodele \n" +
            "INNER JOIN \n" +
            "      bve_typevehicule d on d.id= c.typevehicule \n" +
            "INNER JOIN  \n" +
            "      bve_codecaracteristiquevaleur e on e.codevaleur = c.alimentation \n" +
            "INNER JOIN \n" +
            "      bve_codecaracteristiquevaleur f on f.codevaleur = c.carrosserie \n" +
            "order by \n" +
            "      a.nom"),

    queryBVELocal("SELECT * from bve_final"),

    queryNGC("select \n" +
            "      id, \n" +
            "      marque, \n" +
            "      vin, \n" +
            "      immat, \n" +
            "      modele, \n" +
            "      version, \n" +
            "      typevarversprf, \n" +
            "      cnit_mines, \n" +
            "      energie, \n" +
            "      codemoteur, \n" +
            "      tpboitevit, \n" +
            "      nbvitesse, \n" +
            "      cylindree, \n" +
            "      nbportes, \n" +
            "      carrosserie, \n" +
            "      carrosseriecg, \n" +
            "      genrev, \n" +
            "      genrevcg, \n" +
            "      puisfisc, \n" +
            "      puiskw, \n" +
            "      nbcylind, \n" +
            "      nbplass, \n" +
            "      empat, \n" +
            "      largeur, \n" +
            "      longueur, \n" +
            "      hauteur \n" +
            "from \n" +
            "      vehngc  \n" +
            "where \n" +
            "      LENGTH(vin) > 0 \n" +
            "and \n" +
            "      vin not like '%,%' \n" +
            "and \n" +
            "      vin not like '%;%' \n" +
            "and \n" +
            "      vin not like '%\"%' \n" +
            "and \n" +
            "      LENGTH(vin) = CHAR_LENGTH(vin)");



    private final String query;

    Query(String s) {
        this.query = s;
    }
    public String getQuery(){ return this.query;}
}
