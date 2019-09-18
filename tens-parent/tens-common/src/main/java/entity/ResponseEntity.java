package entity;

import lombok.Data;

@Data
public class ResponseEntity {

    private boolean bool;
    private Integer status;
    private String msg;
    private Object data;

    public ResponseEntity() {
    }

    public ResponseEntity(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResponseEntity(String msg, Object data) {
        this.data = data;
        this.msg = msg;
    }

    public ResponseEntity(Integer status, Object data) {
        this.status = status;
        this.data = data;
    }

    public ResponseEntity(boolean bool, Integer status, String msg) {
        this.bool = bool;
        this.status = status;
        this.msg = msg;
    }

    public ResponseEntity(boolean bool, Integer status, String msg, Object data) {
        this.bool = bool;
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseEntity SUCCESS() {
        return new ResponseEntity(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMsg());
    }

    public static ResponseEntity SUCCESS(String msg) {
        return new ResponseEntity(StatusCode.SUCCESS.getCode(), msg);
    }

    public static ResponseEntity SUCCESS(String msg, Object data) {
        return new ResponseEntity(msg, data);
    }

    public static ResponseEntity SUCCESS(Object data) {
        return new ResponseEntity(StatusCode.SUCCESS.getCode(), data);
    }

    public static ResponseEntity ERROR(String msg) {
        return new ResponseEntity(StatusCode.ERROR.getCode(), msg);
    }
}
