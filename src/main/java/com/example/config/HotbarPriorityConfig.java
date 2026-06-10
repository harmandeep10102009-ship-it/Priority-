package com.example.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "hotbarpriority")
public class HotbarPriorityConfig implements ConfigData {

    public int slot0Priority = 1;
    public int slot1Priority = 2;
    public int slot2Priority = 3;
    public int slot3Priority = 4;
    public int slot4Priority = 5;
    public int slot5Priority = 6;
    public int slot6Priority = 7;
    public int slot7Priority = 8;
    public int slot8Priority = 9;

    public int[] getSlotPriorities() {
        return new int[]{
            slot0Priority, slot1Priority, slot2Priority,
            slot3Priority, slot4Priority, slot5Priority,
            slot6Priority, slot7Priority, slot8Priority
        };
    }

    public int getSlotPriority(int index) {
        switch (index) {
            case 0: return slot0Priority;
            case 1: return slot1Priority;
            case 2: return slot2Priority;
            case 3: return slot3Priority;
            case 4: return slot4Priority;
            case 5: return slot5Priority;
            case 6: return slot6Priority;
            case 7: return slot7Priority;
            case 8: return slot8Priority;
            default: throw new IllegalArgumentException("Invalid slot index: " + index);
        }
    }

    public void setSlotPriority(int index, int value) {
        switch (index) {
            case 0: slot0Priority = value; break;
            case 1: slot1Priority = value; break;
            case 2: slot2Priority = value; break;
            case 3: slot3Priority = value; break;
            case 4: slot4Priority = value; break;
            case 5: slot5Priority = value; break;
            case 6: slot6Priority = value; break;
            case 7: slot7Priority = value; break;
            case 8: slot8Priority = value; break;
            default: throw new IllegalArgumentException("Invalid slot index: " + index);
        }
    }

    public static int getDefaultPriority(int index) {
        return index + 1;
    }

    @Override
    public void validatePostLoad() throws ValidationException {
        int[] priorities = getSlotPriorities();
        boolean[] seen = new boolean[10];
        for (int priority : priorities) {
            if (priority < 1 || priority > 9) {
                throw new ValidationException("Priorities must be between 1 and 9.");
            }
            if (seen[priority]) {
                throw new ValidationException("Each priority value must be unique.");
            }
            seen[priority] = true;
        }
    }
}
