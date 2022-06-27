/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Constants;
import utils.Tokenizer;
import io.jsonwebtoken.Claims;
import utils.JSONWorker;

/**
 *
 * @author Ilhomjon
 */
public class CORSFilter implements Filter {


    public CORSFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        _logger.info("Cors filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        httpResponse.addHeader("Access-Control-Allow-Headers", "*, Content-Type, access_token, path, doc_name, extension");
        httpResponse.addHeader("Access-Control-Expose-Headers", "*");

        if (httpRequest.getMethod().equals("OPTIONS")) {
            httpResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
            httpResponse.getOutputStream().write("Accepted".getBytes());
            return;
        }

        if (httpRequest.getPathInfo() != null && httpRequest.getPathInfo().equals("/user/login")) {
            chain.doFilter(httpRequest, httpResponse);
        } else if (httpRequest.getPathInfo() != null && httpRequest.getPathInfo().equals("/user/register")) {
            chain.doFilter(httpRequest, httpResponse);
        } else if (httpRequest.getPathInfo() != null && httpRequest.getPathInfo().equals("/system/token")) {
            chain.doFilter(httpRequest, httpResponse);
        } else if (httpRequest.getPathInfo() != null && httpRequest.getPathInfo().equals("/system/modules")) {
            chain.doFilter(httpRequest, httpResponse);
        } else if (httpRequest.getPathInfo() != null && httpRequest.getPathInfo().equals("/test")) {
            chain.doFilter(httpRequest, httpResponse);
        } else if (httpRequest.getHeader(Constants.KEY_BARER_TOKEN) == null) {
            httpResponse.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            httpResponse.getOutputStream().write("Expired".getBytes());
        } else {
            Claims claims = Tokenizer.parseBarerToken(httpRequest.getHeader(Constants.KEY_BARER_TOKEN));
            if (claims == null) {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.getOutputStream().write("Forbidden".getBytes());
            } else {
                JsonObject json = JSONWorker.getJsonObj(claims.getSubject());
                if (json == null) {
                    httpResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    httpResponse.getOutputStream().write("Not format".getBytes());
                } else {
                    httpRequest.setAttribute(Constants.KEY_TOKEN_ID, claims.getId());
                    httpRequest.setAttribute(Constants.KEY_USERS_ID, json.get(Constants.KEY_USERS_ID).getAsInt());
                    httpRequest.setAttribute(Constants.KEY_ROLES_ID, json.get(Constants.KEY_ROLES_ID).getAsInt());
//                    httpRequest.setAttribute(Constants.KEY_BRANCH_ID, json.get(Constants.KEY_BRANCH_ID).getAsInt());
//                    httpRequest.setAttribute(Constants.KEY_LEVEL_NUM, json.get(Constants.KEY_LEVEL_NUM).getAsInt());

                    chain.doFilter(httpRequest, httpResponse);
                }
            }
        }
        
        if (httpResponse.getStatus() == 200 || httpResponse.getStatus() == 201 || httpResponse.getStatus() == 403) {
        } else {
//            _logger.info("res:" + httpResponse.getStatus()
//                    + " meth:" + Utils.padRight(httpRequest.getMethod(), 7)
//                    + " path:" + httpRequest.getPathInfo()
//                    + " ip:" + httpRequest.getRemoteAddr()
//                    + " usr:" + httpRequest.getHeader("User-Agent")
//                    + " port:" + httpRequest.getRemotePort()
//                    + " cl:" + httpRequest.getRemoteUser()
//                    + " host:" + httpRequest.getRemoteHost());
        }
    }

    @Override
    public void destroy() {
//        _logger.info("Cors filter destroy");
    }
}

