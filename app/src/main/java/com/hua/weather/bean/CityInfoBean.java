package com.hua.weather.bean;

import java.util.List;

/**
 * Author Spring.  2021/05/28 11:53
 */
public class CityInfoBean {


    /**
     * province : 北京
     * city : [{"cityName":"北京","cityCode":"101010100"},{"cityName":"朝阳","cityCode":"101010300"},{"cityName":"顺义","cityCode":"101010400"},{"cityName":"怀柔","cityCode":"101010500"},{"cityName":"通州","cityCode":"101010600"},{"cityName":"昌平","cityCode":"101010700"},{"cityName":"延庆","cityCode":"101010800"},{"cityName":"丰台","cityCode":"101010900"},{"cityName":"石景山","cityCode":"101011000"},{"cityName":"大兴","cityCode":"101011100"},{"cityName":"房山","cityCode":"101011200"},{"cityName":"密云","cityCode":"101011300"},{"cityName":"门头沟","cityCode":"101011400"},{"cityName":"平谷","cityCode":"101011500"},{"cityName":"八达岭","cityCode":"101011600"},{"cityName":"佛爷顶","cityCode":"101011700"},{"cityName":"汤河口","cityCode":"101011800"},{"cityName":"密云上甸子","cityCode":"101011900"},{"cityName":"斋堂","cityCode":"101012000"},{"cityName":"霞云岭","cityCode":"101012100"},{"cityName":"北京城区","cityCode":"101012200"},{"cityName":"海淀","cityCode":"101010200"}]
     */

    private List<ProvincesBean> provinces;

    public List<ProvincesBean> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<ProvincesBean> provinces) {
        this.provinces = provinces;
    }

    public static class ProvincesBean {
        private String province;
        /**
         * cityName : 北京
         * cityCode : 101010100
         */

        private List<CityBean> city;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean {
            private String cityName;
            private String cityCode;

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode;
            }
        }
    }
}
