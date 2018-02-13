package com.chanjetpay.garlic.exception;

import com.chanjetpay.exception.BaseException;

public class UploadException extends BaseException {

	public UploadException(String defineCode, String defineDesc) {
		super(defineCode, defineDesc);
	}

	public UploadException(String defineCode, String defineDesc, String message) {
		super(defineCode, defineDesc, message);
	}
}
