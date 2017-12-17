package models;


import qualifiers.AccidentalDegree;

import javax.inject.Inject;

public class Accidental {

    private final AccidentalType accidentalType;
    private final int degree;

    public Accidental(AccidentalType accidentalType) {
        this(accidentalType, 1);
    }

    @Inject
    public Accidental(AccidentalType accidentalType, @AccidentalDegree int degree) {
        if (accidentalType == AccidentalType.NATURAL && degree != 1) {
            throw new IllegalArgumentException("Naturals cannot be any degree but 1");
        }

        this.accidentalType = accidentalType;
        this.degree = degree;
    }

    public AccidentalType getAccidentalType() {
        return accidentalType;
    }

    public int getDegree() {
        return degree;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(AccidentalType.toString(accidentalType));

        int count = 1;
        while (count++ < degree) {
            stringBuilder.append(AccidentalType.toString(accidentalType));
        }

        return stringBuilder.toString();
    }
}
