package com.keldron.foxtrot.info;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Info {

    private String key;
    private APIInfoCodes infoCode;
    private String desc;
    private Object object;

    public Info() {
        key = UUID.randomUUID().toString();
    }

    public static ResponseEntity<Info> getInfoResponseEntity(Info result, HttpStatus okStatus, HttpStatus wrongStatus) {
        if (result.isOK()) {
            return new ResponseEntity<>(result, okStatus);
        }
        return new ResponseEntity<>(result, wrongStatus);
    }

    public APIInfoCodes getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(APIInfoCodes infoCode) {
        this.infoCode = infoCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isOK() {
        return this.infoCode.equals(APIInfoCodes.OK);
    }

    @Override
    public String toString() {
        return "Info [desc=" + desc + ", object=" + object + "]";
    }

    public static class InfoBuilder {
        private Info info;

        public InfoBuilder() {
            info = new Info();
        }

        public InfoBuilder setDescription(String desc) {
            info.setDesc(desc);
            return this;
        }

        public InfoBuilder setInfoCode(APIInfoCodes infoCode) {
            info.setInfoCode(infoCode);
            return this;
        }

        public InfoBuilder setObject(Object object) {
            info.setObject(object);
            return this;
        }

        public Info build() {
            return info;
        }
    }
}
