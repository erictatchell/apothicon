package etat.apothicon.main;

/**
 * <h2>GameState
 * <p>Represents various screen states</p></h2><br>
 * <strong>MAIN_MENU</strong><br>
 * <strong>PLAYING</strong><br>
 * <strong>NP_OPTIONS_MENU</strong>: Not playing while the options menu is active<br>
 * <strong>P_OPTIONS_MENU</strong>: Playing while the options menu is active<br>
 * <strong>DEATH_MENU</strong>: Game over screen<br>
 */
public enum GameState {
    MAIN_MENU,
    PLAYING,
    NP_OPTIONS_MENU,
    P_OPTIONS_MENU,
    DEATH_MENU
}
