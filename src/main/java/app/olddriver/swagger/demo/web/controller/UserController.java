package app.olddriver.swagger.demo.web.controller;

import app.olddriver.swagger.demo.entity.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "user", description = "这是一组用户业务接口")
public class UserController {


    @GetMapping("/{id}")
    @ApiOperation(value = "get user by id", notes = "return a user", nickname = "getUserById")
    public User getUserById(
            @ApiParam(value = "ID of user", required = true)
            @PathVariable String id) {
        return new User(id, id, 0);
    }


    /**
     * 注意<code>@RequestBody</code>注解，说明这个只能非表单提交
     *
     * @param user user
     * @return Response
     */
    @PostMapping("/post")
    @ApiOperation(value = "create user", notes = "创建用户")
    public String post(
            @ApiParam(value = "demo请求体内容", required = true)
            @RequestBody User user) {
        return "OK";
    }


    /**
     * 注意<code>@RequestParam</code>注解，说明此接口只能表单提交
     *
     * @param id   id
     * @param name name
     * @return Response
     */
    @PutMapping("/put")
    @ApiOperation(value = "update user", notes = "修改用户")
    public User put(
            @ApiParam(value = "ID", required = true)
            @RequestParam String id,
            @ApiParam(value = "用户名", required = true, example = "张三")
            @RequestParam String name,
            @ApiParam(value = "年龄", required = true, example = "18")
            @RequestParam int age) {
        return new User(id, name, age);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "这是一个delete方法接口", notes = "删除demo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UserId", value = "用户ID", paramType = "header"),
            @ApiImplicitParam(name = "mobilePhone", value = "手机号", paramType = "header"),
            @ApiImplicitParam(name = "CV", value = "版本号", paramType = "header")
    })
    public String delete(
            @ApiParam(value = "ID", required = true, example = "123456789")
            @RequestParam String id) {
        return "OK";
    }
}
