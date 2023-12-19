package com.example.agodadiksha.interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class User implements Comparable <User> {
    String level;
    int count;
    public User(String level, int count) {
        this.level = level;
        this.count = count;
    }

    public String getLevel() {
        return level;
    }
    public int getCount() {
        return count;
    }
    public void incrementCount(int count) {
        this.count += count;
    }

    @Override
    public int compareTo(User o) {
       if (o.getCount() == this.count) {
         return o.level.compareTo(this.level);
       } else {
        return o.count - this.count;
       }
    }

    @Override
    public String toString() {
        String actualLevel = this.level.split("_")[1];
        return actualLevel + " - " + this.count;
    }

    
}

public class Question2 {
    public String[] solution(int[] points) {
        List<String> result = new ArrayList<>();
        List<String> levels = new ArrayList<>();
        levels.add("1_Recruit");
        levels.add("2_Soldier");
        levels.add("3_Warrior");
        levels.add("4_Captain");
        levels.add("5_Ninja");


        List<User> users = new ArrayList<>();
        for(int i =0;i<5;i++) {
            User user = new User(levels.get(i), 0);
            users.add(user);
        }

        for(int i = 0;i<points.length;i++) {
            int level = getPointsLevel(points[i]);
            users.get(level).incrementCount(1);
        }

        Collections.sort(users);
        for(int i =0;i<users.size(); i++) {
            if(users.get(i).getCount()!= 0)  {
                result.add(users.get(i).toString());
            }
        }
        String[] resultArray = result.toArray(new String[result.size()]);
        return resultArray;

    }

    private int getPointsLevel(int points) {
        if (points >=0 && points <= 999) {
            return 0;
        } else if (points >=1000 && points <= 4999) {
            return 1;
        } else if (points >=5000 && points <= 9999) {
            return 2;
        } else if (points >=10000 && points <= 49999) {
            return 3;
        } else if (points >=50000) {
            return 4;
        } else {
            return -1;
        }
    }
}
