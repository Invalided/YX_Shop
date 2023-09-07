package com.o2o.shop.api.v1;

import com.o2o.shop.dto.user.RegisterDTO;
import com.o2o.shop.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;

/**
 * HelloController 项目使用基本示例
 * @Author: 勿忘初心
 * @Date: 2023-06-26 1:00
 */

@RestController
@RequestMapping("/v1/hello")
@Slf4j
@Validated
public class HelloController {

    /**
     * GetMapping 请求
     * @param name 用户名称
     * @param uid  用户uid
     * @return
     */
    @GetMapping("/{id}")
    public ResultDataVO getMsg(@RequestParam(required = false) String name,
                               @Max(value = 10,message = "最大值不能超过10")
                               @PathVariable(name = "id") int uid) {
        log.info("访问hello下的msg方法。");
        String result = "Hello,"+name+" id "+uid;

        return ResultDataVO.success(result);
    }

    /**
     * 测试统一异常处理
     * @return
     */
    @GetMapping("/error")
    public ResultDataVO getError(){
        int res = 1 / 0;
        //throw new BusinessException(ExceptionCodeEnum.EC0);
        return ResultDataVO.success(res);
    }

    /**
     * PostMapping 请求
     * @param user
     * @return
     */
    @PostMapping("/msg")
    public ResultDataVO postMsg(@RequestBody @Validated RegisterDTO user){
        return ResultDataVO.success("hello,"+user.getUsername());
    }

    /**
     * PostMapping 请求
     * @param user
     * @param code
     * @return
     */
    @PutMapping("/msg")
    public ResultDataVO putMsg(@RequestBody RegisterDTO user,
                               @RequestParam Integer code){
        return ResultDataVO.success("hello,"+user.getUsername()+" code "+code);
    }

    /**
     * DeleteMapping请求
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultDataVO deleteMsg(@PathVariable Integer id){
        return ResultDataVO.success("hello, "+id);
    }
}
