package com.portfolio.trading.common;

import com.portfolio.trading.data.dto.response.CommonResult;
import com.portfolio.trading.data.dto.response.ListResult;
import com.portfolio.trading.data.dto.response.SingleResult;

import java.util.List;

public class ApiResponse {

    // 단일건 결과 처리 메소드
    public static <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        return result;
    }

    // 복수건 결과 처리 메서드
    public static <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        return result;
    }

    // 성공 결과만 처리
    public static CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    // 실패 결과만 처리
    public static CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        setFailResult(result, code, msg);
        return result;
    }

    // API 요청 성공 시 응답 모델을 성공 데이터로 세팅
    private static void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    // API 요청 실패 시 응답 모델을 실패 데이터로 세팅
    private static void setFailResult(CommonResult result, int code, String msg) {
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
    }
}
