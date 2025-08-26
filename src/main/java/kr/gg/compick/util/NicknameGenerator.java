package kr.gg.compick.util;

import java.security.SecureRandom;
import java.text.Normalizer;

public final class NicknameGenerator {
    private static final SecureRandom RND = new SecureRandom();
    private static final String[] ADJ = {"빠른","신비","푸른","든든","귀여운","차분한","용감한","재치있는","빛나는","강철"};
    private static final String[] NOUN = {"호랑이","고양이","독수리","고래","여우","판다","늑대","수달","사자","두루미"};

    private NicknameGenerator(){}

    public static String candidate(){
        String a = ADJ[RND.nextInt(ADJ.length)];
        String n = NOUN[RND.nextInt(NOUN.length)];
        int num = 100 + RND.nextInt(900);
        return a + n + num;
    }

    public static String normalize(String s){
        return Normalizer.normalize(s, Normalizer.Form.NFC).strip();
    }

}
