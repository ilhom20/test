/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import beans.User;
import db.DBConfig;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Ilhomjon
 */
public class UserService {
    
    public static String register(User users){
        SqlSession sqlSession = DBConfig.getSqlSession();
        
        Map map = new HashMap();
        
        map.put("name", users.getName());
        map.put("username", users.getUsername());
        map.put("password", users.getPassword());
                
        try{
            sqlSession.insert("insertUser", map);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            sqlSession.close();
        }
        
        return "user successfully saved";
    }

    
}
