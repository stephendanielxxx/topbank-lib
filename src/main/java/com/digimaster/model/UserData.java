package com.digimaster.model;

public class UserData {
    private int userId;
    private int roleId;
    private int instituteId;

    public UserData(int userId, int roleId, int instituteId) {
        this.userId = userId;
        this.roleId = roleId;
        this.instituteId = instituteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(int instituteId) {
        this.instituteId = instituteId;
    }
}
