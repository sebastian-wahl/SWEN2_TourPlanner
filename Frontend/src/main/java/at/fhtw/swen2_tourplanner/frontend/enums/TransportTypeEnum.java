package at.fhtw.swen2_tourplanner.frontend.enums;

import lombok.Getter;

import java.security.AlgorithmConstraints;
import java.util.HashMap;
import java.util.Map;

public enum TransportTypeEnum {
    FOOT(0, "Foot"),
    BIKE(1, "Bike"),
    SKATEBOARD(2, "Skateboard");

    @Getter
    private final int dbValue;
    @Getter
    private final String name;

    private static Map<Integer, TransportTypeEnum> map = new HashMap<>();

    TransportTypeEnum(int value, String name) {
        this.dbValue = value;
        this.name = name;
    }

    static {
        for (TransportTypeEnum transportType : TransportTypeEnum.values()) {
            map.put(transportType.dbValue, transportType);
        }
    }

    public static TransportTypeEnum valueOf(int transportType) {
        return map.get(transportType);
    }




}
