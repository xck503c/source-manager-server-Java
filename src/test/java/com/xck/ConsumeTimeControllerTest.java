package com.xck;

import com.alibaba.fastjson.JSONObject;
import com.xck.model.req.ConsumeTimeReq;
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
 * 时间消费接口测试
 *
 * @author xuchengkun
 * @date 2022/01/08 22:30
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumeTimeControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    public String login() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("userId", "xck")
                .param("password", "xck123456");
        ResultActions actions = mvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        String result = actions.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(result);
        String uuid = jsonObject.getString("data");

        return uuid;
    }

    @Test
    public void addTimeRecord() throws Exception {
        String uuid = login();

        ConsumeTimeReq req = new ConsumeTimeReq();
        req.setProductName("码代码");
        req.setStartTime("2021-01-08 11:11:11");
        req.setEndTime("2021-01-08 11:11:11");
        req.setUseTime(1);
        req.setComment("");
        req.setConsumeType(3002);

        RequestBuilder builder = MockMvcRequestBuilders.post("/time/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .header("token", uuid)
                .content(JSONObject.toJSONString(req));
        ResultActions actions = mvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        String result = actions.andReturn().getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(result);
        Assert.assertEquals(jsonObject.get("errCode"), 0);
    }
}
