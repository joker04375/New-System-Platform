package net.maku.auth.bo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class TestInfo {
    @Min(value = 0, message = "商品价格不能为负数")
    private int price;

    @NotBlank(message = "商品名称不能为空")
    private String goodsName;

    @Min(value = 0, message = "商品数量不能为零")
    private int account;
}
