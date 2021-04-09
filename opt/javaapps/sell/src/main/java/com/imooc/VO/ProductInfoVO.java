package com.imooc.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/*商品详情不直接使用上面的那个是因为前端需要啥你给他啥，不需要的不要给
 * 以免泄露信息*/
@Data
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = -3895834204864685262L;
    @JsonProperty("id")//告诉前端返回去对应前端一个叫id的名字（下面一样道理）
    private String productId;
    @JsonProperty("name")
    private String productName;
    @JsonProperty("price")
    private BigDecimal productPrice;
    @JsonProperty("description")
    private String productDescription;
    @JsonProperty("icon")
    private String productIcon;

}
