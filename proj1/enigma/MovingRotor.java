package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Jacqueline Chu
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initially in its 0 setting (first character of its
     *  alphabet).
     */

    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
        _permutation = perm;
    }


    @Override
    boolean rotates() {
        return true;
    }

    @Override
    boolean atNotch() {
        for (int i = 0; i < _notches.length(); i = i + 1) {
            if (alphabet().toInt(_notches.charAt(i)) == this.setting()) {
                return true;
            }
        }
        return false;
    }

    @Override
    void advance() {
        super.set(_permutation.wrap(super.setting() + 1));
    }

    /** Additional string E. */
    private String _notches;

    /** Additional permutation. */
    private Permutation _permutation;
}
