package at.fhtw.swen2_tourplanner.frontend.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum TransportTypeEnum {
    FOOT(0, "Foot"),
    BIKE(1, "Bike"),
    CAR(2, "Car");

    private static Map<Integer, TransportTypeEnum> map = new HashMap<>();

    static {
        for (TransportTypeEnum transportType : TransportTypeEnum.values()) {
            map.put(transportType.dbValue, transportType);
        }
    }

    @Getter
    private final int dbValue;
    @Getter
    private final String name;

    TransportTypeEnum(int value, String name) {
        this.dbValue = value;
        this.name = name;
    }

    public static TransportTypeEnum valueOf(int transportType) {
        return map.get(transportType);
    }
}
