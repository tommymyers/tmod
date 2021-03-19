package cc.tommymyers.tmod.input;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

/**
 * A representation of keys on the keyboard
 */
public enum Key {
    A('A', GLFW_KEY_A),
    B('B', GLFW_KEY_B),
    C('C', GLFW_KEY_C),
    D('D', GLFW_KEY_D),
    E('E', GLFW_KEY_E),
    F('F', GLFW_KEY_F),
    G('G', GLFW_KEY_G),
    H('H', GLFW_KEY_H),
    I('I', GLFW_KEY_I),
    J('J', GLFW_KEY_J),
    K('K', GLFW_KEY_K),
    L('L', GLFW_KEY_L),
    M('M', GLFW_KEY_M),
    N('N', GLFW_KEY_N),
    O('O', GLFW_KEY_O),
    P('P', GLFW_KEY_P),
    Q('Q', GLFW_KEY_Q),
    R('R', GLFW_KEY_R),
    S('S', GLFW_KEY_S),
    T('T', GLFW_KEY_T),
    U('U', GLFW_KEY_U),
    V('V', GLFW_KEY_V),
    W('W', GLFW_KEY_W),
    X('X', GLFW_KEY_X),
    Y('Y', GLFW_KEY_Y),
    Z('Z', GLFW_KEY_Z);

    private static final Map<Character, Key> keysByCharacter = new HashMap<>();

    static {
        for (Key key: Key.values()) {
            keysByCharacter.put(key.character, key);
        }
    }

    public static Key getKeyByCharacter(char character) {
        return keysByCharacter.get(character);
    }

    private final char character;
    private final int code;

    Key(char character, int code) {
        this.character = character;
        this.code = code;
    }

    @Override
    public String toString() {
        return String.valueOf(character);
    }

    public int getCode() {
        return code;
    }

}
