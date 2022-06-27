/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import beans.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import db.DBConfig;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.apache.ibatis.session.SqlSession;
import service.UserService;
import utils.Constants;
import utils.JSONWorker;
import utils.Tokenizer;

/**
 *
 * @author Ilhomjon
 */

@Path("user")
public class UserResources extends GenericResources{
    
    @POST
    @Path("/register")
    @Produces("application/json")
    public String signup(User user){
        
        Map parMap = getBaseMap();
        String register = UserService.register(user);
        return register;
    }
    
    @POST
    @Path("/login")
    @Produces("application/json")
    @Consumes("application/json")
    public Response login(String argument){
        boolean exception = false;
        JsonObject retval = new JsonObject();
        Gson gson = new Gson();
        JsonObject parJson = JSONWorker.getJsonObj(argument);
        
        if (parJson == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("not json format").build();
        }
        if (!parJson.has("username")) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("username not found").build();
        }
        if (!parJson.has("password")) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("password not found").build();
        }
        
        User user = null;
        SqlSession sqlSession = DBConfig.getSqlSession();
        try {
            Map m = new HashMap();
            m.put("username", parJson.get("username").getAsString());
            user = sqlSession.selectOne("selectUsersSimple", m);
        } catch (Exception ex) {
            exception = true;
        } finally {
            sqlSession.close();
        }
        if (exception) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Сервер билан алоқа йўқ").build();
        }
        if (user == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Логин ёки парол нотўғри").build();
        }
        
        String token_id = Tokenizer.getSign();

        JsonObject json = new JsonObject();
        json.addProperty(Constants.KEY_USERS_ID, user.getId());
        json.addProperty(Constants.KEY_ROLES_ID, user.getRole_id());

        retval.addProperty(Constants.KEY_BASIC_TOKEN, Tokenizer.getBasicToken(token_id, gson.toJson(json)));
        retval.addProperty(Constants.KEY_BARER_TOKEN, Tokenizer.getBarerToken(token_id, gson.toJson(json)));

        return Response.status(Response.Status.OK).entity(gson.toJson(retval)).build();
    }

}
