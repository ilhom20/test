/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

//import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author Ilhomjon
 */
public class DBConfig {
    
    private static SqlSessionFactory sqlSessionFactory;
    
    public static SqlSession getSqlSession() {
        if (sqlSessionFactory == null) {
            try {
                
                Reader fileReader = Resources.getResourceAsReader("property/postgres.properties");
                Reader confReader = Resources.getResourceAsReader("db/Config.xml");
                Properties fileProps = new Properties();
                fileProps.load(fileReader);
                
                
                
                Properties confProps = new Properties();
                confProps.put("jdbc.uri", fileProps.get("jdbc.uri"));
                confProps.put("jdbc.usr", (fileProps.get("jdbc.usr").toString()));
                confProps.put("jdbc.psw", (fileProps.get("jdbc.psw").toString()));
                
                sqlSessionFactory = (new SqlSessionFactoryBuilder()).build(confReader, confProps);
                
            } catch (Exception ex) {
               
                throw new RuntimeException(ex.getCause());
            }
        }
        return sqlSessionFactory.openSession(true);
    }
    
//    private static SqlSessionFactory sqlSessionFactory;
//
//    private static SqlSessionFactory getSqlSessionFactory() {
//        if (sqlSessionFactory == null) {
//            InputStream inputStream = null;
//            try {
//                inputStream = Resources.getResourceAsStream("/db/OracleConfig.xml");
//                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//            } catch (Exception ex) {
////                _logger.error(ex.getMessage(), ex);
//                throw new RuntimeException(ex.getCause());
//            } finally {
//                if (inputStream != null) {
//                    try {
//                        inputStream.close();
//                    } catch (Exception ex) {
////                        _logger.error(ex.getMessage(), ex);
//                    }
//                }
//            }
//        }
//        return sqlSessionFactory;
//    }
//
//    public static SqlSession getSqlSession() {
//        return getSqlSessionFactory().openSession(true);
//    }
//
//    public static SqlSession getSqlSessionTransaction() {
//        return getSqlSessionFactory().openSession(false);
//    }
//
//    public static <T> T getSqlMapper(Class<T> clazz) throws Exception {
//        SqlSession sqlSession = getSqlSession();
//        if (sqlSession == null)
//            throw new NullPointerException("SqlSession is NULL!");
//        T mapper = sqlSession.getMapper(clazz);
//        if (mapper == null)
//            throw new NullPointerException("SqlSession is NULL!");
//        return mapper;   
//    }
}
