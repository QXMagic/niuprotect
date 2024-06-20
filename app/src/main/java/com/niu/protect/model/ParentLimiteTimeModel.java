package com.niu.protect.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
public class ParentLimiteTimeModel extends BaseModel {
    @SerializedName("data")
    private DataDTO data;

    public DataDTO getData() {
        return this.data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("patternParentSlots")
        private List<PatternParentSlotsDTO> patternParentSlots;
        @SerializedName("studentId")
        private String studentId;
        @SerializedName("type")
        private int type;

        public List<PatternParentSlotsDTO> getPatternParentSlots() {
            return this.patternParentSlots;
        }

        public void setPatternParentSlots(List<PatternParentSlotsDTO> patternParentSlots) {
            this.patternParentSlots = patternParentSlots;
        }

        public String getStudentId() {
            return this.studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public static class PatternParentSlotsDTO {
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
