package me.grax.genderswap.scales;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Scales {
  public static final int MAJOR = 0;
  public static final int MINOR = 1;

  public static final Scale CMAJOR = new Scale("C", MAJOR, 0, 2, 4, 5, 7, 9, 11);
  public static final Scale CSMAJOR = new Scale("C#", MAJOR, 1, 3, 5, 6, 8, 10, 12);
  public static final Scale DMAJOR = new Scale("D", MAJOR, 2, 4, 6, 7, 9, 11, 13);
  public static final Scale EbMAJOR = new Scale("Eb", MAJOR, 3, 5, 7, 8, 10, 12, 14);
  public static final Scale EMAJOR = new Scale("E", MAJOR, 4, 6, 8, 9, 11, 13, 15);
  public static final Scale FMAJOR = new Scale("F", MAJOR, 5, 7, 9, 10, 12, 14, 16);
  public static final Scale FSMAJOR = new Scale("F#", MAJOR, 6, 8, 10, 11, 13, 15, 17);
  public static final Scale GMAJOR = new Scale("G", MAJOR, 7, 9, 11, 12, 14, 16, 18);
  public static final Scale AbMAJOR = new Scale("Ab", MAJOR, 8, 10, 12, 13, 15, 17, 19);
  public static final Scale AMAJOR = new Scale("A", MAJOR, 9, 11, 13, 14, 16, 18, 20);
  public static final Scale BbMAJOR = new Scale("Bb", MAJOR, 10, 12, 14, 15, 17, 19, 21);
  public static final Scale BMAJOR = new Scale("B", MAJOR, 11, 13, 15, 16, 18, 20, 22);

  public static final Scale CMINOR = new Scale("C", MINOR, 0, 2, 3, 5, 7, 8, 10);
  public static final Scale CSMINOR = new Scale("C#", MINOR, 1, 3, 4, 6, 8, 9, 11);
  public static final Scale DMINOR = new Scale("D", MINOR, 2, 4, 5, 7, 9, 10, 12);
  public static final Scale EbMINOR = new Scale("Eb", MINOR, 3, 5, 6, 8, 10, 11, 13);
  public static final Scale EMINOR = new Scale("E", MINOR, 4, 6, 7, 9, 11, 12, 14);
  public static final Scale FMINOR = new Scale("F", MINOR, 5, 7, 8, 10, 12, 13, 15);
  public static final Scale FSMINOR = new Scale("F#", MINOR, 6, 8, 9, 11, 13, 14, 16);
  public static final Scale GMINOR = new Scale("G", MINOR, 7, 9, 10, 12, 14, 15, 17);
  public static final Scale AbMINOR = new Scale("Ab", MINOR, 8, 10, 11, 13, 15, 16, 18);
  public static final Scale AMINOR = new Scale("A", MINOR, 9, 11, 12, 14, 16, 17, 19);
  public static final Scale BbMINOR = new Scale("Bb", MINOR, 10, 12, 13, 15, 17, 18, 20);
  public static final Scale BMINOR = new Scale("B", MINOR, 11, 13, 14, 16, 18, 19, 21);

  public static List<Scale> scales = Arrays.asList(CMAJOR, CSMAJOR, DMAJOR, EbMAJOR, EMAJOR, FMAJOR, FSMAJOR, GMAJOR, AbMAJOR, AMAJOR, BbMAJOR,
      BMAJOR, CMINOR, CSMINOR, DMINOR, EbMINOR, EMINOR, FMINOR, FSMINOR, GMINOR, AbMINOR, AMINOR, BbMINOR, BMINOR);

  static {
    CMAJOR.linkRelative(AMINOR);
    CSMAJOR.linkRelative(BbMINOR);
    DMAJOR.linkRelative(BMINOR);
    EbMAJOR.linkRelative(CMINOR);
    EMAJOR.linkRelative(CSMINOR);
    FMAJOR.linkRelative(DMINOR);
    FSMAJOR.linkRelative(EbMINOR);
    GMAJOR.linkRelative(EMINOR);
    AbMAJOR.linkRelative(FMINOR);
    AMAJOR.linkRelative(FSMINOR);
    BbMAJOR.linkRelative(GMINOR);
    BMAJOR.linkRelative(AbMINOR);
  }

  public static class Scale {
    private final List<Integer> possibleKeys;
    private final String name;
    private final int type;
    private Scale relative;

    protected Scale(String name, int type, int... keys) {
      this.name = name;
      this.type = type;
      this.relative = null;
      for (int i = 0; i < keys.length; i++) {
        keys[i] = keys[i] % 12;
      }
      this.possibleKeys = Arrays.stream(keys).boxed().collect(Collectors.toList());
    }

    public boolean isOnScale(int key) {
      return possibleKeys.contains(key % 12);
    }

    public List<Integer> getPossibleKeys() {
      return possibleKeys;
    }

    public String getName() {
      return name;
    }

    public int getType() {
      return type;
    }

    public Scale getRelative() {
      return relative;
    }

    protected void linkRelative(Scale relative) {
      this.relative = relative;
      relative.relative = this;
    }
  }
}
