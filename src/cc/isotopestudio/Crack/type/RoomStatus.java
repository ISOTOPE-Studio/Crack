package cc.isotopestudio.Crack.type;

import cc.isotopestudio.Crack.utli.S;

/**
 * Created by Mars on 5/15/2016.
 * Copyright ISOTOPE Studio
 */
public enum RoomStatus {

    WAITING(S.toGreen("等待中")),
    PROGRESS(S.toYellow("游戏中")),
    BOSS(S.toBoldRed("BOSS"));
    private final String name;

    RoomStatus(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
