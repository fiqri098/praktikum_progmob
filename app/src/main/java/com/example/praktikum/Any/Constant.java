package com.example.praktikum.Any;

public class Constant {
    public static final String URL = "http://pacar.pemirahimanikaunud.web.id";
    public static final String HOME = URL+"/api";
    public static final String LOGIN = HOME+"/login";
    public static final String REGISTER = HOME+"/signup";
    public static final String LOGOUT = HOME+"/logout";
    public static final String GET_USER = HOME+"/userRead";
    public static final String SET_USER = HOME+"/userEdit";
    public static final String UPDATE_PASSWORD = HOME+"/userEditPass";

    public static final String REGISTER_SAKIT = HOME+"/regisSakit";
    public static final String GET_REGIS_SAKIT = HOME+"/getRegisSakitPending";
    public static final String GET_RESPONED_REGIS = HOME+"/getRegisSakitDirespon";
    public static final String SET_REGIS_SAKIT = HOME+"/editRegisSakit";
    public static final String DELETE_REGIS_SAKIT = HOME+"/deleteRegisSakit";

    //ADMIN CONSTANT
    public static final String GET_REGIS_MASUK = HOME+"/getRegisSakitMasuk";
    public static final String REJECT_REGIS_MASUK = HOME+"/rejectRegisSakitMasuk";
    public static final String ACCEPT_REGIS_MASUK = HOME+"/acceptRegisSakitMasuk";
    public static final String GET_REGIS_DIRESPON = HOME+"/getRegisSakitDiresponAdmin";
    public static final String GET_ALL_ADMIN = HOME+"/getAllAdmin";
    public static final String GET_ALL_USER = HOME+"/getAllUser";
    public static final String GET_DETAIL_USER = HOME+"/getUserDetail";


}
