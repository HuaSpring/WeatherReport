package com.hua.weather.bean;

/**
 * Create By Spring 2021/5/28 20:22
 */
public class WeatherInfo {

    /**
     * city : 北京
     * cityid : 101010100
     * temp1 : 3℃
     * temp2 : -8℃
     * weather : 晴
     * img1 : d0.gif
     * img2 : n0.gif
     * ptime : 11:00
     */

    private WeatherinfoBean weatherinfo;

    public WeatherinfoBean getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(WeatherinfoBean weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public static class WeatherinfoBean {
        private String city;
        private String cityid;
        private String temp1;
        private String temp2;
        private String weather;
        private String img1;
        private String img2;
        private String ptime;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getTemp1() {
            return temp1;
        }

        public void setTemp1(String temp1) {
            this.temp1 = temp1;
        }

        public String getTemp2() {
            return temp2;
        }

        public void setTemp2(String temp2) {
            this.temp2 = temp2;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public String getImg2() {
            return img2;
        }

        public void setImg2(String img2) {
            this.img2 = img2;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        @Override
        public String toString() {
            return
                    city + '\'' + "," + "\r\n" +
                    " cityid='" + cityid + '\'' +  "," + "\r\n" +
                    " temp1='" + temp1 + '\'' +  "," + "\r\n" +
                    " temp2='" + temp2 + '\'' +  "," + "\r\n" +
                    " weather='" + weather + '\'' +  "," + "\r\n" +
                    " ptime='" + ptime + '\'' +  "," + "\r\n"
                    ;
        }
    }
}
