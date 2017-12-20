package com.bunny.groovy.utils;

import java.util.Date;
import java.util.List;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/20.
 ****************************************/

public class DateDescriptor {
    private List<Month> mMonthList;

    public class Month {
        private List<Day> mDayList;

        public List<Day> getDayList() {
            return mDayList;
        }

        public void setDayList(List<Day> dayList) {
            mDayList = dayList;
        }
    }

    public class Day {
        private Date mDate;
        private String str = "%s.%s";

        public Date getDate() {
            return mDate;
        }

        public void setDate(Date date) {
            mDate = date;
        }

        /**
         * @return 周几,日期
         */
        public String getWeekDay() {
            String[] split = mDate.toString().split(" ");
            if (split.length == 6)
                return String.format(str, split[0], split[2]);
            return mDate.toString();
        }
    }


}
