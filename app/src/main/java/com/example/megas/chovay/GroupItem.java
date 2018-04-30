package com.example.megas.chovay;

public class GroupItem {
    private long groupID;
    private String groupName;

    public long getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupItem(long groupID, String groupName) {

        this.groupID = groupID;
        this.groupName = groupName;
    }
}
