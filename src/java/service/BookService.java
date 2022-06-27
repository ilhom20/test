/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import beans.Book;
import db.DBConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Ilhomjon
 */
public class BookService {
    
    public static void add(Book book){
        SqlSession sqlSession = DBConfig.getSqlSession();
        
        Map map = new HashMap();
        
        map.put("name", book.getName());
        map.put("author", book.getAuthor());
        map.put("price", book.getPrice());
                
        try{
            sqlSession.insert("add", map);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            sqlSession.close();
        }
    }
    
    public static Book getBookById(int id){
         SqlSession sqlSession = DBConfig.getSqlSession();
        
        try{
             Book book = sqlSession.selectOne("getById", id);
             return book;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            sqlSession.close();
        }
        return null;
    }
    
    public static List<Book> getList(){
        SqlSession sqlSession = DBConfig.getSqlSession();
        
        try{
            List<Book> selectList = sqlSession.selectList("getList");
            return selectList; 
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            sqlSession.close();
        }
        
        return null;
    }
    
    public static boolean delete(int id){
         SqlSession sqlSession = DBConfig.getSqlSession();
        
        try{
            int delete = sqlSession.delete("deleteById", id);
            return delete == 1; 
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            sqlSession.close();
        }
        
        return false;
    }
    
    public static String update(int id, Book book){
        SqlSession sqlSession = DBConfig.getSqlSession();
        Book getByIdBook = null;
        try{
            getByIdBook = sqlSession.selectOne("getById", id);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        if(getByIdBook == null){
            sqlSession.close();
            return "Book is not found";
        }
        
        book.setId(id);
        
        try{
            sqlSession.update("updateBook", book);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            sqlSession.close();
        }
        return "Successfully updated";
    }
}
