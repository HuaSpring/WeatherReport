package com.hua.weather.bean;

/**
 * Create By Spring 2021/5/29 09:09
 */
public class GeoCityBean {

    /**
     * status : OK
     * result : {"location":{"lng":112.601253,"lat":24.343221},"formatted_address":"广东省清远市阳山县s114","business":"","addressComponent":{"city":"清远市","direction":"","distance":"","district":"阳山县","province":"广东省","street":"s114","street_number":""},"cityCode":197}
     */

    private String status;
    /**
     * location : {"lng":112.601253,"lat":24.343221}
     * formatted_address : 广东省清远市阳山县s114
     * business :
     * addressComponent : {"city":"清远市","direction":"","distance":"","district":"阳山县","province":"广东省","street":"s114","street_number":""}
     * cityCode : 197
     */

    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * lng : 112.601253
         * lat : 24.343221
         */

        private LocationBean location;
        private String formatted_address;
        private String business;
        /**
         * city : 清远市
         * direction :
         * distance :
         * district : 阳山县
         * province : 广东省
         * street : s114
         * street_number :
         */

        private AddressComponentBean addressComponent;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public AddressComponentBean getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponentBean addressComponent) {
            this.addressComponent = addressComponent;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "formatted_address='" + formatted_address + '\'' +
                    ", business='" + business + '\'' +
                    ", addressComponent=" + addressComponent +
                    '}';
        }

        public static class LocationBean {
            private double lng;
            private double lat;

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }

        public static class AddressComponentBean {
            private String city;
            private String direction;
            private String distance;
            private String district;
            private String province;
            private String street;
            private String street_number;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getStreet_number() {
                return street_number;
            }

            public void setStreet_number(String street_number) {
                this.street_number = street_number;
            }

            @Override
            public String toString() {
                return "AddressComponentBean{" +
                        "city='" + city + '\'' +
                        ", direction='" + direction + '\'' +
                        ", distance='" + distance + '\'' +
                        ", district='" + district + '\'' +
                        ", province='" + province + '\'' +
                        ", street='" + street + '\'' +
                        ", street_number='" + street_number + '\'' +
                        '}';
            }
        }
    }
}

