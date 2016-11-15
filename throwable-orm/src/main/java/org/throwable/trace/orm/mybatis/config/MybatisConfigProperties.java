package org.throwable.trace.orm.mybatis.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjc
 * @version 2016/11/14 22:32
 * @description Mybatis属性配置
 */
@Component
@ConfigurationProperties(prefix = "org.throwable.trace.orm.mybatis")
public class MybatisConfigProperties {

	private Boolean enable_transaction = false;  //是否允许使用Mybatis管理事务
    private Boolean active = false; //是否使用Mybatis
    private String type_alias_package;  //实体所在包全路径名,多个用','分隔
    private String mapping_locations ; //mapping.xml文件的类路径
    private String base_package;  //mapper 接口所在的包全路径名,多个包用','或者';'分隔
    private String mappers; //通用mapper的全类名,多个用','分隔

    public Boolean getEnable_transaction() {
        return enable_transaction;
    }

    public void setEnable_transaction(Boolean enable_transaction) {
        this.enable_transaction = enable_transaction;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getType_alias_package() {
        return type_alias_package;
    }

    public void setType_alias_package(String type_alias_package) {
        this.type_alias_package = type_alias_package;
    }

    public String getMapping_locations() {
        return mapping_locations;
    }

    public void setMapping_locations(String mapping_locations) {
        this.mapping_locations = mapping_locations;
    }

    public String getBase_package() {
        return base_package;
    }

    public void setBase_package(String base_package) {
        this.base_package = base_package;
    }

    public String getMappers() {
        return mappers;
    }

    public void setMappers(String mappers) {
        this.mappers = mappers;
    }

    public Resource[] mappingsLocations(){
        List<ClassPathResource> classPathResources = new ArrayList<>();
        if (!StringUtils.isBlank(this.mapping_locations)) {
            String[] mappings = mapping_locations.split(",");
            for (String mapping : mappings) {
                classPathResources.add(new ClassPathResource(mapping));
            }
        } else {
            return null;
        }
        return classPathResources.toArray(new ClassPathResource[classPathResources.size()]);
    }
}
