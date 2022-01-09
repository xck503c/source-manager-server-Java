package com.xck.controller.source;

import com.xck.model.req.ConsumeTimeReq;
import com.xck.model.req.ReqResponse;
import com.xck.service.ConsumerService;
import com.xck.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 时间消费信息接口
 *
 * @author xuchengkun
 * @date 2022/01/06 11:02
 **/
@Controller
@RequestMapping("/time")
public class ConsumeTimeInfoController {

    @Autowired
    private ConsumerService consumerService;

    /**
     * 添加时间消费
     */
    @ResponseBody
    @RequestMapping("/add")
    public ReqResponse add(@RequestBody ConsumeTimeReq req, HttpServletRequest request) {

        String userId = (String) request.getAttribute("userId");

        if (req.getUseTime() < 0) {
            return ReqResponse.paramError("useTime参数");
        }

        if (StringUtils.isEmpty(req.getProductName())) {
            return ReqResponse.paramError("productName参数");
        }

        if (StringUtils.isEmpty(req.getStartTime()) || !DateUtil.isYyyyyMMddhhmm(req.getStartTime())) {
            return ReqResponse.paramError("startTime参数");
        }

        if (StringUtils.isEmpty(req.getEndTime()) || !DateUtil.isYyyyyMMddhhmm(req.getEndTime())) {
            return ReqResponse.paramError("endTime参数");
        }

        long diff = DateUtil.diffLongTime(req.getStartTime(), req.getEndTime(), DateUtil.yyyyMMddhhmm);
        if (diff <= 0) {
            return ReqResponse.paramError("end <= start");
        }

        int consumeType = req.getConsumeType();
        if (consumeType <= 0 || !consumerService.isConsumerTypeExists(consumeType)) {
            return ReqResponse.paramError("consumeType参数");
        }

        long useTime = diff/1000/60;

        if (req.getUseTime() > useTime) {
            return ReqResponse.paramError("useTime参数超过实际时间" + useTime + "min");
        }

        if (req.getUseTime() == 0) {
            req.setUseTime((int)useTime);
        }

        if (req.getUseTime() < 10 || req.getUseTime() > 20 * 60) {
            return ReqResponse.paramError("end - start < 10min || > 30h");
        }

        req.setUserId(userId);
        req.setConsumeId(System.currentTimeMillis() + "");

        if (!consumerService.addTimeConsume(req)) {
            return ReqResponse.error(ReqResponse.OPER_ERR);
        }

        return ReqResponse.success("添加成功");
    }

    @ResponseBody
    @RequestMapping("selectAll")
    public List<ConsumeTimeReq> selectAll() {
        return consumerService.selectTimeConsumes();
    }
}
