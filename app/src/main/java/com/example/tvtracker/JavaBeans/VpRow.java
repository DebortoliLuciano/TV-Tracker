package com.example.tvtracker.JavaBeans;

/**
 * @author Saad Amjad
 * @date 2020/04/03
 */

public class VpRow {

    private String vpTitle;
    private String vpBody;

    public VpRow(String vpTitle, String vpBody) {
        this.vpTitle = vpTitle;
        this.vpBody = vpBody;
    }

    public String getVpTitle() {
        return vpTitle;
    }

    public void setVpTitle(String vpTitle) {
        this.vpTitle = vpTitle;
    }

    public String getVpBody() {
        return vpBody;
    }

    public void setVpBody(String vpBody) {
        this.vpBody = vpBody;
    }
}
