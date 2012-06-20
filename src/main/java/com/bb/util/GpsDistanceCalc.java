package com.bb.util;

import com.bb.domain.Location;

/**
 * 本算法只适用北纬和东经地理坐标直接距离的计算。
 */

public class GpsDistanceCalc {
    private static boolean logOn = false;

    private static void log(String msg, Object obj) {
        if (logOn) {
            System.out.println(msg + " = " + obj);
        }
    }

    // 地球半径。单位:米。
    private static double EARTH_RADIUS = 6371004;

    private static double sin(double a) {
        return Math.sin(a);
    }

    private static double cos(double a) {
        return Math.cos(a);
    }

    private static double acos(double a) {
        return Math.acos(a);
    }

    /**
     * 转换经纬度为角度的double显示。只处理到小数点后2位(分:60进制)。
     */
    public static double convert2angle(double a) {
        log("a", a);
        // 转换60进制为10进制。
        double tem = (long) (a * 100) / 100;
        log("tem", tem);
        tem += (a * 100) % 100 / 60;
        log("tem", tem);
        double result = tem * Math.PI / 180.0;
        log("result", result);
        return result;
    }

    private static double abs(double a) {
        return Math.abs(a);
    }

    public static double computeDistance(double lat1, double lng1, double lat2, double lng2) {

        log("lat1", lat1);
        log("lng1", lng1);
        log("lat2", lat2);
        log("lng2", lng2);

        double OC = cos(convert2angle(lat1));
        log("OC", OC);

        double OD = cos(convert2angle(lat2));
        log("OD", OD);

        double AC = sin(convert2angle(lat1));
        log("AC", AC);

        double BD = sin(convert2angle(lat2));
        log("BD", BD);

        // AC=ED
        double BE = abs(BD - AC);
        log("BE", BE);

        double lngGap = convert2angle(lng1) - convert2angle(lng2);
        log("lngGap", lngGap);

        // AE=CD.
        double AE = Math.sqrt(OC * OC + OD * OD - 2 * OC * OD * cos(lngGap));

        log("AE", AE);

        double AB = Math.sqrt(AE * AE + BE * BE);
        log("AB", AB);

        double angle = acos((2 - AB * AB) / 2);
        log("angle", angle);

        double distance = EARTH_RADIUS * angle;
        log("distance", distance);

        return distance;
    }

    private double computeDistance(City cityA, City cityB) {
        return computeDistance(cityA.lat, cityA.lng, cityB.lat, cityB.lng);
    }

    private void testDistance(City cityA, City cityB, double expected,
                              double delta) {

        System.out.println();

        System.out.println(cityA.name + " - " + cityB.name + "  ex = "
                + expected);

        double dis = computeDistance(cityA, cityB);

        System.out.println("dis = " + dis);

    }

    public void test() {

        double delta = 200000;

        // 西安钟楼－－北京TAM广场 1105.7KM
        testDistance(City.BeiJing, City.XiAn, 1105700, delta);

        // 上海航空公司提供的数据,从上海到北京的飞行航程是1088公里
        testDistance(City.BeiJing, City.ShangHai, 1088000, delta);

        // 北京与拉萨直线实际距离为2550千米，在1:30000000的地图上
        testDistance(City.BeiJing, City.LaSa, 2550000, delta);

        // 西安到拉萨1764.585公里 这个数据是直线距离
        testDistance(City.XiAn, City.LaSa, 1764585, delta);

        // 理论是1200多公里
        testDistance(City.HaErBin, City.BeiJing, 1200000, delta);

        // 西安市中心到咸阳市中心30公里左右
        testDistance(City.XiAn, City.XianYang, 30000, delta);
        // ???
        // testDistance(City.HaErBin, City.LaSa, 0, delta);
    }

    // 北京市 北京市 北纬39.55 东经116.24
    // 陕西省 西安 北纬34.17 东经108.57
    // 上海市 上海市 北纬31.14 东经121.29
    // 西藏自治区 拉萨 北纬29.39 东经91.08
    // 黑龙江省 哈尔滨 北纬45.44 东经126.36
    // 陕西省 咸阳 北纬34.20 东经108.43
    private static class City {

        private static City BeiJing = new City("beijing", 39.55, 116.24);

        private static City XiAn = new City("xian", 34.47, 108.57);

        private static City XianYang = new City("xianyang", 34.20, 108.57);
//        private static City XianYang = new City("咸阳", 34.20, 108.43);

        private static City ShangHai = new City("shanghai", 31.14, 121.29);

        private static City LaSa = new City("lasa", 29.39, 91.08);

        private static City HaErBin = new City("haerbin", 45.44, 126.36);

        String name;
        double lat;
        double lng;

        public City(String name, double lat, double lng) {
            this.name = name;
            this.lat = lat;
            this.lng = lng;
        }

    }

    public static boolean isGpsInLocationRange(double lat, double lon, int validRangeInMeter, Location location) {
        boolean result = false;

        return result;
    }

    public static double convertMeter2DDD(Long meter) {
        double angleDDD = 0;
        double angleSeconds = meter / 30.9;
        log("angleSeconds", angleSeconds);
        angleDDD = angleSeconds/3600;
        log("angleDDD", angleDDD);

        return angleDDD;
    }

    public static GpsRange getGpsRange(double lat, double lon, Long radiusInMeter) {
        GpsRange range = new GpsRange();
        double offset = convertMeter2DDD(radiusInMeter);
        range.minLat = lat - offset;
        range.minLon = lon - offset;
        range.maxLat = lat + offset;
        range.maxLon = lon + offset;
        return range;
    }
    
    public static class GpsRange{
        public double minLat;
        public double minLon;
        public double maxLat;
        public double maxLon;

        @Override
        public String toString() {
            return "GpsRange{" +
                    "minLat=" + minLat +
                    ", minLon=" + minLon +
                    ", maxLat=" + maxLat +
                    ", maxLon=" + maxLon +
                    '}';
        }
    }

    public static void main(String[] args) {
        System.out.println(new GpsDistanceCalc().getGpsRange(34.20, 108.43, 30000L));
        new GpsDistanceCalc().test();
    }
}
