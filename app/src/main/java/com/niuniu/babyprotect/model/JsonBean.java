package com.niuniu.babyprotect.model;

import com.contrarywind.interfaces.IPickerViewData;
import java.util.List;
public class JsonBean implements IPickerViewData {
    private List<CityBean> city;
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCityList() {
        return this.city;
    }

    public void setCityList(List<CityBean> city) {
        this.city = city;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }

    public static class CityBean {
        private List<String> area;
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getArea() {
            return this.area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }
}
