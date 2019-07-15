package com.luo.mybatis.session;

import com.luo.mybatis.binding.MapperMethod;
import com.luo.mybatis.binding.MapperRegistry;
import com.luo.mybatis.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class Configuration {
    private InputStream inputStream;
    MapperRegistry mapperRegistry = new MapperRegistry();

    public Map<String, MapperMethod> loadXMLConfigruration(String resource){
        HashMap<String, MapperMethod> map = new HashMap<String, MapperMethod>();
        MapperMethod mapperMethod = new MapperMethod();
        mapperMethod.setSql("");
        mapperMethod.setType(UserInfoMapper.class);
        map.put("UserInfoMapper.selectByPrimaryKey",mapperMethod);
        return map;
    }

    public void loadXMLConfigrurations(){
        try {
            /*Document document = new SAXReader().read(inputStream);
            Element root = document.getRootElement();
            List<Element> mappers = root.element("mappers").elements("mapper");
            for(Element mapper : mappers){
                if(mapper.attribute("resource") != null){
                    mapperRegistry.setKnownMappers(loadXMLConfigruration(mapper.attribute("resource").getText()));
                }
                if(mapper.attribute("resource") != null){

                }
            }*/
            mapperRegistry.setKnownMappers(loadXMLConfigruration(""));

        } catch (Exception e) {
//            log.error("读取文件错误:{}",e);
        }/*finally {
            try {
                inputStream.close();
            } catch (IOException e) {

            }
        }*/
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public MapperRegistry getMapperRegistry() {
        return mapperRegistry;
    }

}
