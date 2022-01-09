package com.xck;

import com.alibaba.fastjson.JSONObject;
import com.xck.service.LoginService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 登录测试
 *
 * @author xuchengkun
 * @date 2022/01/07 09:53
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * 无token的请求
     */
    @Test
    public void noTokenReq() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.post("/source/list");
        mvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"desc\":\"token鉴权失败, token不合法\",\"errCode\":106}"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 先登录拿到token，然后放到header里面，请求source/list接口，访问成功
     * @throws Exception
     */
    @Test
    public void successNormalLogin() throws Exception{
        RequestBuilder builder = MockMvcRequestBuilders.post("/login")
                .param("userId", "xck")
                .param("password", "xck123456");
        ResultActions actions = mvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        String result = actions.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(result);
        String uuid = jsonObject.getString("data");

        builder = MockMvcRequestBuilders.post("/source/list")
                .header("token", uuid);
        actions = mvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        result = actions.andReturn().getResponse().getContentAsString();
        jsonObject = JSONObject.parseObject(result);
        int code = jsonObject.getInteger("errCode");
        Assert.assertEquals(code, 0);

        LoginService.TOKEN_TIMEOUT = 5000;

        Thread.sleep(5000);

        builder = MockMvcRequestBuilders.post("/source/list")
                .header("token", uuid);;
        mvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"desc\":\"token鉴权失败, token超时\",\"errCode\":106}"))
                .andDo(MockMvcResultHandlers.print());
    }
}
