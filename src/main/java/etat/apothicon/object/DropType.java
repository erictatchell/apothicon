package etat.apothicon.object;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum DropType {
    INSTA_KILL,
    MAX_AMMO,
    DOUBLE_POINTS,
    FIRE_SALE,
    INFINITE_AMMO;

    // credit: https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
    private static final List<DropType> VALUES =
            List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static DropType randomDrop() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
