package org.ms.iknow.core.type;

import java.util.ArrayList;
import java.util.List;

public enum RelationType {

    IS("1", "is"),
    IS_NOT("2", "is not"),
    HAS("3", "has"),
    HAS_MANY("4", "has more than one"),
    HAS_NOT("5", "has not"),
    IS_PART_OF("6", "is part of"),
    CONSISTS_OF("7", "consists of"),
    CONTAINS("8", "contains"),
    IS_EQUALS("9", "is equals to"),
    IS_NOT_EQUALS("10", "is not equals to"),
    UNKNOWN("999", "unknown");

    private String                    id;
    private String                    value;
    private static List<RelationType> list;
    static {
        list = new ArrayList<RelationType>();
        list.add(IS);
        list.add(IS_NOT);
        list.add(HAS);
        list.add(HAS_MANY);
        list.add(HAS_NOT);
        list.add(IS_PART_OF);
        list.add(CONSISTS_OF);
        list.add(CONTAINS);
        list.add(IS_EQUALS);
        list.add(IS_NOT_EQUALS);
        list.add(UNKNOWN);
    }

    RelationType(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public static RelationType getRelationTypeById(String id) {
        for (RelationType relationType : list) {
            System.out.println("   --- " + relationType.getId() + ":" + id);
            if (relationType.getId().equals(id)) {
                return relationType;
            }
        }
        return null;
    }

    public static RelationType getRelationTypeByValue(String value) {
        for (RelationType relationType : list) {
            if (relationType.getValue().equals(value)) {
                return relationType;
            }
        }
        return null;
    }

    public static List<RelationType> getAllValues() {
        return list;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
