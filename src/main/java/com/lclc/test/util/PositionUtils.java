package com.lclc.test.util;

public class PositionUtils {

    public static Coordinate fromatCoordinate(String str) {

        if (Tools.isNullOrEmpty(str)) {
            return null;
        }
        String[] strs = str.split(",");
        if (strs.length != 2) {
            return null;
        }
        Coordinate coordinate = null;
        try {
            Double longitude = Double.valueOf(strs[1]);
            Double dimension = Double.valueOf(strs[0]);
            // String longitude1 = df.format(longitude).replaceAll("\\.", "");
            // String dimension1 = df.format(dimension).replaceAll("\\.", "");
            longitude = longitude * 1000000d;
            dimension = dimension * 1000000d;
            coordinate = new Coordinate();
            coordinate.setLongitude(longitude.intValue());
            coordinate.setDimension(dimension.intValue());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return coordinate;
    }

}
