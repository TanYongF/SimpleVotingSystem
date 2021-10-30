package cn.njupt.votingsystem.enums;

import lombok.Getter;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Getter
public enum ResultEnum {
    INTERNAL_WRONG(500, "内部错误!");

    private final Integer resultCode;
    private final String resultMsg;

    ResultEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
