package janisl.musicdb.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerMapping;

@RestController
public class DiscogsController {

    @Value("${discogs.key}")
    private String discogsKey;

    @Value("${discogs.secret}")
    private String discogsSecret;

    @RequestMapping(value = "/discogs/**", method = RequestMethod.GET)
    public @ResponseBody String restProxyGet(HttpServletRequest request) throws InterruptedException {
        Thread.sleep(500);
        
        String restOfTheUrl = request.getServletPath().substring(9);
        if (request.getQueryString() != null && !request.getQueryString().isEmpty())
            restOfTheUrl = restOfTheUrl + "?" + request.getQueryString();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, "MusicDB/1.0");
        headers.set("Authorization", "Discogs key=" + discogsKey + ", secret=" + discogsSecret);
        
        HttpEntity entity = new HttpEntity(headers);
        
        return new RestTemplate().exchange("https://api.discogs.com/" + restOfTheUrl, HttpMethod.GET, entity, String.class).getBody();
    }

}
