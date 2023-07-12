package com.flexone.catchwiseserver.bootstrap.json.PublicLakesJSON;

import lombok.Data;

@Data
public class PublicLakesPropertiesJSON {

    private int gnis_id;
    private int lksdb_basi;
    private String dowlknum;
    private String gnis_name;
    private String pw_basin_n;
    private String pw_parent_;
    private String pw_sub_nam;
    private String sub_flag;
    private int wettype;
    private String pwi_class;
    private String pwi_label;
    private double acres;
    private double shore_mi;
    private String has_flag;
    private String flag_type;
    private String dow_main;
    private double SHAPE_Leng;
    private double SHAPE_Area;
}


/*
"properties": {
    "gnis_id": 648367,
    "lksdb_basi": 15490,
    "dowlknum": "45000200",
    "gnis_name": "Mud River Pool",
    "pw_basin_n": "Mud",
    "pw_parent_": "Mud",
    "pw_sub_nam": null,
    "sub_flag": "N",
    "wettype": 4,
    "pwi_class": "P",
    "pwi_label": "Public Water Basin",
    "acres": 38386.304722599998,
    "shore_mi": 193.17032299,
    "has_flag": "Y",
    "flag_type": "PRI, SHL",
    "dow_main": "45000200",
    "SHAPE_Leng": 310878.12203500001,
    "SHAPE_Area": 155343863.79899999
}
  */