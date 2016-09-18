package utils;

/**
 * Created by Nick on 2016-08-21.
 */
public enum PitchLetter {
    C, D, E, F, G, A, B;

    public static int calculatePitchLetterNumber(PitchLetter pitchLetter) {
        switch (pitchLetter) {
            case C: return 0;
            case D: return 2;
            case E: return 4;
            case F: return 5;
            case G: return 7;
            case A: return 9;
            case B: return 11;
            default: throw new RuntimeException("Error getting pitch letter number value");
        }
    }

    public static CircularLinkedList<PitchLetter> diatonicCollection = new CircularLinkedList<PitchLetter>() {{
        append(new Node<>(C));
        append(new Node<>(D));
        append(new Node<>(E));
        append(new Node<>(F));
        append(new Node<>(G));
        append(new Node<>(A));
        append(new Node<>(B));
    }};

    public PitchLetter next() {
        return transpose(1);
    }

    public PitchLetter transpose(int interval) {
        if (interval < Interval.PitchLetterSpanValue.UNISON || interval > Interval.PitchLetterSpanValue.OCTAVE) {
            throw new RuntimeException("Interval out of bounds of octave span");
        }

        CircularLinkedList.Node<PitchLetter> node = getPitchLetterNode();
        int counter = 0;

        while (counter++ < interval) {
            node = node.getNext();
        }

        return node.getData();
    }

    private CircularLinkedList.Node<PitchLetter> getPitchLetterNode() {
        return diatonicCollection.findNode(this);
    }
}