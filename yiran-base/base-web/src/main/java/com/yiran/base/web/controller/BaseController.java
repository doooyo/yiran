package com.yiran.base.web.controller;

import org.springframework.http.ResponseEntity;

import com.yiran.base.core.code.Code;
import com.yiran.base.core.data.BaseRespData;
import com.yiran.base.core.data.Transaction;
import com.yiran.base.core.util.GsonUtil;

public abstract class BaseController {

	protected ResponseEntity<String> response(int code, String message) {
		BaseRespData data = new BaseRespData();
		data.setCode(code);
		data.setMessage(message);
		return response(data);
	}

	protected ResponseEntity<String> error(String message) {
		return response(Code.SC_BAD_REQUEST, message);
	}

	protected ResponseEntity<String> ok(String message) {
		return response(Code.SC_OK, message);
	}

	protected <T> ResponseEntity<String> ok(T data) {
		return response(Code.SC_OK, GsonUtil.dateGson().toJson(data));
	}

	protected ResponseEntity<String> response(BaseRespData data) {
		if (null != data.getTransaction()) {
			return ResponseEntity.status(data.getCode())
					.header(Transaction.YIRAN_BASE_HEADER_TRANSACTION, data.getTransaction().getTransaction())
					.header(Transaction.YIRAN_BASE_HEADER_VALIDITY, data.getTransaction().getValidity().toString())
					.body(GsonUtil.dateGson().toJson(data));
		} else {
			return ResponseEntity.status(data.getCode()).body(GsonUtil.dateGson().toJson(data));
		}
	}
}
