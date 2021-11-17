package com.consumer.util;

import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * lua脚本，统一放在classpath:lua/目录下<br>
 * date: 2018/2/2 10:36<br>
 * author: lujianzhang<br>
 */
public final class LuaUtils {

    private static Map<String, Map<String, String>> luaCache = new HashMap<String, Map<String, String>>();

    private static String luaPath = "classpath:lua/luaScript.xml";

    static{
        try {
            loadLuaScriptFromConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setLuaPath(){
        Map<String, String> luaCacheItem = luaCache.get(luaPath);
        if(luaCacheItem == null){
            try{
                loadLuaScriptFromConfig();
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    public static String getScript(String key){
        return luaCache.get(luaPath).get(key);
    }

    private static void loadLuaScriptFromConfig() throws Exception {

        String path = luaPath;

        path = path.trim().replace("classpath:", "");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = LuaUtils.class.getClassLoader().getResourceAsStream(path);
        Document parentDoc = db.parse(is);
        NodeList doc = parentDoc.getElementsByTagName("script");
        Map<String, String> tempCache = new HashMap<>();
        for(int i=0; i<doc.getLength(); i++){
            Node node = doc.item(i);
            String key = node.getAttributes().getNamedItem("name").getNodeValue();
            String value = node.getTextContent();
            if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value))
                tempCache.put(key, value);
        }
        luaCache.put(luaPath, tempCache);

    }


}
