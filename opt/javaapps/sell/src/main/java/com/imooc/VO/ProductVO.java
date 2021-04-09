package com.imooc.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*商品（包含类目），这个跟前面的ProductInfo不一样，不直接使用上面的那个是因为前端需要啥你给他啥，不需要的不要给
* 以免泄露信息*/
@Data
public class ProductVO implements Serializable
{
    private static final long serialVersionUID = 7097863777546530545L;
    @JsonProperty("name")//告诉前端返回去对应前端一个叫name的名字（下面一样道理）
    private  String categoryName;//类目名字
    @JsonProperty("type")
    private  Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
