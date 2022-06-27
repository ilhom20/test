 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import beans.Book;
import com.google.gson.JsonObject;
import db.DBConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.ibatis.session.SqlSession;
import service.BookService;
import utils.JSONWorker;

/**
 *
 * @author Ilhomjon
 */

@Path("book")
public class BookResources extends GenericResources{
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    @Path("create/{name}")
    @Produces("application/json")
    public String bookCreate(@PathParam("name") String name){
//        return Response.status(Response.Status.OK).entity("Kel " + name + " , kel kelovir.").build();
        return name;
    }
    
    @POST
    @Path("add")
    @Produces("application/json")
    public Response bookAdd(String argument){
        Boolean exception = false;
        JsonObject parJson = JSONWorker.getJsonObj(argument);
        if (parJson == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("not json format").build();
        }
        Map baseMap = getBaseMap();
        try{
            baseMap.put("name", parJson.get("name").getAsString());
            baseMap.put("author", parJson.get("author").getAsString());
            baseMap.put("price", parJson.get("price").getAsDouble());
        }catch(Exception e){
            exception = true;
        }
        if (exception) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Parameterlar topilmadi").build();
        }
        Integer action_id = getActionID("BOOK_ADD");
        SqlSession sqlSession = DBConfig.getSqlSession();
        if (action_id == null) {
            return Response.status(Response.Status.FORBIDDEN).entity("Huquqingiz yetarli emas").build();
        }
       
        try{
            sqlSession.insert("add", baseMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            sqlSession.close();
        }
        return Response.status(Response.Status.CREATED).entity("Successfully book saved").build();
    }
    
    @GET
    @Path("get/{id}")
    @Produces("application/json")
    public Response get(@PathParam("id") int id){
        Book book = new Book();
        Boolean exception = false;
        
        if (id < 0) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Parameterlar ishonchli emas").build();
        }
        Integer action_id = getActionID("BOOK_GET");
        SqlSession sqlSession = DBConfig.getSqlSession();
        if (action_id == null) {
            return Response.status(Response.Status.FORBIDDEN).entity("Huquqingiz yetarli emas").build();
        }
        try{
             book = sqlSession.selectOne("getById", id);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            sqlSession.close();
        }
        return Response.status(Response.Status.OK).entity(book).build();
    } 
    
    @GET
    @Path("get")
    @Produces("application/json")
    public Response getList(){
        List<Book> selectList = new ArrayList();
        Integer action_id = getActionID("BOOK_GET_LIST");
        SqlSession sqlSession = DBConfig.getSqlSession();
        if (action_id == null) {
            return Response.status(Response.Status.FORBIDDEN).entity("Huquqingiz yetarli emas").build();
        }
        try{
            selectList = sqlSession.selectList("getList");
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            sqlSession.close();
        }
        return Response.status(Response.Status.OK).entity(selectList).build();
    }
    
    @DELETE
    @Path("delete/{id}")
    @Produces("application/json")
    public Response delete(@PathParam("id") int id){
        if (id < 0) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Parameterlar ishonchli emas").build();
        }
        Integer action_id = getActionID("BOOK_DELETE");
        if (action_id == null) {
            return Response.status(Response.Status.FORBIDDEN).entity("Huquqingiz yetarli emas").build();
        }
        SqlSession sqlSession = DBConfig.getSqlSession();
        try{
            sqlSession.delete("deleteById", id);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            sqlSession.close();
        }
        
        return Response.status(Response.Status.OK).entity("Book successfully deleted").build();
    }
    
    @PUT
    @Path("update/{id}")
    @Produces("application/json")
    public Response update(@PathParam("id") int id, Book book){
        if (id < 0) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Id topilmadi").build();
        }
        if (book.getName().equals(null) || book.getAuthor().equals(null) || book.getPrice() < 0){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Paramaterlar to'liq emas").build();
        }
        Integer action_id = getActionID("BOOK_UPDATE");
        SqlSession sqlSession = DBConfig.getSqlSession();
        if (action_id == null) {
            return Response.status(Response.Status.FORBIDDEN).entity("Huquqingiz yetarli emas").build();
        }
        Book getByIdBook = null;
        try{
            getByIdBook = sqlSession.selectOne("getById", id);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        if(getByIdBook == null){
            sqlSession.close();
            return Response.status(Response.Status.NOT_FOUND).entity("Book is not found").build();
        }
        
        book.setId(id);
        
        try{
            sqlSession.update("updateBook", book);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            sqlSession.close();
        }
        return Response.status(Response.Status.OK).entity("Book successfully updated").build();
    }
    
    
}
