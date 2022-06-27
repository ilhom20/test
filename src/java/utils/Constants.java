/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


public class Constants {

    public static final String PATH_TEMPLATES = "/nfs/hgtbalance/templates/";
    public static final String PATH_DOCUMENTS = "/nfs/hgtbalance/documents/";
    public static final String PATH_CACHES = "/nfs/hgtbalance/caches/";
    public static final String PATH_LOGS = "/nfs/hgtbalance/logs/";
    public static final String PATH_ASSETS = "https://askugyur.hududgaz.uz:81/assets/";
    public static final String PATH_QR = "https://askugyur.hududgaz.uz:81/assets/photos/qr/QR.png";
    //
    public static final String KEY_BARER_TOKEN = "access_token";
    public static final String KEY_BASIC_TOKEN = "refresh_token";
    //
    public static final String KEY_LANGUAGE = "sys_language";
    public static final String KEY_TOKEN_ID = "sys_token_id";
    public static final String KEY_USERS_ID = "sys_users_id";
    public static final String KEY_ROLES_ID = "sys_roles_id";
    public static final String KEY_BRANCH_ID = "sys_branch_id";
    public static final String KEY_LEVEL_NUM = "sys_level_num";
    //
    public static final Integer EXPIRE_BASIC_TOKEN = 1000 * 60 * 30 * 01;
    public static final Integer EXPIRE_BARER_TOKEN = 1000 * 60 * 05 * 01;
    public static final int EXPIRE_CASHE_MINUT = 30;
    public static final int EXPIRE_CASHE_HOUR = 12;
    //
    public static final String PER_BASE_URL = "http://192.168.222.13:81/api";
//    public static final String PER_BASE_URL = "http://127.0.0.1:8084/HGTBalancePeriod/api";
    public static final String PER_ACCESS_KEY = "access";
    public static final String PER_ACCESS_VAL = "123";
    //
    public static final String EXC_BASE_URL = "http://192.168.222.13:82/api";
//    public static final String EXC_BASE_URL = "http://127.0.0.1:8084/HGTBalanceExcel/api";
    public static final String EXC_ACCESS_KEY = "access";
    public static final String EXC_ACCESS_VAL = "123";
    //
    public static final String INV_BASE_URL = "http://192.168.222.13:83/api";
//    public static final String INV_BASE_URL = "http://127.0.0.1:8084/HGTBalanceInvoice/api";
    public static final String INV_ACCESS_KEY = "access";
    public static final String INV_ACCESS_VAL = "123";

    public static final String TAX_BASE_URL = "http://192.168.222.13:8090/api";
//    public static final String TAX_BASE_URL = "http://127.0.0.1:8084/HGTBalanceTax/api";
    public static final String TAX_ACCESS_KEY = "access";
    public static final String TAX_ACCESS_VAL = "123";
    //
    public static final Integer TYP_FOIZ_30 = 1;
    public static final Integer TYP_FOIZ_100 = 2;
    //
    public static final String PATH_MG_FILE_OFFER = "/srv/askug-yur/webapps/ROOT/mgfiles/offers";
//    public static final String PATH_MG_FILE_OFFER = "D:\\Projects\\Netbeans\\UTG\\Service REST\\HGTBalance\\web\\mgfiles\\offers";
    public static final String PATH_MG_FILE_CONDITION = "/srv/askug-yur/webapps/ROOT/mgfiles/conditions";
//    public static final String PATH_MG_FILE_CONDITION = "D:\\Projects\\Netbeans\\UTG\\Service REST\\HGTBalance\\web\\mgfiles\\conditions";
}

