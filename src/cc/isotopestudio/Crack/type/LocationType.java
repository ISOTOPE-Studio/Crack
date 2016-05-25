package cc.isotopestudio.Crack.type;

import cc.isotopestudio.Crack.utli.S;

/**
 * Created by Mars on 5/15/2016.
 * Copyright ISOTOPE Studio
 */
public enum LocationType {

    LOBBY(S.toBoldGold("大厅")),
    GAME(S.toBoldGreen("房间中")),
    NONE(S.toBoldRed("不在游戏中")),
    RESPAWN(S.toBoldDarkAqua("复活中"));

    private final String name;

    LocationType(String name){
        this.name = name;
    }
}
