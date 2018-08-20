package com.chanjetpay.garlic.web;

import com.chanjetpay.garlic.api.OfficialService;
import com.chanjetpay.garlic.common.CookieUtils;
import com.chanjetpay.garlic.common.ResourceBundleUtils;
import com.chanjetpay.garlic.dto.Oauth2AccessTokenDto;
import com.chanjetpay.garlic.dto.WxOauth2Dto;
import com.chanjetpay.garlic.dto.WxOfficialSignDto;
import com.chanjetpay.result.GenericResult;
import com.chanjetpay.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
@RequestMapping("official-web")
public class OfficialWebController {

	private static final Logger logger = LoggerFactory.getLogger(OfficialWebController.class);

	@Autowired
	private OfficialService officialService;

	@RequestMapping(value = "/{blockCode}/wx-connect", method = RequestMethod.GET, produces = "application/xml;charset=utf-8")
	public @ResponseBody String connect(@PathVariable("blockCode") String blockCode,
										@RequestParam("signature") String signature,
										@RequestParam("timestamp") String timestamp,
										@RequestParam("nonce") String nonce,
										@RequestParam("echostr") String echostr) {

		logger.info("传入参数 - signature:{},timestamp:{},nonce:{},echostr:{}",signature,timestamp,nonce,echostr);

		//WxOfficial wxOfficial = wxOfficialManager.findWxOfficialByCode(blockCode);
		//if(wxOfficial == null){
		//	return "error";
		//}
		//
		//String[] str = {wxOfficial.getToken(), timestamp, nonce};
		//Arrays.sort(str); // 字典序排序
		//String value = str[0] + str[1] + str[2];
		//
		//String sign = DigestUtils.shaHex(value);
		WxOfficialSignDto wxOfficialSign = new WxOfficialSignDto();
		wxOfficialSign.setTimestamp(timestamp);
		wxOfficialSign.setNonce(nonce);
		GenericResult<String> result = officialService.wxOfficialSign(blockCode, wxOfficialSign);
		if(result.getCode().equals(Result.SUCCESS)){
			return result.getCode() + result.getDesc();
		}else if(signature.equals(result.getData())){
			return echostr;
		}else {
			return "error";
		}

		//logger.info("token:" + wxOfficial.getToken() + " result:" + signature.equals(sign));
		//if (signature.equals(sign)) {// 验证成功返回ehcostr
		//	return echostr;
		//} else {
		//	return "error";
		//}
	}

	@RequestMapping(value = "/{blockCode}/wx-oauth2-code", method = RequestMethod.GET)
	public String oAuth2Code(@PathVariable("blockCode") String blockCode, @RequestParam("fromUri") String fromUri) throws IOException {

		logger.info("oAuth2CodeUrl获取code, blockCode - {}", blockCode);

		WxOauth2Dto wxOauth2 = new WxOauth2Dto(URLEncoder.encode("http://" + ResourceBundleUtils.getDomain() + "/official/" + blockCode + "/wx-oauth2-callback", "utf-8"),
				URLEncoder.encode(fromUri, "utf-8"));

		GenericResult<WxOauth2Dto> result = officialService.wxOfficialCode(blockCode, wxOauth2);

		if(!result.getCode().equals(Result.SUCCESS)){
			throw new RuntimeException(result.getCode() + result.getDesc());
		}

		String uri = result.getData().getRedirectUri();

		return "redirect:" + uri;

	}

	@RequestMapping(value = "/{blockCode}/wx-oauth2-callback", method = RequestMethod.GET)
	public String callback(@PathVariable("blockCode") String blockCode, @RequestParam("code") String code,
						   @RequestParam("state") String state, HttpServletResponse response) {

		logger.info("获取oauth2 token - blockCode:{},code:{},state:{}", blockCode, code, state);

		GenericResult<Oauth2AccessTokenDto> oauth2AccessTokenResult = officialService.wxOauth2AccessToken(blockCode, code);

		logger.info("获取 oauth2AccessToken - result -{}",oauth2AccessTokenResult);

		if (!oauth2AccessTokenResult.getCode().equals(Result.SUCCESS)) {
			throw new RuntimeException(oauth2AccessTokenResult.getCode() + oauth2AccessTokenResult.getDesc());
		}

		String openId = oauth2AccessTokenResult.getData().getOpenId();
		//记录openId到cookie
		CookieUtils.addCookie(response, CookieUtils.COOKIE_USER_ID, openId, ResourceBundleUtils.getDomain());

		return "redirect:" + state;
	}

}
