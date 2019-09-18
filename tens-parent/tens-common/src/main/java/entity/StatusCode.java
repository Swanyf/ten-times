package entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public enum StatusCode {

    SUCCESS(1, "操作成功"),
    ERROR(0, "操作失败"),
    LOGIN_ERROR(2002, "登录失败"),
    PERMISSION_DENIED(2003, "权限不足"),
    REMOTE_ERROR(2004, "远程调用失败"),
    REPEAT_ERROR(2005, "请勿重复操作");

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Getter
    @Setter
    private Integer code;
    @Getter
    @Setter
    private String msg;

}
