package etat.apothicon.object.weapon.gun;

import etat.apothicon.entity.Player;
import etat.apothicon.utility.sound.GunSound;

public class GunDirector {

    public static void buildM14(Player player, GunBuilder builder) {
        builder.name("M14")
                .upgradedName("Ember")
                .image("src/main/resources/guns/m14-1.png")
                .upgradedImagePath("src/main/resources/guns/m14-pap.png")
                .owner(player)
                .fireSound(GunSound.M14_FIRE)
                .upgradedFireSound(GunSound.M14_UPGRADED_FIRE)
                .reloadSound(GunSound.M14_RELOAD)
                .damage(140)
                .upgradedDamage(300)
                .defaultAmmoPerMagazine(8)
                .upgradedDefaultAmmoPerMagazine(12)
                .reserve(92)
                .upgradedDefaultReserve(144)
                .fireRate(1.0f)
                .fireType(FireType.SEMI_AUTO)
                .reloadRate(2.5f);
    }

    public static void buildM16(Player player, GunBuilder builder) {
        builder.name("M16")
                .upgradedName("Emancipator 16000")
                .image("src/main/resources/guns/m16.png")
                .upgradedImagePath("src/main/resources/guns/m16-pap.png")
                .owner(player)
                .fireSound(GunSound.M16_FIRE)
                .upgradedFireSound(GunSound.M16_UPGRADED_FIRE)
                .reloadSound(GunSound.M16_RELOAD)
                .damage(50)
                .upgradedDamage(100)
                .defaultAmmoPerMagazine(30)
                .upgradedDefaultAmmoPerMagazine(40)
                .reserve(180)
                .upgradedDefaultReserve(360)
                .fireRate(1.0f)
                .fireType(FireType.BURST)
                .reloadRate(1.0f);
    }

    public static void buildM1911(Player player, GunBuilder builder) {
        builder.name("M1911")
                .upgradedName("Abel & Aubrey")
                .image("src/main/resources/guns/m1911.png")
                .upgradedImagePath("src/main/resources/guns/m1911-pap.png")
                .owner(player)
                .fireSound(GunSound.M1911_FIRE_1)
                .upgradedFireSound(GunSound.M1911_UPGRADED_FIRE_1)
                .reloadSound(GunSound.M1911_RELOAD)
                .damage(35)
                .upgradedDamage(400)
                .defaultAmmoPerMagazine(80)
                .upgradedDefaultAmmoPerMagazine(6)
                .reserve(32)
                .upgradedDefaultReserve(50)
                .fireRate(1.0f)
                .fireType(FireType.SEMI_AUTO)
                .reloadRate(1.0f);
    }

    public static void buildMP40(Player player, GunBuilder builder) {
        builder.name("MP40")
                .upgradedName("Mortality Provider 4000")
                .image("src/main/resources/guns/mp40.png")
                .upgradedImagePath("src/main/resources/guns/mp40-pap.png")
                .owner(player)
                .fireSound(GunSound.MP40_FIRE)
                .upgradedFireSound(GunSound.MP40_UPGRADED_FIRE)
                .reloadSound(GunSound.MP40_RELOAD)
                .damage(75)
                .upgradedDamage(150)
                .defaultAmmoPerMagazine(32)
                .upgradedDefaultAmmoPerMagazine(64)
                .reserve(192)
                .upgradedDefaultReserve(384)
                .fireRate(0.8f)
                .fireType(FireType.AUTO)
                .reloadRate(1.5f);
    }

    public static void buildOlympia(Player player, GunBuilder builder) {
        builder.name("Olympia")
                .upgradedName("Crete 725")
                .image("src/main/resources/guns/olympia.png")
                .upgradedImagePath("src/main/resources/guns/olympia-pap.png")
                .owner(player)
                .fireSound(GunSound.OLYMPIA_FIRE)
                .upgradedFireSound(GunSound.OLYMPIA_UPGRADED_FIRE)
                .reloadSound(GunSound.OLYMPIA_RELOAD)
                .damage(450)
                .upgradedDamage(1000)
                .defaultAmmoPerMagazine(2)
                .upgradedDefaultAmmoPerMagazine(2)
                .reserve(32)
                .upgradedDefaultReserve(50)
                .fireRate(3.0f)
                .fireType(FireType.SEMI_AUTO)
                .reloadRate(4.0f);
    }

    public static void buildStakeout(Player player, GunBuilder builder) {
        builder.name("Stakeout")
                .upgradedName("Apprehension")
                .image("src/main/resources/guns/stakeout.png")
                .upgradedImagePath("src/main/resources/guns/stakeout-pap.png")
                .owner(player)
                .fireSound(GunSound.STAKEOUT_FIRE)
                .upgradedFireSound(GunSound.STAKEOUT_UPGRADED_FIRE)
                .reloadSound(GunSound.STAKEOUT_RELOAD)
                .damage(500)
                .upgradedDamage(1000)
                .defaultAmmoPerMagazine(8)
                .upgradedDefaultAmmoPerMagazine(10)
                .reserve(32)
                .upgradedDefaultReserve(70)
                .fireRate(5.0f)
                .fireType(FireType.SEMI_AUTO)
                .reloadRate(2.0f);
    }


}
