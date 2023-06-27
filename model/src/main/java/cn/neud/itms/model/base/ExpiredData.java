package cn.neud.itms.model.base;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpiredData {
    private LocalDateTime expireTime;
    private Object data;
}
