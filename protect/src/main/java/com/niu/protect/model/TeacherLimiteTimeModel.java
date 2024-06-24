package com.niu.protect.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
public class TeacherLimiteTimeModel extends BaseModel {
    @SerializedName("data")
    private DataDTO data;

    public DataDTO getData() {
        return this.data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("patternTeacherSlots")
        private List<PatternTeacherSlotsDTO> patternTeacherSlots;

        public List<PatternTeacherSlotsDTO> getPatternTeacherSlots() {
            return this.patternTeacherSlots;
        }

        public void setPatternTeacherSlots(List<PatternTeacherSlotsDTO> patternTeacherSlots) {
            this.patternTeacherSlots = patternTeacherSlots;
        }

        public static class PatternTeacherSlotsDTO {
            @SerializedName("dayTheWeek")
            private int dayTheWeek;
            @SerializedName("patternTimeScopes")
            private List<PatternTimeScopesDTO> patternTimeScopes;

            public int getDayTheWeek() {
                return this.dayTheWeek;
            }

            public void setDayTheWeek(int dayTheWeek) {
                this.dayTheWeek = dayTheWeek;
            }

            public List<PatternTimeScopesDTO> getPatternTimeScopes() {
                return this.patternTimeScopes;
            }

            public void setPatternTimeScopes(List<PatternTimeScopesDTO> patternTimeScopes) {
                this.patternTimeScopes = patternTimeScopes;
            }
        }
    }
}
