/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import db.DBConfig;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.apache.ibatis.session.SqlSession;
import utils.Constants;


public class GenericResources {
    protected SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    protected Gson gson = new Gson();
    @Context
    protected UriInfo uri;
    @Context
    protected HttpServletRequest httpRequest;
    protected String sys_language = "uk";

    public GenericResources() {
//        _logger.info("---1");
    }

    protected Map getBaseMap() {
        Map retval = new HashMap();
        if (httpRequest.getAttribute(Constants.KEY_TOKEN_ID) != null) {
            retval.put(Constants.KEY_TOKEN_ID, httpRequest.getAttribute(Constants.KEY_TOKEN_ID));
        }
        if (httpRequest.getAttribute(Constants.KEY_USERS_ID) != null) {
            retval.put(Constants.KEY_USERS_ID, httpRequest.getAttribute(Constants.KEY_USERS_ID));
        }
        if (httpRequest.getAttribute(Constants.KEY_ROLES_ID) != null) {
            retval.put(Constants.KEY_ROLES_ID, httpRequest.getAttribute(Constants.KEY_ROLES_ID));
        }
        if (httpRequest.getAttribute(Constants.KEY_BRANCH_ID) != null) {
            retval.put(Constants.KEY_BRANCH_ID, httpRequest.getAttribute(Constants.KEY_BRANCH_ID));
        }
        if (httpRequest.getAttribute(Constants.KEY_LEVEL_NUM) != null) {
            retval.put(Constants.KEY_LEVEL_NUM, httpRequest.getAttribute(Constants.KEY_LEVEL_NUM));
        }
        if (httpRequest.getHeader(Constants.KEY_LANGUAGE) != null) {
            sys_language = httpRequest.getHeader(Constants.KEY_LANGUAGE);
            retval.put(Constants.KEY_LANGUAGE, httpRequest.getHeader(Constants.KEY_LANGUAGE));
        } else {
            retval.put(Constants.KEY_LANGUAGE, sys_language);
        }
        return retval;
    }

    protected Map getParMap() {
        Map retval = getBaseMap();
        Set<String> keys = uri.getQueryParameters().keySet();
        for (String key : keys) {
            if (!uri.getQueryParameters().get(key).get(0).isEmpty()) {
                retval.put(key, uri.getQueryParameters().get(key).get(0));
            }
        }
        return retval;
    }

    protected Integer getActionID(String uri) {
        Integer retval = null;
        SqlSession sqlSession = DBConfig.getSqlSession();
        try {
            Map m = getBaseMap();
            m.put("uri", uri);
            retval = sqlSession.selectOne("selectActionID", m);
        } catch (Exception ex) {
//            _logger.error(ex);
        } finally {
            sqlSession.close();
        }
        return retval;
    }

    protected Integer getModuleID(String uri) {
        Integer retval = null;
        SqlSession sqlSession = DBConfig.getSqlSession();
        try {
            Map m = getBaseMap();
            m.put("uri", uri);
            retval = sqlSession.selectOne("selectModuleID", m);
        } catch (Exception ex) {
//            _logger.error(ex);
        } finally {
            sqlSession.close();
        }
        return retval;
    }

//    protected PlatBean getPlatOnlineBean(String tid) {
//        PlatBean retval = null;
//        SqlSession sqlSession = DBConfig.getSqlSession();
//        try {
//            Map m = getBaseMap();
//            m.put("tid", tid);
//            m.put("founded", 0);
//            retval = sqlSession.selectOne("selectPlatsOnline", m);
//        } catch (Exception ex) {
//            _logger.error(ex);
//        } finally {
//            sqlSession.close();
//        }
//        return retval;
//    }

    protected String getUserFIO(Integer id) {
        String retval = null;
        SqlSession sqlSession = DBConfig.getSqlSession();
        try {
            Map parMap = getBaseMap();
            parMap.put("id", id);
            retval = sqlSession.selectOne("selectUsersFIO", parMap);
        } catch (Exception ex) {
//            _logger.error(ex);
        } finally {
            sqlSession.close();
        }
        return retval;
    }

    protected String getString(String key) {
        try {
            Locale locale = new Locale(sys_language);
            ResourceBundle rb = ResourceBundle.getBundle("uz.jaesh.gasbalance.core.Application", locale);
            return rb.getString(key);
        } catch (Exception ex) {
//            _logger.error("key:" + key
//                    + " line:" + Thread.currentThread().getStackTrace()[2].getLineNumber()
//                    + " class:" + Thread.currentThread().getStackTrace()[2].getClassName()
//                    + " ex:" + ex);
        }
        return key;
    }

    protected String getPathCacheFolder() {
        Calendar now = Calendar.getInstance();
        return Constants.PATH_CACHES + now.get(Calendar.YEAR)
                + "/" + (now.get(Calendar.MONTH) + 1)
                + "/" + now.get(Calendar.DAY_OF_MONTH)
                + "/" + httpRequest.getPathInfo();
    }

    protected String getPathLogFolder() {
        Calendar now = Calendar.getInstance();
        return Constants.PATH_LOGS + httpRequest.getPathInfo();
    }

    protected String getFileData(String filePath, String fileName) {
        String data = "";
        try {
            File file = new File(filePath + "/" + fileName + ".json");
            if (file.exists()) {
                Scanner myReader = new Scanner(file);
                try {
                    while (myReader.hasNextLine()) {
                        data += myReader.nextLine();
                    }
                } catch (Exception ex) {
//                    _logger.error(ex);
                } finally {
                    myReader.close();
                }
            }
        } catch (Exception ex) {
//            _logger.error(ex);
        }
        return data;
    }

    protected void writeFileData(String filePath, String fileName, JsonObject json) {
        try {
            if (!new File(filePath).exists()) {
                new File(filePath).mkdirs();
            }
            FileOutputStream outputStream = new FileOutputStream(filePath + "/" + fileName + ".json");
            try {
                outputStream.write(gson.toJson(json).getBytes());
            } catch (Exception ex) {
//                _logger.error(ex);
            } finally {
                outputStream.close();
            }
        } catch (Exception ex) {
//            _logger.error(ex);
        }
    }

    protected Integer getCountData(String sqlCnt, Map map) {
        Integer retval = null;
        SqlSession sqlSession = DBConfig.getSqlSession();
        try {
            retval = sqlSession.selectOne(sqlCnt, map);
        } catch (Exception ex) {
//            _logger.error(ex);
        } finally {
            sqlSession.close();
        }
        return retval;
    }

    protected String getObjectName(String sqlQuery, Map map) {
        String retval = null;
        SqlSession sqlSession = DBConfig.getSqlSession();
        try {
            retval = sqlSession.selectOne(sqlQuery, map);
            
        } catch (Exception ex) {
//            _logger.error(ex);
        } finally {
            sqlSession.close();
        }
        return retval;
    }
}
