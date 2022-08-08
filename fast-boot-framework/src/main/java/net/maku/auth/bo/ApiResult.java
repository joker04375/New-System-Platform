package net.maku.auth.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.maku.auth.constant.ApiStatus;
import net.maku.auth.exception.ErrorCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult implements Serializable{
    private static final Map<String, String> map = new HashMap<>(1);
    private int code;
    private String msg;
    private Object data;
    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;

    public static <T> ApiResult ok() {
        return ok(ApiStatus.OK.getMsg(), null);
    }


    public static <T> ApiResult ok(T data) {
        return ok(ApiStatus.OK.getMsg(), data);
    }


    public static <T> ApiResult ok(String msg, T data) {

        if (msg != null && data instanceof String) {
            String value = (String) data;

            map.clear();
            map.put(msg, value);

            ApiResult apiResult = new ApiResult();
            apiResult.setCode(ApiStatus.OK.getCode());
            apiResult.setMsg(ApiStatus.OK.getMsg());
            apiResult.setData(map);
            apiResult.setTimestamp(LocalDateTime.now());
            return apiResult;
        }

        ApiResult apiResult = new ApiResult();
        apiResult.setCode(ApiStatus.OK.getCode());
        apiResult.setMsg(msg);
        apiResult.setData(data);
        apiResult.setTimestamp(LocalDateTime.now());
        return apiResult;
    }


    public static <T> ApiResult fail(String msg) {
        return fail(msg, null);
    }

    public static <T> ApiResult fail(String msg, T data) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(ApiStatus.FAIL.getCode());
        apiResult.setMsg(msg);
        apiResult.setData(data);
        apiResult.setTimestamp(LocalDateTime.now());
        return apiResult;
    }

    public static <T> ApiResult error(ErrorCode errorCode) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(errorCode.getCode());
        apiResult.setMsg(errorCode.getMsg());
        apiResult.setTimestamp(LocalDateTime.now());
        return apiResult;
    }

    public static <T> ApiResult error(String msg) {
        return error(msg, null);
    }

    public static <T> ApiResult error(T data) {
        return error(null, data);
    }

    public static <T> ApiResult error(String msg, T data) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(ApiStatus.ERROR.getCode());
        apiResult.setMsg(msg);
        apiResult.setData(data);
        apiResult.setTimestamp(LocalDateTime.now());
        return apiResult;
    }

    public static <T> ApiResult instance(ApiStatus apiStatus) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(apiStatus.getCode());
        apiResult.setMsg(apiStatus.getMsg());
        apiResult.setData(null);
        apiResult.setTimestamp(LocalDateTime.now());
        return apiResult;
    }

    public boolean isFail() {
        return this.code != ApiStatus.OK.getCode();
    }

    public boolean isSuccess() {
        return this.code == ApiStatus.OK.getCode();
    }
}
