package com.bunny.groovy.model;

import java.util.List;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/14.
 ****************************************/

public class GoogleMapLoc {

    /**
     * results : [{"address_components":[{"long_name":"477150","short_name":"477150","types":["postal_code"]},{"long_name":"郸城县","short_name":"郸城县","types":["political","sublocality","sublocality_level_1"]},{"long_name":"周口市","short_name":"周口市","types":["locality","political"]},{"long_name":"河南省","short_name":"河南省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}],"formatted_address":"中国河南省周口市郸城县 邮政编码: 477150","geometry":{"location":{"lat":33.64478160000001,"lng":115.1771625},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":33.64613058029151,"lng":115.1785114802915},"southwest":{"lat":33.64343261970851,"lng":115.1758135197085}}},"place_id":"ChIJheK4KRi80TURtKvac5o0-F8","types":["postal_code"]}]
     * status : OK
     * "error_message" : "Requests to this API must be over SSL. Load the API with \"https://\" instead of \"http://\".",
     */
    private String error_message;
    private String status;
    private List<ResultsBean> results;

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * address_components : [{"long_name":"477150","short_name":"477150","types":["postal_code"]},{"long_name":"郸城县","short_name":"郸城县","types":["political","sublocality","sublocality_level_1"]},{"long_name":"周口市","short_name":"周口市","types":["locality","political"]},{"long_name":"河南省","short_name":"河南省","types":["administrative_area_level_1","political"]},{"long_name":"中国","short_name":"CN","types":["country","political"]}]
         * formatted_address : 中国河南省周口市郸城县 邮政编码: 477150
         * geometry : {"location":{"lat":33.64478160000001,"lng":115.1771625},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":33.64613058029151,"lng":115.1785114802915},"southwest":{"lat":33.64343261970851,"lng":115.1758135197085}}}
         * place_id : ChIJheK4KRi80TURtKvac5o0-F8
         * types : ["postal_code"]
         */

        private String formatted_address;
        private GeometryBean geometry;
        private String place_id;
        private List<AddressComponentsBean> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<AddressComponentsBean> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponentsBean> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class GeometryBean {
            /**
             * location : {"lat":33.64478160000001,"lng":115.1771625}
             * location_type : APPROXIMATE
             * viewport : {"northeast":{"lat":33.64613058029151,"lng":115.1785114802915},"southwest":{"lat":33.64343261970851,"lng":115.1758135197085}}
             */

            private LocationBean location;
            private String location_type;
            private ViewportBean viewport;

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public ViewportBean getViewport() {
                return viewport;
            }

            public void setViewport(ViewportBean viewport) {
                this.viewport = viewport;
            }

            public static class LocationBean {
                /**
                 * lat : 33.64478160000001
                 * lng : 115.1771625
                 */

                private String lat;
                private String lng;

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLng() {
                    return lng;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }
            }

            public static class ViewportBean {
                /**
                 * northeast : {"lat":33.64613058029151,"lng":115.1785114802915}
                 * southwest : {"lat":33.64343261970851,"lng":115.1758135197085}
                 */

                private NortheastBean northeast;
                private SouthwestBean southwest;

                public NortheastBean getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBean northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBean getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBean southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBean {
                    /**
                     * lat : 33.64613058029151
                     * lng : 115.1785114802915
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBean {
                    /**
                     * lat : 33.64343261970851
                     * lng : 115.1758135197085
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class AddressComponentsBean {
            /**
             * long_name : 477150
             * short_name : 477150
             * types : ["postal_code"]
             */

            private String long_name;
            private String short_name;
            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }
    }
}
