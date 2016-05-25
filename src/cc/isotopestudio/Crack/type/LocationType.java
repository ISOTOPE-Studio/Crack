package cc.isotopestudio.Crack.type;

import cc.isotopestudio.Crack.utli.S;

/**
 * Created by Mars on 5/15/2016.
 * Copyright ISOTOPE Studio
 */
public enum LocationType {

    LOBBY(S.toBoldGold("����")),
    GAME(S.toBoldGreen("������")),
    NONE(S.toBoldRed("������Ϸ��")),
    RESPAWN(S.toBoldDarkAqua("������"));

    private final String name;

    LocationType(String name){
        this.name = name;
    }
}
