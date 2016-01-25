package com.arolla.jam.calculatrice.model;

import java.io.Serializable;
import java.util.UUID;

public class Calcul implements Serializable {

    private String id;
    private Operateur operateur;
    private Operande a;
    private Operande b;


    /**
     * Convention: A operateur B
     *
     * @param operateur l'operateur à utiliser entre les operateurs
     * @param a le premier operande,se situe à gauche du signe
     * @param b le deuxiéme operande,se situe à droite du signe
     */
    public Calcul(final Operateur operateur, final int a, final int b) {
        this(operateur,new IntegerOperande(a),new IntegerOperande(b));
    }

    /**
     * Convention: A operateur B
     *
     * @param operateur l'operateur à utiliser entre les operateurs
     * @param a le premier operande,se situe à gauche du signe (peut être un calcul)
     * @param b le deuxiéme operande,se situe à droite du signe (peut être un calcul)
     */
    public Calcul(final Operateur operateur, final Operande a, final Operande b) {
        this.id= UUID.randomUUID().toString().replace("-","");
        this.operateur = operateur;
        this.a = a;
        this.b = b;
    }
    /**
     * Convention: A operateur B
     *
     * @param operateur l'operateur à utiliser entre les operateurs
     * @param a le premier operande,se situe à gauche du signe (peut être un calcul)
     * @param b le deuxiéme operande,se situe à droite du signe (peut être un calcul)
     */
    public Calcul(final Operateur operateur, final int a, final String b) {
        this(operateur,new IntegerOperande(a),new FutureOperande(b));
    }

    private Calcul() {
        super();
    }

    @Override
    public String toString() {
        return getId()+":["+a +" "+ operateur.getSign()+" " + b+"]";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final Calcul calcul = (Calcul) o;

        if (a != calcul.a) { return false; }
        if (b != calcul.b) { return false; }
        return operateur == calcul.operateur;
    }

    @Override
    public int hashCode() {
        int result = operateur != null ? operateur.hashCode() : 0;
        result = 31 * result + a.getId().hashCode();
        result = 31 * result + b.getId().hashCode();
        return result;
    }

    public String getId() {
        return id;
    }

    public Operateur getOperateur() {
        return operateur;
    }

    public Operande getA() {
        return a;
    }

    public Operande getB() {
        return b;
    }


}
