package com.imooc.controller;

import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.OrderDTO;
import com.imooc.exception.SellException;
import com.imooc.form.ProductForm;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductService;
import com.imooc.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

//卖家端商品部分的展示
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    //列表
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                             Map<String,Object> map){
        //我定义的接口是从第一页开始查，而PageRequest从第0页开始的，所以这里要减1
        PageRequest request= PageRequest.of(page-1,size);
        Page<ProductInfo> productInfoPage=productService.findAll(request);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);//当前页
        map.put("size",size);//每页条数
        //第一个参数是模板名字
        return new ModelAndView("product/list",map);
    }
    // 商品上架
    @GetMapping("/on_sale")
    @CacheEvict(cacheNames = "product",key = "123")//在访问这个方法后会把缓存清除，下次BuyerProductController那边再次访问list接口就存一次更新后的数据
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object>map){
        try{
            productService.onSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
    // 商品下架
    @GetMapping("/off_sale")
    @CacheEvict(cacheNames = "product",key = "123")//在访问这个方法后会把缓存清除，下次BuyerProductController那边再次访问list接口就存一次更新后的数据
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String,Object>map){
        try{
            productService.offSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
    //商品商页
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,
                      Map<String,Object>map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo=productService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        //查询所有类目
        List<ProductCategory> categoryList=categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("product/index",map);
    }
    //保存和更新（也就是修改或者新增后提交）
    @PostMapping("/save")
    //缓存更新注解，使用 @CachePut的话返回对象要和使用@Cacheable那边返回的对象一致
//    @CachePut(cacheNames = "product",key = "123")//@CachePut每次都会执行下面方法的内容然后存进去缓存
    @CacheEvict(cacheNames = "product",key = "123")//在访问这个方法后会把缓存清除，下次BuyerProductController那边再次访问list接口就存一次更新后的数据
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        //new一个的时候就有上下架状态了因为默认在架
        ProductInfo productInfo=new ProductInfo();
        try{
            //如果productId是空的说明是新增
            if(!StringUtils.isEmpty(form.getProductId())){
                //先查询商品主要是为了得到商品上下架状态form没有传这个属性过来就得自己查询拷贝
                productInfo=productService.findOne(form.getProductId());
            }else{
                //如果新增要设置id
                 form.setProductId(KeyUtil.genUniqueKey()

                 );
            }
            //传过来的数据没错就要拷贝了
            BeanUtils.copyProperties(form,productInfo);
            productService.save(productInfo);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}
