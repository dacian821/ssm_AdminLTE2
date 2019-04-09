package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Product;
import com.itheima.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping("/findAll.do")
    @RolesAllowed("ADMIN")
    public ModelAndView findAll() throws Exception {
        List<Product> list = iProductService.findAllProduct();
        ModelAndView mv = new ModelAndView();
        mv.addObject("productList", list);
        mv.setViewName("product-list1");
        return mv;

    }

    @RequestMapping("/save.do")
    public String save(Product product) throws Exception {
        iProductService.save(product);
        return "redirect:findAll.do";
    }


    @RequestMapping("/delete.do")
    public String delete(String [] ids) {
        for (String id : ids) {
            System.out.println(id);

        }
        return null;
    }
}
